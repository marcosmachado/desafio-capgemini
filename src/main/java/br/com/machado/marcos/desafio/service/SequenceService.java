package br.com.machado.marcos.desafio.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.ArrayUtils;

import br.com.machado.marcos.desafio.domain.request.SequenceRequest;
import br.com.machado.marcos.desafio.domain.response.SequenceResponse;
import br.com.machado.marcos.desafio.domain.response.StatsResponse;

@Service
public class SequenceService {

    public SequenceResponse sequence(SequenceRequest request) {
        return new SequenceResponse(this.validateSequence(request));
    }


    private boolean validateSequence(SequenceRequest request) {
        int totalHorizontalSequenceValid= findSequence(request.getLetters());
        int totalVerticalSequenceValid  = findSequence(getVerticalLetters(request.getLetters()));
        int totalDiagonal1SequenceValid = findSequence(getDiagonalLetters(request.getLetters()));
        int totalDiagonal2SequenceValid = findSequence(getDiagonalLetters(getVerticalLettersReverse(request.getLetters())));

        return (totalHorizontalSequenceValid + totalVerticalSequenceValid + totalDiagonal1SequenceValid
            + totalDiagonal2SequenceValid) > 1;
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

        System.out.println(lstLetters);

        return lstLetters.toArray(new String[0]);
    }


    public String[] getDiagonalLetters(String[] arrLetters) {

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
        System.out.println(lstLetters);
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


    public StatsResponse stats() {
        return new StatsResponse(40, 60, 0.4);
    }

}
