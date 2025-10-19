package calculator.service;

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
}
