package com.example.plantmate

import android.app.Application
import com.example.plantmate.data.local.AppDatabase
import com.example.plantmate.data.repository.NewsRepository
import com.example.plantmate.data.repository.local.EncyclopediaLocalRepository
import com.example.plantmate.data.repository.local.LensLocalRepository
import com.example.plantmate.data.repository.local.NewsLocalRepository
import com.example.plantmate.data.viewmodel.local.ViewModelFactory

class YourApp : Application() {

    // Database
    val database: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }

    // Repository
    val encyclopediaLocalRepository: EncyclopediaLocalRepository by lazy {
        EncyclopediaLocalRepository(database.encyclopediaDao())
    }

    val newsLocalRepository: NewsLocalRepository by lazy {
        NewsLocalRepository(database.newsDao(),
            remote = NewsRepository(database.newsDao())
        )
    }

    val lensLocalRepository: LensLocalRepository by lazy {
        LensLocalRepository(database.lensDao())
    }


    val viewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory(
            encyclopediaLocalRepository,
            newsLocalRepository,
            lensLocalRepository
        )
    }
}
