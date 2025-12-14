package com.example.plantmate.data

import com.example.plantmate.R
import com.example.plantmate.model.NavbarIcon
import com.example.plantmate.model.FeatureIcon

class DataSource {

    // ==============================
    //       NAVBAR & FEATURES
    // ==============================

    fun loadNavbar(): List<NavbarIcon> {
        return listOf(
            NavbarIcon(R.drawable.home, R.string.navbar_home, "home"),
            NavbarIcon(R.drawable.journal, R.string.navbar_journal, "journal"),
            NavbarIcon(R.drawable.bookmark, R.string.navbar_bookmark, "bookmark"),
            NavbarIcon(R.drawable.profile, R.string.navbar_profile, "profile")
        )
    }

    fun loadFeature(): List<FeatureIcon> {
        return listOf(
            FeatureIcon(R.drawable.plant_journal, R.string.plant_journal, "plantJournal"),
            FeatureIcon(R.drawable.lens, R.string.plant_lens, "plantLens"),
            FeatureIcon(R.drawable.encyclopedia, R.string.plant_encyclopedia, "plantEncyclopedia"),
            FeatureIcon(R.drawable.news, R.string.plant_news, "plantNews")
        )
    }

    // ==============================
    //       DROPDOWNS VALUE
    // ==============================

    // Preparation
    fun loadPlantType(): List<Int> {
        return listOf(
            R.string.vegetable,
            R.string.ornamental,
            R.string.herb,
            R.string.fruit
        )
    }

    fun loadSource(): List<Int> {
        return listOf(
            R.string.seed,
            R.string.sapling,
            R.string.young_plant,
            R.string.cutting
        )
    }

    fun loadSoilType(): List<Int> {
        return listOf(
            R.string.humus,
            R.string.sand_mix,
            R.string.potting_mix
        )
    }

    fun loadFertilizerType(): List<Int> {
        return listOf(
            R.string.compost,
            R.string.organic,
            R.string.npk
        )
    }

    // Planting
    fun loadMethod(): List<Int> {
        return listOf(
            R.string.soil,
            R.string.container,
            R.string.hydroponic
        )
    }

    fun loadLocation(): List<Int> {
        return listOf(
            R.string.indoor,
            R.string.outdoor,
            R.string.pot,
            R.string.planting_bed
        )
    }

    fun loadFrequency(): List<Int> {
        return listOf(
            R.string.low_frequency,
            R.string.medium_frequency,
            R.string.high_frequency,
            R.string.veryHigh_frequency
        )
    }

    fun loadAmount(): List<Int> {
        return listOf(
            R.string.low_amount,
            R.string.medium_amount,
            R.string.high_amount,
            R.string.veryHigh_amount
        )
    }

    // Treatment
    fun loadCondition(): List<Int> {
        return listOf(
            R.string.healthy,
            R.string.wilted,
            R.string.pestDisease,
            R.string.nutrient
        )
    }

    fun loadTreatment(): List<Int> {
        return listOf(
            R.string.fertilizing,
            R.string.watering_treatment,
            R.string.pruning_treatment,
            R.string.pestDisease_control
        )
    }
}
