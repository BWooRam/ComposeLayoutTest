package com.hyundaiht.composelayouttest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hyundaiht.composelayouttest.ui.InputAndSelectTagScreen
import com.hyundaiht.composelayouttest.ui.PasswordDotsInputField
import com.hyundaiht.composelayouttest.ui.PasswordInputField2
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
                        //테스트 필요 - 회전, 생명주기 등..

                        //비밀번호 입력 UI - 1
                        var rememberPassword by remember { mutableStateOf("") }
                        PasswordDotsInputField(
                            password = rememberPassword,
                            dotLength = 6,
                            onInputPassword = {
                                Log.d(tag, "PasswordDotsInput onPasswordChange password = $it")
                                rememberPassword = it
                            }
                        )
                        HorizontalDivider()

                        //비밀번호 입력 UI - 2
                        PasswordInputField2(
                            password = rememberPassword,
                            dotLength = 6,
                            onInputPassword = {
                                Log.d(tag, "PasswordInputField2 onPasswordChange password = $it")
                                rememberPassword = it
                            })
                        HorizontalDivider()

                        //요일 선택 UI
                        WeekdaySelectorScreen {
                            Log.d(tag, "WeekdaySelectorScreen onSelectedDay = $it")
                        }
                        HorizontalDivider()

                        //태그 입력 UI
                        InputAndSelectTagScreen()
                        HorizontalDivider()
                    }
                }
            }
        }
    }

}