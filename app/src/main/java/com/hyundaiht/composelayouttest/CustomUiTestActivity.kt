package com.hyundaiht.composelayouttest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyundaiht.composelayouttest.ui.InputAndSelectTagScreen
import com.hyundaiht.composelayouttest.ui.TagInputField
import com.hyundaiht.composelayouttest.ui.WeekdaySelectorScreen
import com.hyundaiht.composelayouttest.ui.theme.ComposeUiTestTheme

class CustomUiTestActivity : ComponentActivity() {
    private val tag = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeUiTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(state)
                    ) {
                        //비밀번호 입력 UI
                        var rememberPassword by remember { mutableStateOf("") }
                        PasswordDotsInputField(rememberPassword) {
                            Log.d(tag, "PasswordDotsInput onPasswordChange password = $it")
                            rememberPassword = it
                        }

                        //요일 선택 UI
                        WeekdaySelectorScreen {
                            Log.d(tag, "WeekdaySelectorScreen onSelectedDay = $it")
                        }

                        //태그 입력 UI
                        InputAndSelectTagScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun PasswordDotsInputField(password: String, onPasswordChange: (String) -> Unit) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 비밀번호 입력 UI (● 점 표시)
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(6) { index ->
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
                onValueChange = { if (it.length <= 6) onPasswordChange(it) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp) // 사용자 입력 가능하도록 영역 확보
                    .alpha(0f) // 완전 투명하게 숨김
            )
        }
    }



}