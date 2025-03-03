package com.xenikii.newsapp.features.filter.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xenikii.newsapp.data.model.NewsSearchParams
import com.xenikii.newsapp.data.model.SearchScope
import com.xenikii.newsapp.data.model.SearchSortBy
import com.xenikii.newsapp.data.model.SourceItem
import com.xenikii.newsapp.features.filter.view_model.FilterViewModel
import com.xenikii.newsapp.shared.extensions.displayName
import com.xenikii.newsapp.shared.ui.elements.CustomCheckboxListTile
import com.xenikii.newsapp.shared.ui.elements.CustomRadioListTile
import com.xenikii.newsapp.shared.ui.elements.ErrorSnack

@Composable
fun FilterBottomSheet(
    onSubmit: (NewsSearchParams) -> Unit = {},
    onCancel: () -> Unit = {},
    viewModel: FilterViewModel
) {
    val state by viewModel.currentParams.collectAsState()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(Modifier.align(Alignment.End)) {
            TextButton(
                onClick = { viewModel.reset() }, colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Reset")
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item { Title(title = "Sort by") }
                item {
                    SortItems(
                        onChanged = { viewModel.setSort(it) }, selected = state.sortBy
                    )
                }
                item { Title(title = "Search in") }
                item {
                    SearchInItems(
                        onChanged = { viewModel.toggleScope(it) },
                        selected = state.scopes
                    )
                }
                item { Title(title = "Include searches from") }
                item {
                    SourcesItems(
                        onChanged = { viewModel.toggleSource(it) },
                        selected = state.sources,
                        sources = viewModel.availableSources
                    )
                }
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.hasChanges.collectAsState().value,
            onClick = {
                val error = viewModel.validate()
                if (error == null) {
                    onSubmit(viewModel.currentParams.value)
                } else {
                    errorMessage.value = error
                }
            }) {
            Text("Submit")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            onClick = { onCancel() }) {
            Text("Cancel")
        }
    }
    ErrorSnack(
        message = errorMessage.value ?: "",
        isShowing = errorMessage.value != null,
        onFinished = { errorMessage.value = null }
    )
}


@Composable
private fun Title(title: String) {
    return Text(
        title, style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun SortItems(onChanged: (SearchSortBy) -> Unit, selected: SearchSortBy) {
    return Column {
        SearchSortBy.entries.forEach {
            CustomRadioListTile(
                value = it,
                selectedValue = selected,
                onChanged = onChanged,
                buildName = { it.displayName() })
        }
    }
}

@Composable
private fun SearchInItems(onChanged: (SearchScope) -> Unit, selected: List<SearchScope>) {
    return Column {
        SearchScope.entries.forEach { scope ->
            CustomCheckboxListTile(
                value = scope,
                selectedValues = selected,
                onChanged = { onChanged(scope) },
                buildName = { it.displayName() })
        }
    }
}

@Composable
private fun SourcesItems(
    onChanged: (SourceItem) -> Unit,
    selected: List<SourceItem>,
    sources: List<SourceItem>
) {
    return Column {
        sources.forEach { source ->
            CustomCheckboxListTile<SourceItem>(
                value = source,
                selectedValues = selected,
                onChanged = { onChanged(source) },
                buildName = { it.name })
        }
    }
}