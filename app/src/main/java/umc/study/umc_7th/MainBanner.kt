//package umc.study.umc_7th
//
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonColors
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.imageResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.LineBreak
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import umc.study.umc_7th.umc.study.umc_7th.Content
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun MainBanner(
//    title: String,
//    date: LocalDate,
//    contentList: List<Content>,
//    textColor: Color,
//    backgroundImage: ImageBitmap,
//    onVoiceSearchButtonClicked: () -> Unit,
//    onSubscriptionButtonClicked: () -> Unit,
//    onSettingButtonClicked: () -> Unit,
//    onPlayButtonClicked: () -> Unit,
//) {
//    Box {
//        Image(
//            bitmap = backgroundImage,
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.matchParentSize(),
//        )
//        Column(
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            modifier = Modifier.padding(16.dp)
//        ) {
//            // 버튼 바
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                listOf(
//                    onVoiceSearchButtonClicked to R.drawable.btn_main_mike,
//                    onSubscriptionButtonClicked to R.drawable.btn_main_ticket,
//                    onSettingButtonClicked to R.drawable.btn_main_setting,
//                ).forEach { (onClick, icon) ->
//                    IconButton(
//                        onClick = onClick,
//                        modifier = Modifier.size(32.dp)
//                    ) {
//                        Icon(
//                            painter = painterResource(id = icon),
//                            contentDescription = null,
//                            tint = textColor,
//                        )
//                    }
//                }
//            }
//            // 제목
//            Text(
//                text = title,
//                style = TextStyle(
//                    color = textColor,
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    lineBreak = LineBreak.Heading,
//                )
//            )
//            // 전체 재생 버튼
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                IconButton(
//                    onClick = onPlayButtonClicked,
//                    modifier = Modifier.size(48.dp)
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.btn_panel_play_large),
//                        contentDescription = null,
//                        tint = textColor,
//                    )
//                }
//            }
//            // 리스트 정보 축약
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                Text(text = "총 ${contentList.size}곡", color = textColor)
//                Text(
//                    text = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
//                    color = textColor
//                )
//            }
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier.height(100.dp)
//            ) {
//                items(count = contentList.size) { index ->
//                    val content = contentList[index]
//                    Button(
//                        colors = ButtonColors(
//                            containerColor = Color.Transparent,
//                            disabledContainerColor = Color.Transparent,
//                            contentColor = textColor,
//                            disabledContentColor = textColor,
//                        ),
//                        onClick = { /* TODO: 기능 구현 */ },
//                        shape = RoundedCornerShape(8.dp),
//                        contentPadding = PaddingValues(vertical = 8.dp)
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(16.dp),
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Image(
//                                bitmap = content.image,
//                                contentDescription = null,
//                                modifier = Modifier.size(40.dp)
//                            )
//                            Column(
//                                verticalArrangement = Arrangement.spacedBy(4.dp),
//                            ) {
//                                Text(text = content.title, color = textColor)
//                                Text(text = content.author, color = textColor)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun PreviewBanner() {
//    MainBanner(
//        title = "포근하게 덮어주는 꿈의 목소리",
//        date = LocalDate.parse("2019-11-11"),
//        contentList = List(15) {
//            Content(
//                title = "Butter",
//                author = "BTS",
//                image = ImageBitmap.imageResource(id = R.drawable.img_album_exp),
//                length = 200,
//            )
//        },
//        textColor = Color.White,
//        backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_default_4_x_1),
//        onVoiceSearchButtonClicked = {},
//        onSubscriptionButtonClicked = {},
//        onSettingButtonClicked = {},
//        onPlayButtonClicked = {},
//    )
//}