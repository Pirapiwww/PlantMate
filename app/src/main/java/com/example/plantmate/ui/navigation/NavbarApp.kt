package com.example.plantmate.ui.navigation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.plantmate.data.api.ApiProvider
import com.example.plantmate.data.repository.EncyclopediaRepository
import com.example.plantmate.data.viewmodel.EncyclopediaViewModel
import com.example.plantmate.data.viewmodel.EncyclopediaViewModelFactory
import com.example.plantmate.model.CareGuideItem
import com.example.plantmate.ui.home.PlantHomeScreen
import com.example.plantmate.ui.myjournal.MyJournalScreen
import com.example.plantmate.ui.bookmark.BookmarkScreen
import com.example.plantmate.ui.careguide.CareGuideScreen
import com.example.plantmate.ui.plantencyclopedia.PlantEncyclopediaSearchScreen
import com.example.plantmate.ui.plantjournal.PlantJournalScreen
import com.example.plantmate.ui.plantlens.PlantLensInputScreen
import com.example.plantmate.ui.plantlens.PlantLensResultScreen
import com.example.plantmate.ui.plantnews.PlantNewsScreen

@Composable
fun NavbarApp(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Bottom Navbar
        composable("home") { PlantHomeScreen(navController) }
        composable("journal") { MyJournalScreen(navController) }
        composable("bookmark") { BookmarkScreen(navController) }
//        composable("profile") { ProfileScreen(navController) }

        // Feature

        // Plant Journal
        composable("plantJournal") { PlantJournalScreen( onBack = {navController.popBackStack()})}

        // Plant Lens
        composable("plantLens") {
            PlantLensInputScreen(
                onBack = { navController.popBackStack() },
                onAnalysis = { uri ->
                    navController.navigate("result?imageUri=$uri")
                }
            )
        }
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
            val apiKey = "VSjIrbSoXGTfXqcJXhQQHCEIh5FR2wjuEHGwUsInpOcAe4rQso"

            PlantLensResultScreen(
                imageUri = imageUri,
                apiKey = apiKey,
                plantIdApi = ApiProvider.plantIdApi,
                onBack = { navController.popBackStack() }
            )
        }


        // ==========================
// NAV HOST INTEGRASI PLANT CARE
// ==========================
        composable("plantEncyclopedia") {
            val repository = EncyclopediaRepository()

            PlantEncyclopediaSearchScreen(
                repo = repository,
                onBack = { navController.popBackStack() },
                onDetailClick = { plantId ->
                    navController.navigate("plantCareGuide/$plantId")
                }
            )
        }

        composable(
            "plantCareGuide/{plantId}",
            arguments = listOf(navArgument("plantId") { type = NavType.IntType })
        ) { backStackEntry ->

            val plantId = backStackEntry.arguments?.getInt("plantId") ?: 0
            val repository = EncyclopediaRepository()
            val viewModel: EncyclopediaViewModel = viewModel(
                factory = EncyclopediaViewModelFactory(repository)
            )

            // State lokal
            var careGuide by remember { mutableStateOf<CareGuideItem?>(null) }
            var imageUrl by remember { mutableStateOf<String?>(null) } // gambar dari PlantListItem

            // State dari ViewModel
            val isLoading by viewModel.isLoading.collectAsState()
            val error by viewModel.error.collectAsState()

            LaunchedEffect(plantId) {
                // 1. Fetch care guide
                viewModel.loadCareGuide(plantId) { guideList ->
                    careGuide = guideList?.firstOrNull()
                }

                // 2. Fetch plant detail agar dapat gambar
                try {
                    val plantDetail = repository.getPlantDetail(plantId)
                    imageUrl = plantDetail.default_image?.regular_url
                } catch (e: Exception) {
                    imageUrl = null
                }
            }

            // UI
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                careGuide != null -> {
                    CareGuideScreen(
                        careGuide = careGuide!!,
                        imageUrl = imageUrl, // <-- tampilkan gambar
                        onBack = { navController.popBackStack() }
                    )
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = error ?: "Care guide tidak tersedia")
                    }
                }
            }
        }

        // Plant News
        composable("plantNews") { PlantNewsScreen(onBack = {navController.popBackStack()}) }

    }
}