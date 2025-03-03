package com.xenikii.newsapp.features.home.ui.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.xenikii.newsapp.features.home.view_model.HomeViewModel

@Composable
fun HomeSearchField(viewModel: HomeViewModel) {
    val style = MaterialTheme.typography.bodyLarge.copy(
        fontSize = 14.sp,
        lineHeight = 14.sp
    )
    val pageOptions = viewModel.pageOptions.collectAsState()
    var searchText by remember {
        mutableStateOf(pageOptions.value.searchParams.query ?: "")
    }

    OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
            viewModel.fetchByQuery(it)
        },
        leadingIcon = {
            Icon(
                Icons.Filled.Search, contentDescription = "Search",
            )
        },
        maxLines = 1,
        placeholder = { Text("Search", style = style) },
        modifier = Modifier.minimumInteractiveComponentSize(),
        textStyle = style,
        singleLine = true,
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = {
                    searchText = ""
                    viewModel.fetchByQuery("")
                }) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Clear text field"
                    )
                }
            }
        }
    )
}