package com.isaac.sufyan.pk.numerology_example

import com.isaac.sufyan.pk.big.BigDecimalMath
import java.math.BigInteger
import java.math.MathContext
import java.util.*

fun main() {
    println("Chooses a number between 1 and 1000: ")
    val command = readLine()!!

    var n = convertInputToInt(command)
    if (n <= 0) {
        println("Bad Input")
        return
    }

    n++

    val mathContext = MathContext(n)
    val bigDecimalPie = BigDecimalMath.pi(mathContext)
    var numberP = bigDecimalPie.toString()
    numberP = numberP.substring(numberP.indexOf(".")).substring(1)
    System.out.printf("First %d Decimal Digits of Pie : %s\n", n - 1, numberP)

    val bigDecimalE = BigDecimalMath.e(mathContext)
    var numberE = bigDecimalE.toString()
    numberE = numberE.substring(numberE.indexOf(".")).substring(1)
//        numberE = "7182818284"

    var lastNumber = numberE.substring(numberE.length - 1)
    if (lastNumber.toInt() >= 5) {
        lastNumber = (lastNumber.toInt() - 1).toString()
        numberE = numberE.substring(0, numberE.length - 1)
        numberE += lastNumber
    }

    System.out.printf("First %d Decimal Digits of E : %s\n", n - 1, numberE)

    val pie = BigInteger(numberP)
    val e = BigInteger(numberE)
    val sumOfPieAndE = pie.add(e)
    println("Sum Of Two Number i.e K = $sumOfPieAndE")
    val twoDigitPrimeCount = twoDigitPrimeNumber(sumOfPieAndE.toString())
    val threeDigitPrimeCount = threeDigitPrimeNumber(sumOfPieAndE.toString())
    val result = twoDigitPrimeCount * threeDigitPrimeCount

    println("TwoDigitPrimeNumber: $twoDigitPrimeCount")
    println("ThreeDigitPrimeNumber: $threeDigitPrimeCount")
    println("Result (TwoPrime*ThreePrime): $result")

    if (result > 9) {
        val finalResult = regularNumerologyAddition(result)
        println("Final Result After Regular Expression: $finalResult")
    }
}

private fun twoDigitPrimeNumber(input: String): Int {
    val twoDigitPrimeNumberList: MutableList<String> = ArrayList()
    var twoDigitPrimeCount = 0
    for (i in 0 until input.length - 1) {
        val ch = input[i]
        val chNext = input[i + 1]
        val s = ch.toString() + chNext
        val isPrime = isPrime(s.toInt())
        if (isPrime) {
            twoDigitPrimeCount++
            twoDigitPrimeNumberList.add(s)
        }
    }
    println(twoDigitPrimeNumberList)
    return twoDigitPrimeCount
}

private fun threeDigitPrimeNumber(input: String): Int {
    val threeDigitPrimeNumberList: MutableList<String> = ArrayList()
    var threeDigitPrimeCount = 0
    for (i in 0 until input.length - 2) {
        val chFirst = input[i]
        val chSecond = input[i + 1]
        val chThird = input[i + 2]
        val s = chFirst.toString() + chSecond + chThird
        val isPrime = isPrime(s.toInt())
        if (isPrime) {
            threeDigitPrimeCount++
            threeDigitPrimeNumberList.add(s)
        }
    }
    println(threeDigitPrimeNumberList)
    return threeDigitPrimeCount
}

private fun convertInputToInt(input: String): Int {
    try {
        return input.toInt()
    } catch (e: RuntimeException) {
        println("BAD Input")
    }
    return 0
}

private fun isPrime(num: Int): Boolean {
    var flag = true
    for (i in 2..num / 2) {
        if (num % i == 0) {
            flag = false
            break
        }
    }
    return flag
}

private fun regularNumerologyAddition(num: Int): Int {
    var n = num
    var sum = 0
    while (n > 0 || sum > 9) {
        if (n == 0) {
            n = sum
            sum = 0
        }
        sum += n % 10
        n /= 10
    }
    return sum
}
