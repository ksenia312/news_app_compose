package com.xenikii.newsapp.shared.ui.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun <T> CustomRadioListTile(
    value: T,
    selectedValue: T,
    onChanged: (T) -> Unit,
    buildName: (T) -> String,
) {
    val isSelected = value == selectedValue
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(
                selected = (isSelected),
                onClick = { onChanged(value) },
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onChanged(value) },
        )
        Text(
            buildName(value)
        )
    }
}