package calculator.service;

import java.util.ArrayList;
import java.util.List;

public class CalculatorService {
    public String checkDelimiter(String input){
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

    public List<Integer> getNumbers(String input, String customDelimiter){
        List<Integer> numberList = new ArrayList<>();
        String[] splitedNumbers = input.split(",|:" + customDelimiter);

        for(String i : splitedNumbers){
            numberList.add(Integer.parseInt(i));
        }

        return numberList;
    }
}
