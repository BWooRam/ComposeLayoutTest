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
import androidx.compose.ui.unit.dp

@Composable
fun WeekdaySelector(
    selectedDays: List<String>, onDaySelected: (String) -> Unit
) {
    val days = listOf("일", "월", "화", "수", "목", "금", "토")

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)
    ) {
        days.forEach { day ->
            val isSelected = selectedDays.contains(day)
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                    .background(if (isSelected) Color.Black else Color.White)
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

    WeekdaySelector(selectedDays) { day ->
        selectedDays = if (day in selectedDays) {
            selectedDays - day // 선택 해제
        } else {
            selectedDays + day // 선택
        }
        onSelectedDay.invoke(selectedDays)
    }
}