package com.hyundaiht.composelayouttest.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val koreanDays = listOf("일", "월", "화", "수", "목", "금", "토")
val englishDays = listOf("M", "T", "W", "T", "F", "S", "S")
val JapaneseDays = listOf("月", "火", "水", "木", "金", "土", "日")
val ChineseDays = listOf("一", "二", "三", "四", "五", "六", "日")

@Composable
fun WeekdaySelector(
    days: List<String>,
    selectedDays: List<String>,
    onDaySelected: (String) -> Unit,
    daySize: Dp = 40.dp,
    daySpaceBy: Dp = 8.dp,
    defaultColor: Color = Color.White,
    activeColor: Color = Color.Black,
    borderColor: Color = Color.Black
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(daySpaceBy), modifier = Modifier.padding(16.dp)
    ) {
        days.forEach { day ->
            val isSelected = selectedDays.contains(day)
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(daySize)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, borderColor, RoundedCornerShape(8.dp))
                    .background(if (isSelected) activeColor else defaultColor)
                    .clickable { onDaySelected(day) }) {
                Text(
                    text = day,
                    color = if (isSelected) Color.White else Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun WeekdaySelectorScreen(onSelectedDay: (List<String>) -> Unit = {}) {
    var selectedDays by remember { mutableStateOf(listOf<String>()) }

    WeekdaySelector(
        days = koreanDays,
        selectedDays = selectedDays,
        onDaySelected = { day ->
            selectedDays = if (day in selectedDays) {
                selectedDays - day // 선택 해제
            } else {
                selectedDays + day // 선택
            }
            onSelectedDay.invoke(selectedDays)
        }
    )
}