package com.hyundaiht.composelayouttest.ui

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import com.hyundaiht.composelayouttest.BuildConfig


@SuppressLint("SuspiciousModifierThen")
fun Modifier.debugSemantics(
    mergeDescendants: Boolean = false,
    properties: (SemanticsPropertyReceiver.() -> Unit)
): Modifier = if (BuildConfig.DEBUG) {
    this then semantics(
        mergeDescendants = mergeDescendants,
        properties = properties
    )
} else {
    this
}

