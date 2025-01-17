package com.hyundaiht.composelayouttest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyundaiht.composelayouttest.ui.theme.ComposeUiTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeUiTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        BoxInTextWithPaddingToBaselinePreview()
                        TextWithPaddingToBaselinePreview()
                        TextWithNormalPaddingPreview()
                        MyBasicColumn {
                            Column {
                                Text("Hi1")
                                Text("Hi2")
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyBasicColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier
            .size(200.dp, 200.dp)
            .background(Color.Magenta),
        content = content
    ) { measurables, constraints ->
        for (measurable in measurables) {
            Log.d("MyBasicColumn", "measurables item = $measurable")
        }
        val placeable = measurables.first().measure(constraints)
        Log.d("MyBasicColumn", "placeable width = ${placeable.width}, height = ${placeable.height}")
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(placeable.width, placeable.height)
        }
    }
}

@Preview
@Composable
fun BoxInTextWithPaddingToBaselinePreview() {
    Box(
        Modifier
            .size(100.dp, 200.dp)
            .background(Color.Cyan)
    ) {
        Text(
            "Hi\nasdasd\nthere!",
            Modifier
                .padding(0.dp)
                .firstBaselineToTop(0.dp)
                .background(Color.Red)
        )
    }
}

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    /*
        constraints = Constraints(minWidth = 0, maxWidth = 1440, minHeight = 0, maxHeight = 2708)
        placeable width = 247, height = 93
        placeable measuredWidth = 247, measuredHeight = 93
        firstBaseline = 67, lastBaseline = 67
        placeableY = 45, height = 138
    */
    Text(
        "Hi there!",
        Modifier
            .firstBaselineToTop(32.dp)
            .background(Color.Red)
    )
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    /*
        constraints = Constraints(minWidth = 0, maxWidth = 1440, minHeight = 0, maxHeight = 2458)
        placeable width = 247, height = 93
        placeable measuredWidth = 247, measuredHeight = 93
        firstBaseline = 67, lastBaseline = 67
    */
    Text(
        "Hi there!",
        Modifier
            .padding(top = 32.dp)
            .background(Color.Blue)
    )
}

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = layout { measurable, constraints ->
    // Measure the composable
    val placeable = measurable.measure(constraints)
    Log.d("firstBaselineToTop", "measurable = $measurable")
    Log.d("firstBaselineToTop", "constraints = $constraints")
    Log.d(
        "firstBaselineToTop",
        "placeable width = ${placeable.width}, height = ${placeable.height}"
    )
    Log.d(
        "firstBaselineToTop",
        "placeable measuredWidth = ${placeable.measuredWidth}, measuredHeight = ${placeable.measuredHeight}"
    )

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]
    val lastBaseline = placeable[LastBaseline]
    Log.d("firstBaselineToTop", "firstBaseline = $firstBaseline, lastBaseline = $lastBaseline")

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    Log.d("firstBaselineToTop", "placeableY = $placeableY, height = $height")

    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}