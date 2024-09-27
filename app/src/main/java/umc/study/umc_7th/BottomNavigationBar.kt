package umc.study.umc_7th

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class NavigationDestination(
    val expression: String,
    val unselectedIconId: Int,
    val selectedIconId: Int,
) {
    HOME(
        expression = "홈",
        unselectedIconId = R.drawable.ic_bottom_home_no_select,
        selectedIconId = R.drawable.ic_bottom_home_select,
    ),
    BROWSE(
        expression = "둘러보기",
        unselectedIconId = R.drawable.ic_bottom_look_no_select,
        selectedIconId = R.drawable.ic_bottom_look_select,
    ),
    SEARCH(
        expression = "검색",
        unselectedIconId = R.drawable.ic_bottom_search_no_select,
        selectedIconId = R.drawable.ic_bottom_search_select,
    ),
    MY(
        expression = "보관함",
        unselectedIconId = R.drawable.ic_bottom_locker_no_select,
        selectedIconId = R.drawable.ic_bottom_locker_select,
    ),
}

@Composable
fun BottomNavigationBar(
    onDestinationClicked: (NavigationDestination) -> Unit,
) {
    var currentDestination by remember { mutableStateOf(NavigationDestination.HOME) }

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        NavigationDestination.entries.forEach { destination ->
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        currentDestination = destination
                        onDestinationClicked(destination)
                    },
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    painter = painterResource(
                        id = if (destination == currentDestination)
                            destination.selectedIconId
                        else
                            destination.unselectedIconId
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = destination.expression,
                    style = TextStyle(
                        color = if (destination == currentDestination)
                            Color.Blue
                        else
                            Color.Black.copy(alpha = 0.5f),
                        fontSize = 10.sp,
                        lineBreak = LineBreak.Heading,
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    BottomNavigationBar(
        onDestinationClicked = {},
    )
}