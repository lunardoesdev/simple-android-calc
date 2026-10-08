package org.example.simpleandroidcalc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorScreen() {
    val calc = remember { Calculator() }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Display(calc.display, Modifier.weight(1f).fillMaxWidth())

            CalcRow {
                Key("C", onClick = calc::clear)
                Key("±", onClick = calc::negate)
                Key("⌫", onClick = calc::backspace)
                OpKey("÷") { calc.operator(Calculator.Op.Div) }
            }
            CalcRow {
                Key("7") { calc.digit('7') }
                Key("8") { calc.digit('8') }
                Key("9") { calc.digit('9') }
                OpKey("×") { calc.operator(Calculator.Op.Mul) }
            }
            CalcRow {
                Key("4") { calc.digit('4') }
                Key("5") { calc.digit('5') }
                Key("6") { calc.digit('6') }
                OpKey("−") { calc.operator(Calculator.Op.Sub) }
            }
            CalcRow {
                Key("1") { calc.digit('1') }
                Key("2") { calc.digit('2') }
                Key("3") { calc.digit('3') }
                OpKey("+") { calc.operator(Calculator.Op.Add) }
            }
            CalcRow {
                Key("0", Modifier.weight(2f)) { calc.digit('0') }
                Key(".") { calc.decimal() }
                OpKey("=") { calc.equals() }
            }
        }
    }
}

@Composable
private fun Display(text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.BottomEnd) {
        Text(
            text = text,
            style = MaterialTheme.typography.displaySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )
    }
}

@Composable
private fun ColumnScope.CalcRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().weight(1f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = content,
    )
}

@Composable
private fun RowScope.Key(
    label: String,
    modifier: Modifier = Modifier.weight(1f),
    onClick: () -> Unit,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier.fillMaxHeight(),
    ) {
        Text(label, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
private fun RowScope.OpKey(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.weight(1f).fillMaxHeight(),
    ) {
        Text(label, style = MaterialTheme.typography.headlineSmall)
    }
}
