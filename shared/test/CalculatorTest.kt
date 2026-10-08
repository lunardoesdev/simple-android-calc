package org.example.simpleandroidcalc

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorTest {
    @Test
    fun `initial display is zero`() {
        assertEquals("0", Calculator().display)
    }

    @Test
    fun `leading zero is replaced by first digit`() {
        val calc = Calculator()
        calc.digit('7')
        assertEquals("7", calc.display)
    }

    @Test
    fun `addition`() {
        val calc = Calculator()
        calc.digit('2')
        calc.operator(Calculator.Op.Add)
        calc.digit('3')
        calc.equals()
        assertEquals("5", calc.display)
    }

    @Test
    fun `subtraction, multiplication and division`() {
        assertEquals("4", runOp('9', Calculator.Op.Sub, '5'))
        assertEquals("12", runOp('3', Calculator.Op.Mul, '4'))
        assertEquals("3", runOp('9', Calculator.Op.Div, '3'))
    }

    @Test
    fun `chained operations evaluate left to right`() {
        val calc = Calculator()
        calc.digit('2')
        calc.operator(Calculator.Op.Add)
        calc.digit('3')
        calc.operator(Calculator.Op.Mul)
        calc.digit('4')
        calc.equals()
        assertEquals("20", calc.display)
    }

    @Test
    fun `decimal entry`() {
        val calc = Calculator()
        calc.digit('1')
        calc.decimal()
        calc.digit('5')
        calc.operator(Calculator.Op.Add)
        calc.digit('2')
        calc.decimal()
        calc.digit('5')
        calc.equals()
        assertEquals("4", calc.display)
    }

    @Test
    fun `division by zero yields error`() {
        assertEquals("Error", runOp('5', Calculator.Op.Div, '0'))
    }

    @Test
    fun `clear resets state`() {
        val calc = Calculator()
        calc.digit('9')
        calc.operator(Calculator.Op.Add)
        calc.digit('1')
        calc.clear()
        assertEquals("0", calc.display)
        calc.digit('5')
        calc.operator(Calculator.Op.Add)
        calc.digit('5')
        calc.equals()
        assertEquals("10", calc.display)
    }

    @Test
    fun `backspace removes last digit`() {
        val calc = Calculator()
        calc.digit('1')
        calc.digit('2')
        calc.digit('3')
        calc.backspace()
        assertEquals("12", calc.display)
    }

    @Test
    fun `negate toggles sign`() {
        val calc = Calculator()
        calc.digit('4')
        calc.negate()
        assertEquals("-4", calc.display)
        calc.negate()
        assertEquals("4", calc.display)
    }

    private fun runOp(a: Char, op: Calculator.Op, b: Char): String {
        val calc = Calculator()
        calc.digit(a)
        calc.operator(op)
        calc.digit(b)
        calc.equals()
        return calc.display
    }
}
