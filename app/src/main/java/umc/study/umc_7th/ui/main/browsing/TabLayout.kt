package umc.study.umc_7th.ui.main.browsing

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class TabItem(
    val label: String,
    val page: @Composable () -> Unit,
)

@Composable
fun TabLayout(
    tabs: List<TabItem>
) {
    var currentTabIndex by remember(tabs) { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp),
            ) {
                tabs.forEachIndexed { index, (label, _) ->
                    AssistChip(
                        onClick = { currentTabIndex = index },
                        label = { Text(text = label) },
                        shape = RoundedCornerShape(percent = 50),
                        border = null,
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (index == currentTabIndex) Color.Blue
                            else Color.Black.copy(0.1f),
                            labelColor = if (index == currentTabIndex) Color.White
                            else Color.Black
                        )
                    )
                }
            }
        }

        tabs[currentTabIndex].page()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTabLayout() {
    TabLayout(tabs = listOf("차트", "영상", "장르", "상황", "분위기", "오디오").map {
        TabItem(
            label = it,
            page = { Text(text = "이것은 $it") },
        )
    })
}