package com.hyundaiht.composelayouttest.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hyundaiht.composelayouttest.ui.theme.ComposeUiTestTheme
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChipKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val TAG_CHIP_ON_CLICK = "ChipOnClickButton"
    private val TAG_CHIP_ON_REMOVE = "ChipOnRemoveButton"
    private val TAG_CHIP_TEXT = "ChipText"
    private val TAG_CHIP_REMOVE_ICON = "ChipRemoveIcon"

    @Test
    fun chip() {
        var isOnClick: Boolean = false
        var isOnRemove: Boolean = false
        composeTestRule.setContent {
            ComposeUiTestTheme {
                Chip(
                    text = "test",
                    onClick = {
                        isOnClick = true
                    },
                    onRemove = {
                        isOnRemove = true
                    }
                )
            }
        }

        val chipOnClick = composeTestRule.onNodeWithContentDescription(TAG_CHIP_ON_CLICK)
        val chipOnRemove = composeTestRule.onNodeWithContentDescription(TAG_CHIP_ON_REMOVE)
        val chipText = composeTestRule.onNodeWithContentDescription(TAG_CHIP_TEXT)
        val chipRemoveIcon = composeTestRule.onNodeWithContentDescription(TAG_CHIP_REMOVE_ICON)

        //생성 확인
        chipOnClick.assertExists()
        chipOnRemove.assertExists()
        chipText.assertExists()
        chipRemoveIcon.assertExists()

        //이벤트 확인
        chipOnClick.performClick()
        chipOnRemove.performClick()
        Assert.assertTrue(isOnClick)
        Assert.assertTrue(isOnRemove)

        //text 확인
        chipText.assertTextEquals("test")
    }
}