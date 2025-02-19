package com.hyundaiht.composelayouttest.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hyundaiht.composelayouttest.ui.theme.ComposeUiTestTheme
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PasswordDotsInputFieldKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val TAG_PASSWORD_TEXT_FIELD = "PasswordTextField"
    private val TAG_PASSWORD_DOT_ROW = "PasswordDotRow"
    private val TAG_PASSWORD_DOT = "PasswordDot"

    @Test
    fun passwordDotsInputField() {
        val statePassword = mutableStateOf("")
        composeTestRule.setContent {
            ComposeUiTestTheme {
                var rememberPassword by remember { statePassword }

                PasswordDotsInputField(
                    password = rememberPassword,
                    dotLength = 4,
                    onInputPassword = {
                        rememberPassword = it
                    },
                )
            }
        }

        val passwordDotRow = composeTestRule.onNodeWithContentDescription(TAG_PASSWORD_DOT_ROW)
        val passwordTextField = composeTestRule.onNodeWithContentDescription(TAG_PASSWORD_TEXT_FIELD)
        val passwordDotsInputField = composeTestRule.onNode(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))
        val beforePasswordDot = composeTestRule.onAllNodes(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "StateNotInput"))

        //생성 확인
        passwordDotRow.assertExists()
        passwordTextField.assertExists()
        passwordDotsInputField.assertExists()

        //Dot 갯수 확인
        passwordDotRow.onChildren().assertCountEquals(4)
        beforePasswordDot.printToLog("beforePasswordDot")
        beforePasswordDot.assertCountEquals(4)

        //이벤트 확인
        passwordTextField.performTextInput("1")
        composeTestRule.onAllNodes(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "StateInput")).assertCountEquals(1)
        Assert.assertTrue(statePassword.value == "1")
        passwordTextField.performTextInput("2")
        composeTestRule.onAllNodes(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "StateInput")).assertCountEquals(2)
        Assert.assertTrue(statePassword.value == "12")
        passwordTextField.performTextInput("3")
        composeTestRule.onAllNodes(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "StateInput")).assertCountEquals(3)
        Assert.assertTrue(statePassword.value == "123")
        passwordTextField.performTextInput("4")
        composeTestRule.onAllNodes(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "StateInput")).assertCountEquals(4)
        Assert.assertTrue(statePassword.value == "1234")
    }
}