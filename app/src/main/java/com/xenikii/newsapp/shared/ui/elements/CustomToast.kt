package com.xenikii.newsapp.shared.ui.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ErrorSnack(
    message: String? = null,
    isShowing: Boolean = false,
    duration: Long = 4000L,
    onFinished: () -> Unit = {},
) {
    CustomSnack(
        message = message,
        icon = Icons.Filled.Warning,
        isShowing = isShowing,
        duration = duration,
        backgroundColor = MaterialTheme.colorScheme.error,
        foregroundColor = MaterialTheme.colorScheme.onError,
        onFinished = onFinished,
    )
}


@Composable
fun CustomSnack(
    message: String? = null,
    icon: ImageVector,
    isShowing: Boolean = false,
    duration: Long = 3000L,
    backgroundColor: Color,
    foregroundColor: Color,
    onFinished: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    var visible by remember(isShowing) { mutableStateOf(false) }

    LaunchedEffect(isShowing) {
        if (isShowing) {
            visible = true
        }
        if (visible && duration > 0) {
            scope.launch {
                delay(duration)
                onFinished()
                visible = false
            }
        }
    }


    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(1000, easing = FastOutSlowInEasing)
        ),
        exit = ExitTransition.None,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp)),
                color = backgroundColor,
                shadowElevation = 2.dp,
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Icon",
                        tint = foregroundColor
                    )
                    if (message != null) {
                        Text(
                            text = message,
                            color = foregroundColor,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}