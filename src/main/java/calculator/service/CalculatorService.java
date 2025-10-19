package calculator.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CalculatorService {
    public String getDelimiter(String input){
        if(isDelimiter(input)){
            return String.valueOf(input.charAt(2));
        }
        return null;
    }

    public boolean isDelimiter(String input){
        if(input.length() >= 5
        && input.charAt(0) == '/'
        && input.charAt(1) == '/'
        && input.charAt(3) == '\\'
        && input.charAt(4) == 'n'){
            return true;
        }
        return false;
    }

    public List<BigInteger> getNumbers(String input, String customDelimiter){
        String numbers = input;
        String delimiter = ",|:";

        if(customDelimiter != null){
            numbers = input.substring(5);
            delimiter += "|" + customDelimiter;
        }

        List<BigInteger> numberList = new ArrayList<>();
        String[] splitedNumbers = numbers.split(delimiter, -1);

        for(String i : splitedNumbers){
            numberList.add(new BigInteger(i));
        }

        return numberList;
    }

    public BigInteger getSum(List<BigInteger> numberList){
        BigInteger sum = BigInteger.ZERO;

        for(BigInteger i : numberList){
            sum = sum.add(i);
        }

        return sum;
    }
}
