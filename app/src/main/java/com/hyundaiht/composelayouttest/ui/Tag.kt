package com.hyundaiht.composelayouttest.ui

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagInputField(
    tags: List<String>,
    onCreatedTag: (String) -> Unit,
    onRemoveTag: (String) -> Unit,
    onClearTag: () -> Unit,
    tagMaxSize: Int = 3,
    tagMaxLength: Int = 20
) {
    val context = LocalContext.current
    var textState by remember { mutableStateOf(TextFieldValue(text = "", selection = TextRange(0))) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                // 태그 리스트 (Chip 형태)
                FlowRow(
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = 40.dp)
                        .wrapContentHeight()
                        .align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    //태그 생성
                    tags.forEach { tag ->
                        Chip(text = tag, onRemove = onRemoveTag)
                    }

                    //입력 창 생성
                    if (tags.size < 3) {
                        BasicTextField(
                            value = textState,
                            onValueChange = { if (it.text.length <= tagMaxLength) textState = it },
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                                .onKeyEvent { keyEvent ->
                                    if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == Key.Backspace) {
                                        //Backspace 이벤트
                                        if(textState.text.isEmpty() && tags.isNotEmpty()){
                                            val lastTag = tags.last()
                                            textState = TextFieldValue(lastTag, selection = TextRange(lastTag.length))
                                            onRemoveTag.invoke(lastTag)
                                        }
                                        true
                                    } else {
                                        false
                                    }
                                },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                Log.d("TagInputField", "text = ${textState.text}")
                                val newTag = textState.text.trim()
                                if (newTag.isNotEmpty() && newTag !in tags && tags.size < tagMaxSize) {
                                    Log.d("TagInputField", "onUpdate")
                                    onCreatedTag.invoke(textState.text)
                                    textState = TextFieldValue("")
                                }
                            }),
                            singleLine = true
                        )
                    }
                }

                if (tags.isNotEmpty()) {
                    // X 버튼 (모든 태그 삭제)
                    IconButton(
                        modifier = Modifier
                            .size(40.dp, 40.dp)
                            .align(Alignment.CenterVertically),
                        onClick = onClearTag
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear tags")
                    }
                }
            }
        }
    }
}

private val startGroupTag = listOf<String>("우리집")

@SuppressLint("MutableCollectionMutableState")
@Composable
fun InputAndSelectTagScreen() {
    var rememberTargetGroupTag by remember { mutableStateOf(listOf<String>()) }
    var rememberWaitingGroupTag by remember { mutableStateOf(startGroupTag) }
    Log.d("tag", "InputAndSelectTagScreen rememberTargetGroupTag = $rememberTargetGroupTag")
    Log.d("tag", "InputAndSelectTagScreen rememberWaitingGroupTag = $rememberWaitingGroupTag")

    Column {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "그룹 이름",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        TagInputField(
            tags = rememberTargetGroupTag,
            onCreatedTag = {
                Log.d("tag", "TagInputField onCreatedTag tag = $it")
                rememberTargetGroupTag = rememberTargetGroupTag + it

                if (rememberWaitingGroupTag.contains(it)) {
                    rememberWaitingGroupTag = rememberWaitingGroupTag - it
                }
            },
            onRemoveTag = {
                Log.d("tag", "TagInputField onRemoveTag tag = $it")
                rememberTargetGroupTag = rememberTargetGroupTag - it

                if (startGroupTag.contains(it) && !rememberWaitingGroupTag.contains(it)) {
                    rememberWaitingGroupTag = rememberWaitingGroupTag + it
                }
            },
            onClearTag = {
                Log.d("tag", "TagInputField onClearTag")
                rememberTargetGroupTag = listOf()
            }
        )
        TagList(
            tags = rememberWaitingGroupTag,
            onSelectedTag = { selectedTag ->
                Log.d("tag", "TagList onSelectedTag tag = $selectedTag")
                rememberTargetGroupTag = rememberTargetGroupTag + selectedTag
                rememberWaitingGroupTag = rememberWaitingGroupTag - selectedTag
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagList(
    tags: List<String>,
    onSelectedTag: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        for (tag in tags) {
            Chip(text = tag, onClick = {
                onSelectedTag.invoke(it)
            })
        }
    }
}