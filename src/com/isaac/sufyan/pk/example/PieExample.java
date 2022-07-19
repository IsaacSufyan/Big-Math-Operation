package com.isaac.sufyan.pk.example;

import com.isaac.sufyan.pk.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;


/**
 * This example shows how to calculate pi with different precisions.
 */
public class PieExample {

    public static void main(String[] args) {
        int input = 10;
        MathContext mathContext = new MathContext(input);
        BigDecimal bigDecimalPie = BigDecimalMath.pi(mathContext);
        System.out.printf("First %d Decimal Digits of Pie : %s\n", input - 1, bigDecimalPie);

        BigDecimal bigDecimalE = BigDecimalMath.e(mathContext);
        System.out.printf("First %d Decimal Digits of E : %s\n", input - 1, bigDecimalE);
    }
}
