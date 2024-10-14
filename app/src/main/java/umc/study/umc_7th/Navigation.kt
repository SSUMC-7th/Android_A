package umc.study.umc_7th

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import java.lang.reflect.Modifier

@Composable
public fun NavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier,
    route: String?,
    builder: NavGraphBuilder.() -> Unit
): Unit{}

//
//class FakeNavController : PreviewParameterProvider<NavController>{
//    override val values: Sequence<NavController>
//        get(){
//
//        }
//}