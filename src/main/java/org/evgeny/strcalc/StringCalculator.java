package org.evgeny.strcalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = ",";
    private static final String DELIMITER_OVERRIDE_MARKER = "//";
    private static final int IGNORE_THRESHOLD = 1000;
    private static final String EMPTY_STRING = "";
    private static final String MULTIPLE_DELIMITERS_DELIMITER = "|";

    public int add(String numbers){
        if ("".equals(numbers)){
            return 0;
        }

        String[] splitByNewLine = numbers.split("\n");

        String delimiter = DEFAULT_DELIMITER;
        int startFrom = 0;
        if (splitByNewLine[0].startsWith(DELIMITER_OVERRIDE_MARKER) && splitByNewLine[0].length()>2){
            delimiter = buildDelimiter(splitByNewLine[0].substring(2));
            startFrom = 1;
        }

        int sum = 0;
        List<Integer> negatives = new ArrayList<>();
        for (int i = startFrom;i < splitByNewLine.length;i++){
            String[] splitByDelimiter = splitByNewLine[i].split(delimiter);

            for (String numberAsString : splitByDelimiter) {
                sum += convertToInt(numberAsString, negatives);
            }
        }

        if (negatives.size()>0){
            throw new IllegalArgumentException("negatives not allowed: "+ Arrays.toString(negatives.toArray()));
        }
        return sum;
    }

    private int convertToInt(String numberAsString, List<Integer> negatives) {
        int number = Integer.valueOf(numberAsString);
        if (number < 0){
            negatives.add(number);
            return 0;
        } else if (number > IGNORE_THRESHOLD) {
            //ignore
            return 0;
        } else {
            return number;
        }
    }

    private String buildDelimiter(String delimiters) {
        String result = EMPTY_STRING;
        for (String delimiter : delimiters.split(Pattern.quote(MULTIPLE_DELIMITERS_DELIMITER))){
            if (!EMPTY_STRING.equals(result)){
                result+= MULTIPLE_DELIMITERS_DELIMITER;
            }
            result+=Pattern.quote(delimiter);
        }
        return result;
    }
}
