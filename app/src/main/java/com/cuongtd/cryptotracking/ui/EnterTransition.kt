package com.cuongtd.cryptotracking.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable

@ExperimentalAnimationApi
@Composable
fun EnterTransition(
    content: @Composable () -> Unit,
) {
    if (true) {
        AnimatedVisibility(
            visible = true,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { -it }),
            content = content,
            initiallyVisible = false
        )
    } else {
        content()
    }
}