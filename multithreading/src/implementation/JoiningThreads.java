package implementation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoiningThreads {

    public static class FactorialCalculation  extends  Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialCalculation(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i=n ;i>0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return  result;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(1000000L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);
        List<FactorialCalculation> threads = new ArrayList<>();
        for (long inputNumber: inputNumbers) {
            threads.add(new FactorialCalculation(inputNumber));
        }

        for (Thread thread: threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (Thread thread: threads) {
            thread.join(2000);
        }

        for (int i=0; i<inputNumbers.size(); i++) {
            FactorialCalculation factorialCalculation = threads.get(i);
            if (factorialCalculation.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialCalculation.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }
}
