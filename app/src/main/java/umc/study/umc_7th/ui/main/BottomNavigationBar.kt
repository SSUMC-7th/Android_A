package umc.study.umc_7th.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import umc.study.umc_7th.Content
import umc.study.umc_7th.R
import umc.study.umc_7th.ui.main.home.HomeFragment
import umc.study.umc_7th.ui.main.locker.LockerFragment
import umc.study.umc_7th.previewMusicContentList
import umc.study.umc_7th.ui.main.browsing.BrowsingFragment

enum class NavigationDestination(
    val getFragment: () -> Fragment,
    val expression: String,
    val unselectedIconId: Int,
    val selectedIconId: Int,
) {
    HOME(
        getFragment = { HomeFragment() },
        expression = "홈",
        unselectedIconId = R.drawable.ic_bottom_home_no_select,
        selectedIconId = R.drawable.ic_bottom_home_select,
    ),
    BROWSING(
        getFragment = { BrowsingFragment() },
        expression = "둘러보기",
        unselectedIconId = R.drawable.ic_bottom_look_no_select,
        selectedIconId = R.drawable.ic_bottom_look_select,
    ),
    SEARCH(
        getFragment = { throw Exception() },
        expression = "검색",
        unselectedIconId = R.drawable.ic_bottom_search_no_select,
        selectedIconId = R.drawable.ic_bottom_search_select,
    ),
    MY(
        getFragment = { LockerFragment() },
        expression = "보관함",
        unselectedIconId = R.drawable.ic_bottom_locker_no_select,
        selectedIconId = R.drawable.ic_bottom_locker_select,
    ),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    currentDestination: NavigationDestination,
    currentContent: Content?,
    playingPoint: Int?,
    isPlaying: Boolean,
    onDestinationClicked: (NavigationDestination) -> Unit,
    onContentClicked: (Content) -> Unit,
    onPlayButtonClicked: (Boolean) -> Unit,
    onNextButtonClicked: () -> Unit,
    onPreviousButtonClicked: () -> Unit,
    onPlaylistButtonClicked: () -> Unit,
) {
    Column {
        // 현재 재생중인 콘텐츠
        if (currentContent != null) Column(
            modifier = Modifier.background(color = Color.Black.copy(0.05f))
        ) {
            if (playingPoint != null) Slider(
                state = remember(currentContent, playingPoint) {
                    SliderState(
                        value = playingPoint.toFloat(),
                        valueRange = 0f..currentContent.length.toFloat(),
                    )
                },
                enabled = false,
                thumb = {},
                track = { state ->
                    SliderDefaults.Track(
                        sliderState = state,
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color.Blue,
                        ),
                        thumbTrackGapSize = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            ) {
                Button(
                    colors = ButtonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        contentColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified,
                    ),
                    onClick = { onContentClicked(currentContent) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentContent.title,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = currentContent.author,
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.Black.copy(alpha = 0.5f)
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onPreviousButtonClicked,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.btn_miniplayer_previous),
                                contentDescription = null,
                            )
                        }
                        IconButton(
                            onClick = { onPlayButtonClicked(!isPlaying) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPlaying)
                                        R.drawable.btn_miniplay_pause
                                    else
                                        R.drawable.btn_miniplay_mvplay
                                ),
                                contentDescription = null,
                            )
                        }
                        IconButton(
                            onClick = onNextButtonClicked,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.btn_miniplayer_next),
                                contentDescription = null,
                            )
                        }
                    }
                    IconButton(
                        onClick = onPlaylistButtonClicked,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.btn_miniplayer_go_list),
                            contentDescription = null,
                        )
                    }
                }
            }
        }

        // 내비게이팅 바
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            NavigationDestination.entries.forEach { destination ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onDestinationClicked(destination) },
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
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    BottomNavigationBar(
        currentDestination = NavigationDestination.HOME,
        currentContent = previewMusicContentList.random(),
        playingPoint = 50,
        isPlaying = true,
        onDestinationClicked = {},
        onPlayButtonClicked = {},
        onNextButtonClicked = {},
        onPreviousButtonClicked = {},
        onPlaylistButtonClicked = {},
        onContentClicked = {},
    )
}