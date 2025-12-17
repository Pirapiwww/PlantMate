package com.example.plantmate

import android.app.Application
import com.example.plantmate.data.local.AppDatabase
import com.example.plantmate.data.repository.NewsRepository
import com.example.plantmate.data.repository.local.*
import com.example.plantmate.data.repository.local.FormRepo.*
import com.example.plantmate.data.viewmodel.local.ViewModelFactory

class YourApp : Application() {

    // Database
    val database: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }

    // Repository lama
    val encyclopediaLocalRepository: EncyclopediaLocalRepository by lazy {
        EncyclopediaLocalRepository(database.encyclopediaDao())
    }

    val newsLocalRepository: NewsLocalRepository by lazy {
        NewsLocalRepository(
            database.newsDao(),
            remote = NewsRepository(database.newsDao())
        )
    }

    val lensLocalRepository: LensLocalRepository by lazy {
        LensLocalRepository(database.lensDao())
    }

    val searchLocalRepository: SearchLocalRepository by lazy {
        SearchLocalRepository(database.searchDao())
    }

    // Repository baru untuk Journal + kategori
    val journalLocalRepository: JournalLocalRepository by lazy {
        JournalLocalRepository(database.journalDao())
    }

    val preparationLocalRepository: PreparationLocalRepository by lazy {
        PreparationLocalRepository(database.preparationDao())
    }

    val plantingLocalRepository: PlantingLocalRepository by lazy {
        PlantingLocalRepository(database.plantingDao())
    }

    val treatmentLocalRepository: TreatmentLocalRepository by lazy {
        TreatmentLocalRepository(database.treatmentDao())
    }

    // ViewModelFactory
    val viewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory(
            encyclopediaLocalRepository,
            newsLocalRepository,
            lensLocalRepository,
            searchLocalRepository,
            journalLocalRepository,
            preparationLocalRepository,
            plantingLocalRepository,
            treatmentLocalRepository
        )
    }
}
