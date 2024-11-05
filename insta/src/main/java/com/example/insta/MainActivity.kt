package com.example.insta

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.insta.ui.theme.InstagramTheme
import kotlinx.coroutines.launch

private val emptyBitmap = ImageBitmap(1, 1)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstagramTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ExploreScreen(
                            getImage = { RetrofitClient.getImage() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExploreScreen(
    getImage: suspend () -> ImageBitmap,
) {
    var size by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val images = remember { mutableStateMapOf<Int, ImageBitmap>() }
    val gridState = rememberLazyStaggeredGridState()
    var limit by remember { mutableIntStateOf(100) }

    Column(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            with(density) {
                size = (it.size.width / 3).toDp()
            }
        }
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3),
            state = gridState,
        ) {
            items(
                count = limit,
                key = { it }
            ) { index ->
                LaunchedEffect(Unit) {
                    if (index in gridState.firstVisibleItemIndex..gridState.firstVisibleItemIndex + 20) {
                        if (limit < index + 30) limit += 100
                        repeat(50) { offset ->
                            val target = index + offset
                            if (!images.containsKey(target)) {
                                images[target] = emptyBitmap
                                launch {
                                    Log.d("ExploreScreen", "Loading image $target")
                                    try {
                                        images[target] = getImage()
                                    } catch (e: Exception) {
                                        images.remove(target)
                                    }
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .width(size)
                        .height(
                            size * when (index % 10) {
                                2, 5 -> 2
                                else -> 1
                            }
                        )
                ) {
                    Box(modifier = Modifier.padding(2.dp)) {
                        Image(
                            bitmap = images[index] ?: emptyBitmap,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExploreScreen() {
    InstagramTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                val image = ImageBitmap.imageResource(id = R.drawable.img)
                ExploreScreen(
                    getImage = { image }
                )
            }
        }
    }
}
