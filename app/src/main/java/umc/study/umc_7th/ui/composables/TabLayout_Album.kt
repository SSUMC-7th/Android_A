package umc.study.umc_7th.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class TabItem(
    val label: String,
    val page: @Composable () -> Unit,
)

@Composable
fun TabLayout(
    tabs: List<TabItem>,
) {
    var currentTabIndex by remember(tabs) { mutableIntStateOf(0) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, (label, _) ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .drawBehind {
                            drawLine(
                                color = Color.Black.copy(0.1f),
                                start = Offset(x = 0f, y = size.height),
                                end = Offset(x = size.width, y = size.height),
                                strokeWidth = 5f,
                            )
                            if (index == currentTabIndex) drawLine(
                                color = Color.Blue,
                                start = Offset(x = size.width.times(1 / 3f), y = size.height),
                                end = Offset(x = size.width.times(2 / 3f), y = size.height),
                                strokeWidth = 5f,
                            )
                        }
                        .heightIn(min = 48.dp)
                        .weight(1f)
                        .clickable { currentTabIndex = index }
                ) {
                    Text(
                        text = label,
                        style = TextStyle(
                            color = if (index == currentTabIndex)
                                Color.Blue
                            else
                                Color.Black.copy(alpha = 0.5f),
                        )
                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs[currentTabIndex].page()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTabLayout() {
    TabLayout(
        tabs = listOf(
            TabItem(
                label = "수록곡",
                page = {},
            ),
            TabItem(
                label = "상세정보",
                page = {},
            ),
            TabItem(
                label = "영상",
                page = {},
            ),
        )
    )
}