package com.hyundaiht.composelayouttest.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp


@Composable
fun Chip(
    text: String,
    onClick: ((String) -> Unit)? = null,
    onRemove: ((String) -> Unit)? = null
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentSize()
            .background(Color.Gray, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(
                onClick = { onClick?.invoke(text) },
                modifier = Modifier
                    .wrapContentSize()
                    .debugSemantics {
                        contentDescription = "ChipOnClickButton"
                    }) {
                Text(
                    modifier = Modifier.debugSemantics { contentDescription = "ChipText" },
                    text = text,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(4.dp))

            if (onRemove != null) {
                IconButton(
                    onClick = { onRemove.invoke(text) },
                    modifier = Modifier
                        .size(16.dp)
                        .debugSemantics {
                            contentDescription = "ChipOnRemoveButton"
                        }) {
                    Icon(Icons.Default.Close, contentDescription = "ChipRemoveIcon", tint = Color.White)
                }
            }
        }
    }
}