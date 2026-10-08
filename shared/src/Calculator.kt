package org.example.simpleandroidcalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.abs
import kotlin.math.roundToLong

/**
 * Plain calculator engine. Holds the display text plus the pending operand/operator,
 * exposing mutation methods the UI calls on each key press.
 */
class Calculator {
    enum class Op(val symbol: String) {
        Add("+"),
        Sub("−"),
        Mul("×"),
        Div("÷"),
    }

    var display by mutableStateOf("0")
        private set

    private var accumulator: Double? = null
    private var pending: Op? = null
    private var fresh = true

    fun digit(d: Char) {
        if (d !in '0'..'9') return
        display = when {
            fresh -> d.toString()
            display == "0" -> d.toString()
            else -> display + d
        }
        fresh = false
    }

    fun decimal() {
        if (fresh) {
            display = "0."
            fresh = false
        } else if (!display.contains('.')) {
            display += "."
        }
    }

    fun operator(op: Op) {
        val current = display.toDoubleOrNull() ?: return
        accumulator = if (pending != null && !fresh) {
            apply(accumulator ?: 0.0, current, pending!!)
        } else {
            current
        }
        display = format(accumulator!!)
        pending = op
        fresh = true
    }

    fun equals() {
        val op = pending ?: return
        val current = display.toDoubleOrNull() ?: return
        display = format(apply(accumulator ?: 0.0, current, op))
        accumulator = null
        pending = null
        fresh = true
    }

    fun clear() {
        display = "0"
        accumulator = null
        pending = null
        fresh = true
    }

    fun backspace() {
        if (fresh) return
        display = display.dropLast(1).ifEmpty { "0" }
        if (display == "0") fresh = true
    }

    fun negate() {
        display = when {
            display == "0" -> "0"
            display.startsWith("-") -> display.removePrefix("-")
            else -> "-$display"
        }
    }

    private fun apply(a: Double, b: Double, op: Op): Double = when (op) {
        Op.Add -> a + b
        Op.Sub -> a - b
        Op.Mul -> a * b
        Op.Div -> a / b
    }

    private fun format(v: Double): String {
        if (v.isNaN() || v.isInfinite()) return "Error"
        val rounded = if (abs(v) < 1e15) (v * 1e10).roundToLong() / 1e10 else v
        return if (rounded == rounded.toLong().toDouble()) {
            rounded.toLong().toString()
        } else {
            rounded.toString()
        }
    }
}
