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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hyundaiht.composelayouttest.ui.InputAndSelectTagScreen
import com.hyundaiht.composelayouttest.ui.PasswordDotsInputField
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
                        PasswordDotsInputField(
                            password = rememberPassword,
                            dotLength = 6,
                            onInputPassword = {
                                Log.d(tag, "PasswordDotsInput onPasswordChange password = $it")
                                rememberPassword = it
                            }
                        )

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

}