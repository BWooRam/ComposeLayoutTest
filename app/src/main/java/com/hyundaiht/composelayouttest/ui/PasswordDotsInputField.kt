package com.hyundaiht.composelayouttest.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PasswordDotsInputField(
    password: String,
    dotLength: Int,
    onInputPassword: (String) -> Unit,
    dotSize: Dp = 16.dp,
    dotSpaceBy: Dp = 12.dp,
    defaultDotColor: Color = Color.LightGray,
    activateDotColor: Color = Color.Gray,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        // 비밀번호 입력 UI (● 점 표시)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .debugSemantics {
                    contentDescription = "PasswordDotRow"
                },
            horizontalArrangement = Arrangement.spacedBy(dotSpaceBy, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(dotLength) { index ->
                Box(
                    modifier = Modifier
                        .size(dotSize) // 원 크기
                        .clip(CircleShape)
                        .background(if (index < password.length) activateDotColor else defaultDotColor)
                        .debugSemantics {
                            contentDescription = "PasswordDot$index"
                            onClick(label = "PasswordDotOnClick", action = { return@onClick false })
                        }
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
                .debugSemantics {
                    contentDescription = "PasswordTextField"
                },
            singleLine = true
        )
    }
}

@Composable
fun PasswordInputField2(
    password: String,
    dotLength: Int = 6,
    onInputPassword: (String) -> Unit,
    dotSize: Dp = 16.dp,
    dotSpaceBy: Dp = 12.dp,
    defaultDotColor: Color = Color.LightGray,
    activateDotColor: Color = Color.Gray,
) {
    BasicTextField(
        value = password,
        onValueChange = {
            Log.d("PasswordInputField", "onValueChange password = $password")
            if (it.length <= dotLength) onInputPassword.invoke(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
        decorationBox = { innerTextField ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = dotSpaceBy,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .debugSemantics {
                        contentDescription = "PasswordDotRow"
                    },
            ) {
                for (index in 0 until dotLength) {
                    Box(
                        modifier = Modifier
                            .size(dotSize)
                            .background(
                                color = if (index < password.length) activateDotColor else defaultDotColor,
                                shape = CircleShape
                            ).debugSemantics {
                                contentDescription = "PasswordDot$index"
                            },
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        singleLine = true
    )
}
