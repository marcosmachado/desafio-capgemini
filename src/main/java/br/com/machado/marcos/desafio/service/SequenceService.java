package br.com.machado.marcos.desafio.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.machado.marcos.desafio.domain.request.SequenceRequest;
import br.com.machado.marcos.desafio.domain.response.SequenceResponse;
import br.com.machado.marcos.desafio.domain.response.StatsResponse;

@Service
public class SequenceService {

    public SequenceResponse sequence(SequenceRequest request) {
        return new SequenceResponse(this.validateSequence(request));
    }


    private boolean validateSequence(SequenceRequest request) {
        int totalHorizontalSequenceValid = findSequence(request.getLetters());
        int totalVerticalSequenceValid = findSequence(getVerticalLetters(request.getLetters()));
        int totalDiagonalSequenceValid = findSequence(getDiagonalLetters(request.getLetters()));

        this.getVerticalLetters(request.getLetters());

        return (totalHorizontalSequenceValid + totalVerticalSequenceValid + totalDiagonalSequenceValid) > 1;
    }

    public String[] getVerticalLetters(String[] arrLetters) {
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


    public String[] getDiagonalLetters(String[] arrLetters) {
        int line = 0;
        int col = 0;
        int totalColumns = arrLetters.length;
        List<String> lstLetters = new ArrayList<>();
        for (int i=0; i < (totalColumns * 2)-1; i++) {
            lstLetters.add("");
        }

        for (int i=0; i < totalColumns; i++) {

            for (int j=0; j < totalColumns; j++) {
                if (i == j) {
                    char letter = arrLetters[i].charAt(j);
                    String sequence = lstLetters.get(5) + String.valueOf(letter);
                    lstLetters.set(5, sequence);

                    System.out.println(lstLetters);
                }

                for (int colDiagonal = 1; colDiagonal < totalColumns; colDiagonal++) {
                    if (i + colDiagonal == j) {
                        char letter = arrLetters[i].charAt(j);
                        String sequence = lstLetters.get(j - i - 1) + String.valueOf(letter);
                        lstLetters.set(j - i - 1, sequence);

                        System.out.println(lstLetters);
                    }
                }

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
                    }
                }
                index++;
            }
            if (totalEqualsLetters>=3) {
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
