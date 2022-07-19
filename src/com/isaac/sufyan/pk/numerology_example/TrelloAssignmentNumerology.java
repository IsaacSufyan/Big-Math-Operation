package com.isaac.sufyan.pk.numerology_example;

import com.isaac.sufyan.pk.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TrelloAssignmentNumerology {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Chooses a number between 1 and 1000: ");
        String command = reader.nextLine();

        int input = convertInputToInt(command);
        if (input <= 0) {
            System.out.println("Bad Input");
            return;
        }

        int n = input + 1;

        MathContext mathContext = new MathContext(n);
        BigDecimal bigDecimalPie = BigDecimalMath.pi(mathContext);
        String numberP = String.valueOf(bigDecimalPie);
        numberP = numberP.substring(numberP.indexOf(".")).substring(1);
//        numberP = "1814666323";
        System.out.printf("First %d Decimal Digits of Pie : %s\n", n - 1, numberP);

        BigDecimal bigDecimalE = BigDecimalMath.e(mathContext);
        String numberE = String.valueOf(bigDecimalE);
        numberE = numberE.substring(numberE.indexOf(".")).substring(1);
//        numberE = "7182818284";
        System.out.printf("First %d Decimal Digits of E : %s\n", n - 1, numberE);

        BigInteger pie = new BigInteger(numberP);
        BigInteger e = new BigInteger(numberE);
        BigInteger K = pie.add(e);
        System.out.println("Sum Of Two Number i.e K = " + K);
        int twoDigitPrimeCount = twoDigitPrimeNumber(String.valueOf(K));
        int threeDigitPrimeCount = threeDigitPrimeNumber(String.valueOf(K));
        int result = twoDigitPrimeCount * threeDigitPrimeCount;

        System.out.println("TwoDigitPrimeNumber: " + twoDigitPrimeCount);
        System.out.println("ThreeDigitPrimeNumber: " + threeDigitPrimeCount);
        System.out.println("Result (TwoPrime*ThreePrime): " + result);

        if (result>9){
            int finalResult = regularNumerologyAddition(result);
            System.out.println("Final Result After Regular Expression: "+finalResult);
        }
    }

    private static int twoDigitPrimeNumber(String input) {
        List<String> twoDigitPrimeNumberList = new ArrayList<>();
        int twoDigitPrimeCount = 0;
        for (int i = 0; i < input.length() - 1; i++) {
            char ch = input.charAt(i);
            char chNext = input.charAt(i + 1);
            String s = String.valueOf(ch) + chNext;
            boolean isPrime = isPrime(Integer.parseInt(s));
            if (isPrime) {
                twoDigitPrimeCount++;
                twoDigitPrimeNumberList.add(s);
            }
        }
        System.out.println(twoDigitPrimeNumberList);
        return twoDigitPrimeCount;
    }

    private static int threeDigitPrimeNumber(String input) {
        List<String> threeDigitPrimeNumberList = new ArrayList<>();
        int threeDigitPrimeCount = 0;
        for (int i = 0; i < input.length() - 2; i++) {
            char chFirst = input.charAt(i);
            char chSecond = input.charAt(i + 1);
            char chThird = input.charAt(i + 2);
            String s = String.valueOf(chFirst) + chSecond + chThird;
            boolean isPrime = isPrime(Integer.parseInt(s));
            if (isPrime) {
                threeDigitPrimeCount++;
                threeDigitPrimeNumberList.add(s);
            }
        }
        System.out.println(threeDigitPrimeNumberList);
        return threeDigitPrimeCount;
    }

    private static int convertInputToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (RuntimeException e) {
            System.out.println("BAD Input");
        }
        return 0;
    }

    public static boolean isPrime(int num) {
        boolean flag = true;
        for (int i = 2; i <= num / 2; ++i) {
            if (num % i == 0) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private static int regularNumerologyAddition(int n) {
        int sum = 0;
        while (n > 0 || sum > 9) {
            if (n == 0) {
                n = sum;
                sum = 0;
            }
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }
}
