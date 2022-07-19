package com.isaac.sufyan.pk.example;

import com.isaac.sufyan.pk.big.DefaultBigDecimalMath;
import com.isaac.sufyan.pk.big.stream.BigDecimalStream;

import java.math.BigDecimal;

import static com.isaac.sufyan.pk.big.DefaultBigDecimalMath.*;
import static java.math.BigDecimal.*;

public class DefaultBigDecimalMathExample {
    public static void main(String[] args) {
        runDefaultExample();
        runCreateLocalMathContextExample();
        runWithLocalMathContextExample();
        runWithLocalMathContextCompactExample();
        runWithLocalMathContextParallelBadExample();
        runWithLocalMathContextParallelGoodExample();
        runPiChudnovskyExample();
    }

    private static void runDefaultExample() {
        System.out.println("Pi[default]: " + pi());
    }

    private static void runPiChudnovskyExample() {
        withLocalMathContext(10, () -> {
            System.out.println("Pi[" + currentMathContext().getPrecision() + "]: " + piChudnovsky());
            System.out.println("Pi[" + currentMathContext().getPrecision() + "]: " + piChudnovskyMixedArithmetic());
        });
        withLocalMathContext(1000, () -> {
            System.out.println("Pi[" + currentMathContext().getPrecision() + "]: " + piChudnovsky());
            System.out.println("Pi[" + currentMathContext().getPrecision() + "]: " + piChudnovskyMixedArithmetic());
        });
    }

    private static BigDecimal piChudnovsky() {
        final BigDecimal value24 = valueOf(24);
        final BigDecimal value640320 = valueOf(640320);
        final BigDecimal value13591409 = valueOf(13591409);
        final BigDecimal value545140134 = valueOf(545140134);
        final BigDecimal valueDivisor = divide(pow(value640320, 3), value24);

        BigDecimal sumA = ONE;
        BigDecimal sumB = ZERO;

        BigDecimal a = ONE;
        long dividendTerm1 = 5; // -(6*k - 5)
        long dividendTerm2 = -1; // 2*k - 1
        long dividendTerm3 = -1; // 6*k - 1
        BigDecimal kPower3;

        long iterationCount = (currentMathContext().getPrecision()+13) / 14;
        for (long k = 1; k <= iterationCount; k++) {
            BigDecimal valueK = valueOf(k);
            dividendTerm1 += -6;
            dividendTerm2 += 2;
            dividendTerm3 += 6;
            BigDecimal dividend = multiply(multiply(valueOf(dividendTerm1), valueOf(dividendTerm2)), valueOf(dividendTerm3));
            kPower3 = pow(valueK, 3);
            BigDecimal divisor = multiply(kPower3, valueDivisor);
            a = divide(multiply(a, dividend), divisor);
            BigDecimal b = multiply(valueK, a);

            sumA = add(sumA, a);
            sumB = add(sumB, b);
        }

        final BigDecimal value426880 = valueOf(426880);
        final BigDecimal value10005 = valueOf(10005);
        final BigDecimal factor = multiply(value426880, sqrt(value10005));
        BigDecimal pi = divide(factor, add(multiply(value13591409, sumA), multiply(value545140134, sumB)));
        return pi;
    }

    private static BigDecimal piChudnovskyMixedArithmetic() {
        final BigDecimal value24 = valueOf(24);
        final BigDecimal value640320 = valueOf(640320);
        final BigDecimal value13591409 = valueOf(13591409);
        final BigDecimal value545140134 = valueOf(545140134);
        final BigDecimal valueDivisor = pow(value640320, 3).divide(value24, currentMathContext());

        BigDecimal sumA = ONE;
        BigDecimal sumB = ZERO;

        BigDecimal a = ONE;
        long dividendTerm1 = 5; // -(6*k - 5)
        long dividendTerm2 = -1; // 2*k - 1
        long dividendTerm3 = -1; // 6*k - 1
        BigDecimal kPower3;

        long iterationCount = (currentMathContext().getPrecision()+13) / 14;
        for (long k = 1; k <= iterationCount; k++) {
            BigDecimal valueK = valueOf(k);
            dividendTerm1 += -6;
            dividendTerm2 += 2;
            dividendTerm3 += 6;
            BigDecimal dividend = valueOf(dividendTerm1).multiply(valueOf(dividendTerm2)).multiply(valueOf(dividendTerm3));
            kPower3 = pow(valueK, 3);
            BigDecimal divisor = kPower3.multiply(valueDivisor);
            a = a.multiply(dividend).divide(divisor, currentMathContext());
            BigDecimal b = valueK.multiply(a);

            sumA = sumA.add(a);
            sumB = sumB.add(b);
        }

        final BigDecimal value426880 = valueOf(426880);
        final BigDecimal value10005 = valueOf(10005);
        final BigDecimal factor = value426880.multiply(sqrt(value10005));
        BigDecimal pi = factor.divide(value13591409.multiply(sumA).add(value545140134.multiply(sumB)), currentMathContext());
        return pi;
    }

    private static void runCreateLocalMathContextExample() {
        System.out.println("Pi[default]: " + pi());
        try (DefaultBigDecimalMath.LocalMathContext context = DefaultBigDecimalMath.createLocalMathContext(5)) {
            System.out.println("Pi[5]: " + pi());
            try (DefaultBigDecimalMath.LocalMathContext context2 = DefaultBigDecimalMath.createLocalMathContext(10)) {
                System.out.println("Pi[10]: " + pi());
            }
            System.out.println("Pi[5]: " + pi());
        }
        System.out.println("Pi[default]: " + pi());
    }

    private static void runWithLocalMathContextExample() {
        System.out.println("Pi[default]: " + pi());
        DefaultBigDecimalMath.withLocalMathContext(5, () -> {
            System.out.println("Pi[5]: " + pi());
            DefaultBigDecimalMath.withLocalMathContext(10, () -> {
                System.out.println("Pi[10]: " + pi());
            });
            System.out.println("Pi[5]: " + pi());
        });
        System.out.println("Pi[default]: " + pi());
    }

    private static void runWithLocalMathContextCompactExample() {
        System.out.println("Pi[default]: " + pi());
        withLocalMathContext(5, () -> {
            System.out.println("Pi[5]: " + pi());
            withLocalMathContext(10, () -> {
                System.out.println("Pi[10]: " + pi());
            });
            System.out.println("Pi[5]: " + pi());
        });
        System.out.println("Pi[default]: " + pi());
    }

    private static void runWithLocalMathContextParallelBadExample() {
        DefaultBigDecimalMath.withLocalMathContext(5, () -> {
            BigDecimalStream.range(0.0, 1.0, 0.01, currentMathContext())
                    .map(b -> DefaultBigDecimalMath.cos(b))
                    .map(b -> "sequential " + Thread.currentThread().getName() + " [5]: " + b)
                    .forEach(System.out::println);

            BigDecimalStream.range(0.0, 1.0, 0.01, currentMathContext())
                    .parallel()
                    .map(b -> DefaultBigDecimalMath.cos(b))
                    .map(b -> "parallel " + Thread.currentThread().getName() + " [?]: " + b)
                    .forEach(System.out::println);
        });
    }

    private static void runWithLocalMathContextParallelGoodExample() {
        try (DefaultBigDecimalMath.LocalMathContext context = DefaultBigDecimalMath.createLocalMathContext(5)) {
            BigDecimalStream.range(0.0, 1.0, 0.01, currentMathContext())
                    .map(b -> DefaultBigDecimalMath.cos(b))
                    .map(b -> "sequential " + Thread.currentThread().getName() + " [5]: " + b)
                    .forEach(System.out::println);

            BigDecimalStream.range(0.0, 1.0, 0.01, currentMathContext())
                    .parallel()
                    .map(b -> {
                        try (DefaultBigDecimalMath.LocalMathContext context2 = DefaultBigDecimalMath.createLocalMathContext(5)) {
                            return DefaultBigDecimalMath.cos(b);
                        }
                    })
                    .map(b -> "parallel " + Thread.currentThread().getName() + " [5]: " + b)
                    .forEach(System.out::println);
        }
    }
}

