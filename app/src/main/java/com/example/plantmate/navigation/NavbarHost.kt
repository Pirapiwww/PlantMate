package com.example.plantmate.navigation

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.plantmate.data.local.AppDatabase
import com.example.plantmate.data.repository.EncyclopediaRepository
import com.example.plantmate.data.viewmodel.EncyclopediaViewModel
import com.example.plantmate.data.viewmodel.EncyclopediaViewModelFactory
import com.example.plantmate.data.viewmodel.local.EncyclopediaLocalViewModel
import com.example.plantmate.model.CareGuideItem
import com.example.plantmate.ui.bookmark.BookmarkEncyclopediaScreen
import com.example.plantmate.ui.bookmark.BookmarkScreen
import com.example.plantmate.ui.careguide.CareGuideScreen
import com.example.plantmate.ui.home.PlantHomeScreen
import com.example.plantmate.ui.myjournal.MyJournalScreen
import com.example.plantmate.ui.plantencyclopedia.PlantEncyclopediaSearchScreen
import com.example.plantmate.ui.plantjournal.PlantJournalScreen
import com.example.plantmate.ui.plantlens.PlantLensInputScreen
import com.example.plantmate.ui.plantlens.PlantLensResultScreen
import com.example.plantmate.ui.plantnews.PlantNewsScreen
import com.example.plantmate.ui.settings.SettingsScreen

import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import com.example.plantmate.YourApp
import com.example.plantmate.data.api.PlantIdApiProvider
import com.example.plantmate.data.viewmodel.local.LensLocalViewModel
import com.example.plantmate.ui.bookmark.BookmarkLensScreen
import com.example.plantmate.ui.myjournal.JournalCategoryResultScreen
import com.example.plantmate.ui.myjournal.JournalCategoryScreen
import com.example.plantmate.ui.plantjournal.PlantJournalResultScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavbarApp(
    navController: NavHostController,
    database: AppDatabase
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = "home",
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {

        // HOME
        composable("home") { PlantHomeScreen(navController) }
        composable("journal") { MyJournalScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable(
            route = "bookmark?tab={tab}",
            arguments = listOf(
                navArgument("tab") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { entry ->
            val tab = entry.arguments?.getInt("tab") ?: 0
            BookmarkScreen(
                navController = navController,
                initialTab = tab
            )
        }

        // LOCAL LENS
        composable(
            route = "lens/{from}/{id}",
            arguments = listOf(
                navArgument("from") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) { entry ->

            val from = entry.arguments?.getString("from") ?: "Home"
            val id = entry.arguments?.getInt("id") ?: 0

            val app = LocalContext.current.applicationContext as YourApp
            val viewModel: LensLocalViewModel =
                viewModel(factory = app.viewModelFactory)

            val lensList by viewModel.lens.collectAsState()
            val lens = lensList.firstOrNull { it.id == id }

            if (lens != null) {
                BookmarkLensScreen(
                    lens = lens,
                    navController = navController,
                    routeBack = from
                )
            }
        }

        // LOCAL CARE GUIDE
        composable(
            route = "localCareGuide/{from}/{id}",
            arguments = listOf(
                navArgument("from") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) { entry ->

            val from = entry.arguments?.getString("from") ?: "Home"
            val id = entry.arguments?.getInt("id") ?: 0

            val app = LocalContext.current.applicationContext as YourApp
            val viewModel: EncyclopediaLocalViewModel =
                viewModel(factory = app.viewModelFactory)

            val encyclopediaList by viewModel.encyclopedia.collectAsState()
            val encyclopedia = encyclopediaList.firstOrNull { it.id == id }

            if (encyclopedia != null) {
                BookmarkEncyclopediaScreen(
                    encyclopedia = encyclopedia,
                    navController = navController,
                    routeBack = from
                )
            }
        }

        // =========================
        // MY JOURNAL
        // =========================
        composable(
            route = "myjournal/{routeBack}/{journalId}",
            arguments = listOf(
                navArgument("routeBack") {
                    type = NavType.StringType
                },
                navArgument("journalId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val routeBack =
                backStackEntry.arguments!!.getString("routeBack")!!

            val journalId =
                backStackEntry.arguments!!.getInt("journalId")

            JournalCategoryScreen(
                journalId = journalId,
                routeBack = routeBack,
                navController = navController
            )
        }

        // =========================
        // MY JOURNAL (RESULT)
        // =========================
        composable(
            route = "journal/{type}/{categoryId}",
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                },
                navArgument("categoryId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val type =
                backStackEntry.arguments!!.getString("type")!!

            val categoryId =
                backStackEntry.arguments!!.getInt("categoryId")

            JournalCategoryResultScreen(
                categoryId = categoryId,
                type = type,
                navController = navController
            )
        }

        // =========================
        // PLANT JOURNAL (DEFAULT)
        // =========================
        composable(
            route = "plantJournal"
        ) {
            PlantJournalScreen(
                categoryId = 0,
                type = null,
                navController = navController
            )
        }

        // =========================
        // FORM EDIT
        // =========================
        composable(
            route = "form_edit/{type}/{categoryId}",
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                },
                navArgument("categoryId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val type =
                backStackEntry.arguments?.getString("type")

            val categoryId =
                backStackEntry.arguments?.getInt("categoryId") ?: 0

            PlantJournalScreen(
                categoryId = categoryId,
                type = type,
                navController = navController
            )
        }

        // =========================
        // FORM RESULT (PLANTING)
        // =========================
        composable(
            route = "form_result/planting/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id =
                backStackEntry.arguments?.getInt("id") ?: 0

            PlantJournalResultScreen(
                type = "planting",
                categoryId = id,
                navController = navController
            )
        }

        // =========================
        // FORM RESULT (PREPARATION)
        // =========================
        composable(
            route = "form_result/preparation/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id =
                backStackEntry.arguments?.getInt("id") ?: 0

            PlantJournalResultScreen(
                type = "preparation",
                categoryId = id,
                navController = navController
            )
        }

        // =========================
        // FORM RESULT (TREATMENT)
        // =========================
        composable(
            route = "form_result/treatment/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id =
                backStackEntry.arguments?.getInt("id") ?: 0

            PlantJournalResultScreen(
                type = "treatment",
                categoryId = id,
                navController = navController
            )
        }


        // PLANT LENS INPUT
        composable("plantLens") {
            PlantLensInputScreen(
                onBack = { navController.popBackStack() },
                onAnalysis = { uri ->

                    val encodedUri = Uri.encode(uri.toString())

                    navController.navigate(
                        "result?imageUri=$encodedUri"
                    )
                }
            )
        }

        // PLANT LENS RESULT
        composable(
            route = "result?imageUri={imageUri}",
            arguments = listOf(
                navArgument("imageUri") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { entry ->

            val imageUri = entry.arguments?.getString("imageUri")

            PlantLensResultScreen(
                imageUri = imageUri,
                onBack = { navController.popBackStack() },
                navController
            )
        }

        // PLANT ENCYCLOPEDIA
        composable("plantEncyclopedia") {

            val repository = EncyclopediaRepository(database.encyclopediaDao())

            PlantEncyclopediaSearchScreen(
                repo = repository,
                onBack = { navController.popBackStack() },
                onDetailClick = { plantId ->
                    navController.navigate("plantCareGuide/$plantId")
                }
            )
        }

        // CARE GUIDE
        composable(
            route = "plantCareGuide/{plantId}",
            arguments = listOf(
                navArgument("plantId") { type = NavType.IntType }
            )
        ) { entry ->

            val plantId = entry.arguments?.getInt("plantId") ?: 0
            val repository = EncyclopediaRepository(database.encyclopediaDao())

            val viewModel: EncyclopediaViewModel = viewModel(
                factory = EncyclopediaViewModelFactory(repository)
            )

            var careGuide by remember { mutableStateOf<CareGuideItem?>(null) }
            var imageUrl by remember { mutableStateOf<String?>(null) }

            val isLoading by viewModel.isLoading.collectAsState()
            val error by viewModel.error.collectAsState()

            LaunchedEffect(plantId) {
                viewModel.loadCareGuide(plantId) { guideList ->
                    careGuide = guideList?.firstOrNull()
                }

                try {
                    val plantDetail = repository.getPlantDetail(plantId)
                    imageUrl = plantDetail.default_image?.regular_url
                } catch (_: Exception) {
                    imageUrl = null
                }
            }

            when {
                isLoading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                careGuide != null -> CareGuideScreen(
                    careGuide = careGuide!!,
                    imageUrl = imageUrl,
                    onBack = { navController.popBackStack() },
                    navController = navController
                )

                else -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text(error ?: "Care guide tidak tersedia") }
            }
        }

        // NEWS
        composable("plantNews") {
            PlantNewsScreen(onBack = { navController.popBackStack() })
        }
    }
}
