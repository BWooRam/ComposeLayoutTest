package com.hyundaiht.composelayouttest.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordDotsInputField(
    password: String,
    dotLength: Int,
    onInputPassword: (String) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp)
    ) {
        // 비밀번호 입력 UI (● 점 표시)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(dotLength) { index ->
                Box(
                    modifier = Modifier
                        .size(16.dp) // 원 크기
                        .clip(CircleShape)
                        .background(if (index < password.length) Color.Gray else Color.LightGray)
                )
            }
        }

        // 실제 입력을 위한 TextField (투명 처리)
        BasicTextField(
            value = password,
            onValueChange = { if (it.length <= dotLength) onInputPassword(it) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxSize()
                .alpha(0f) // 완전 투명하게 숨김
        )
    }
}