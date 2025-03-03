package com.xenikii.newsapp.shared.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun <T> CustomCheckboxListTile(
    value: T,
    selectedValues: List<T>,
    onChanged: (Boolean) -> Unit,
    buildName: (T) -> String,
) {
    val isSelected = selectedValues.contains(value)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(
                selected = (isSelected),
                onClick = { onChanged(!isSelected) },
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.weight(1f)) {
            Text(
                buildName(value)
            )
        }

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onChanged(it) },
        )

    }
}