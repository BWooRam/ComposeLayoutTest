package com.hyundaiht.composelayouttest

import org.junit.Assert
import org.junit.Test
import kotlin.math.round
import kotlin.math.roundToInt

class MainActivityKtTest {

    @Test
    fun mathRoundTest() {
        val dNum1 = 454.54600
        val kotlinRoundToIntResult = dNum1.roundToInt()
        println("mathRoundTest kotlinRoundToIntResult = $kotlinRoundToIntResult")
        Assert.assertEquals(kotlinRoundToIntResult, 455)

        val dNum2 = 454.14600
        val kotlinRoundResult1 = round(dNum2)
        val kotlinRoundResult2 = (round(dNum2 * 100) / 100)
        println("mathRoundTest kotlinRoundResult1 = $kotlinRoundResult1")
        println("mathRoundTest kotlinRoundResult2 = $kotlinRoundResult2")
        Assert.assertTrue(kotlinRoundResult1 == 454.0)
        Assert.assertTrue(kotlinRoundResult2 == 454.15)
    }
}