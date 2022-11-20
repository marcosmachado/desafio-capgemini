package br.com.machado.marcos.desafio.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


import br.com.machado.marcos.desafio.domain.request.SequenceRequest;
import br.com.machado.marcos.desafio.domain.response.SequenceResponse;
import br.com.machado.marcos.desafio.domain.response.StatsResponse;
import br.com.machado.marcos.desafio.exception.SequenceException;
import br.com.machado.marcos.desafio.model.Sequence;
import br.com.machado.marcos.desafio.repository.SequenceRepository;

@Service
public class SequenceService {

    @Autowired
    private SequenceRepository sequenceRepository;


    public SequenceResponse sequence(SequenceRequest request) throws SequenceException {

        this.verifyInvalidCaracters(request.getLetters());

        return new SequenceResponse(this.validateSequence(request));

    }

    private void verifyInvalidCaracters(String[] arrLetters) throws SequenceException {
        Pattern p = Pattern.compile("^[BUDH]*$");
        for (String letters : arrLetters) {
            if (!p.matcher(letters).matches()) {
                throw new SequenceException("A sequencia só pode conter os caracteres (B, U, D, H)");
            }
        }
    }


    private boolean validateSequence(SequenceRequest request) throws SequenceException {
        int totalHorizontalSequenceValid= findSequence(request.getLetters());
        int totalVerticalSequenceValid  = findSequence(getVerticalLetters(request.getLetters()));
        int totalDiagonal1SequenceValid = findSequence(getDiagonalLetters(request.getLetters()));
        int totalDiagonal2SequenceValid = findSequence(getDiagonalLetters(getVerticalLettersReverse(request.getLetters())));

        boolean isValid = (totalHorizontalSequenceValid + totalVerticalSequenceValid + totalDiagonal1SequenceValid
            + totalDiagonal2SequenceValid) > 1;

        this.saveSequence(new Sequence( Arrays.toString(request.getLetters()), isValid));

        return isValid;
    }






    private String[] getVerticalLettersReverse(String[] arrLetters) {
        String[] verticalLetters = getVerticalLetters(arrLetters);
        List<String> lstLetters = new ArrayList<>();
        Arrays.asList(verticalLetters).forEach(letters -> {
            lstLetters.add(this.reverse(letters));
        });
        return lstLetters.toArray(new String[0]);
    }

    private String reverse(String letters) {
        String input = letters;
        StringBuilder input1 = new StringBuilder();
        input1.append(input);
        input1.reverse();
        return input1.toString();
    }



    private String[] getVerticalLetters(String[] arrLetters) {
        int line = 0;
        int col = 0;
        int totalColumns = arrLetters.length;

        List<String> lstLetters = new ArrayList<>();
        for (int i=0; i < totalColumns; i++) {
            lstLetters.add("");
        }

        for (int i=0; i < totalColumns; i++) {
            for (String letters : arrLetters) {
                char letter = letters.charAt(col);
                String sequence = lstLetters.get(line) + String.valueOf(letter);
                lstLetters.set(line, sequence);
            }
            col++;
            line++;
        }

        return lstLetters.toArray(new String[0]);
    }


    private String[] getDiagonalLetters(String[] arrLetters) {

        int totalLines = arrLetters.length;
        int totalColumns = arrLetters.length;
        List<String> lstLetters = new ArrayList<>();
        for (int i=0; i < (totalColumns * 2)-1; i++) {
            lstLetters.add("");
        }

        for (int line=0; line < totalLines; line++) {
            for (int col=0; col < totalColumns; col++) {
                char letter = arrLetters[line].charAt(col);
                String sequence = lstLetters.get(col - line + 5) + String.valueOf(letter);
                lstLetters.set(col - line + 5, sequence);
            }
        }
        return lstLetters.stream().filter(s -> s.length()>=4).collect(Collectors.toList()).toArray(new String[0]);
    }





    private Integer findSequence(String[] arrLetters) {

        int totalSequence = 0;

        for (String letters : arrLetters) {
            int totalEqualsLetters = 0;
            int index= 0;
            char referenceLetter = letters.charAt(index);
            while (index < letters.length()) {
                if (isNextLetterEquals(index, letters, referenceLetter)) {
                    totalEqualsLetters++;
                } else {
                    if (index+1 < letters.length()) {
                        referenceLetter = letters.charAt(index + 1);
                        if (totalEqualsLetters < 3) {
                            totalEqualsLetters = 0;
                        }
                    }
                }
                index++;
            }
            if (totalEqualsLetters >= 3) {
                totalSequence++;
            }
        }
        return totalSequence;
    }


    private boolean isNextLetterEquals(int index, String letters, char referencyLetter) {
        char nextLetter = '.';
        if (index + 1 < letters.length()) {
            nextLetter = letters.charAt(index + 1);
        } else {
            nextLetter = '.';
        }
        return nextLetter == referencyLetter;
    }



    private void saveSequence(Sequence sequenceModel) throws SequenceException {

        try {
            this.sequenceRepository.insert(sequenceModel);
        } catch (DuplicateKeyException e) {
            throw new SequenceException("Sequência já cadastrada");
        }
    }



    public StatsResponse stats() {
        AtomicReference<Double> count_valid = new AtomicReference<>(0D);
        AtomicReference<Double> count_invalid = new AtomicReference<>(0D);
        Double ratio = 0D;

        List<Sequence>  lstSequence = this.sequenceRepository.findAll();
        lstSequence.forEach( sequence -> {
            if (sequence.isValid()) {
                count_valid.getAndSet(count_valid.get() + 1);
            } else {
                count_invalid.getAndSet(count_invalid.get() + 1);
            }
        });

        ratio = Double.valueOf((count_valid.get() / lstSequence.size()));

        return new StatsResponse(count_valid.get().intValue(), count_invalid.get().intValue()
            , ratio);
    }




}
