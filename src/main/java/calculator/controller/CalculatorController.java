package calculator.controller;

import calculator.service.CalculatorService;
import calculator.view.InputView;
import calculator.view.OutputView;
import java.math.BigInteger;
import java.util.List;

public class CalculatorController {
    private final CalculatorService calculatorService;
    private final InputView inputView;
    private final OutputView outputView;

    public CalculatorController() {
        this.calculatorService = new CalculatorService();
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run(){
        String input = inputView.inputNumber();

        if(calculatorService.checkEmpty(input)) {
            outputView.printResult(BigInteger.ZERO);
            return;
        }

        String customDelimiter = calculatorService.getDelimiter(input);

        List<BigInteger> numberList =calculatorService.getNumbers(input, customDelimiter);

        BigInteger sum = calculatorService.getSum(numberList);

        outputView.printResult(sum);
    }
}
