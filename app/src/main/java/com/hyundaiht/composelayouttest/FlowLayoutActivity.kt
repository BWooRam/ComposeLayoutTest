package com.hyundaiht.composelayouttest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ContextualFlowRowOverflowScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyundaiht.composelayouttest.ui.theme.ComposeUiTestTheme

class FlowLayoutActivity : ComponentActivity() {
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
                        FlowRowSimpleUsageExample(
                            color = Color.Magenta,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalArrangement =Arrangement.Top
                        )
                        Spacer(Modifier.height(20.dp))
                        FlowRowSimpleUsageExample(
                            color = Color.Gray,
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalArrangement = Arrangement.Bottom,
                            maxItemsInEachRow = 3
                        )
                        Spacer(Modifier.height(20.dp))
                        FlowRowSimpleUsageExample(
                            color = Color.Cyan,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.Center,
                            maxItemsInEachRow = 2
                        )
                        Spacer(Modifier.height(20.dp))
                        ContextualFlowRowExample()
                        Spacer(Modifier.height(20.dp))
                        FlowRowWeightExample()
                        Spacer(Modifier.height(20.dp))
                        FlowRowRatioExample()
                        Spacer(Modifier.height(20.dp))
                        FillMaxColumnWidth()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun ContextualFlowRowExample() {
        val totalCount = 40
        var maxLines by remember {
            mutableIntStateOf(2)
        }

        val moreOrCollapseIndicator = @Composable { scope: ContextualFlowRowOverflowScope ->
            val remainingItems = totalCount - scope.shownItemCount
            ChipItem(if (remainingItems == 0) "Less" else "+$remainingItems", onClick = {
                if (remainingItems == 0) {
                    maxLines = 2
                } else {
                    maxLines += 5
                }
            })
        }

        ContextualFlowRow(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxWidth(1f)
                .wrapContentHeight()
                .background(Color.Green),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            maxLines = maxLines,
            overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
                minRowsToShowCollapse = 4,
                expandIndicator = moreOrCollapseIndicator,
                collapseIndicator = moreOrCollapseIndicator
            ),
            itemCount = totalCount
        ) { index ->
            ChipItem("Item $index")
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun FlowRowWeightExample() {
        val rows = 3
        val columns = 4
        FlowRow(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            maxItemsInEachRow = rows
        ) {
            val itemModifier = Modifier
                .padding(4.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Blue)
            repeat(rows * columns) { item ->
                Log.d("FlowRowWeightExample", "item = $item")
                if (((item + 1) % (columns + 1)) == 0) {
                    Spacer(modifier = itemModifier.fillMaxWidth())
                } else {
                    Spacer(modifier = itemModifier.weight(0.25f))
                }
            }
        }
    }


    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun FlowRowRatioExample(){
        FlowRow(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            maxItemsInEachRow = 3
        ) {
            val itemModifier = Modifier
                .clip(RoundedCornerShape(8.dp))
            Box(
                modifier = itemModifier
                    .height(200.dp)
                    .width(60.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = itemModifier
                    .height(200.dp)
                    .fillMaxWidth(0.7f)
                    .background(Color.Blue)
            )
            Box(
                modifier = itemModifier
                    .height(200.dp)
                    .weight(1f)
                    .background(Color.Magenta)
            )
        }
    }

    private val listDesserts = listOf(
        "Apple",
        "Banana",
        "Cupcake",
        "Donut",
        "Eclair",
        "Froyo",
        "Gingerbread",
        "Honeycomb",
        "Ice Cream Sandwich",
        "Jellybean",
        "KitKat",
        "Lollipop",
        "Marshmallow",
        "Nougat",
    )

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun FillMaxColumnWidth() {
        FlowColumn(
            Modifier
                .padding(20.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachColumn = 5,
        ) {
            repeat(listDesserts.size) {
                Box(
                    Modifier
                        .fillMaxColumnWidth()
                        .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {

                    Text(
                        text = listDesserts[it],
                        fontSize = 18.sp,
                        modifier = Modifier.padding(3.dp)
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
     fun FlowRowSimpleUsageExample(
        color: Color = Color.White,
        horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        maxItemsInEachRow: Int = Int.MAX_VALUE
    ) {
        FlowRow(
            modifier = Modifier
                .padding(8.dp)
                .background(color),
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement,
            maxItemsInEachRow = maxItemsInEachRow
        ) {
            ChipItem("Price: High to Low")
            ChipItem("Avg rating: 4+")
            ChipItem("Free breakfast")
            ChipItem("Free cancellation")
            ChipItem("Free cancellation £50 pn")
            ChipItem("£50 pn")
            ChipItem("Free cancellation Avg rating: 4+ £50 pn")
            ChipItem("Price: High to Low Avg rating: 4+ Free breakfast £51 pn")
            ChipItem("Price: High to Low £52 pn Free cancellation")
            ChipItem("£53 pn")
            ChipItem("£54 pn")
            ChipItem("£55 pn")
            ChipItem("£56 pn")
            ChipItem("£57 pn")
            ChipItem("£58 pn")
            ChipItem("£59 pn")
            ChipItem("£60 pn")
            ChipItem("£61 pn")
            ChipItem("£62 pn")
            ChipItem("£63 pn")
        }
    }

    @Composable
    fun ChipItem(text: String, onClick: () -> Unit = {}) {
        FilterChip(
            modifier = Modifier.padding(end = 4.dp),
            onClick = onClick,
            leadingIcon = {},
            border = BorderStroke(1.dp, Color(0xFF3B3A3C)),
            label = {
                Text(text)
            },
            selected = false
        )
    }
}