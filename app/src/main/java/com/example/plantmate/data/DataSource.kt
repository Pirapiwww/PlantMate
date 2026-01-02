package com.example.plantmate.data

import com.example.plantmate.R
import com.example.plantmate.model.FeatureIcon
import com.example.plantmate.model.NavbarIcon

object DataSource {

    // ==============================
    //       NAVBAR & FEATURES
    // ==============================

    fun loadNavbar(): List<NavbarIcon> = listOf(
        NavbarIcon(R.drawable.home, R.string.navbar_home, "home"),
        NavbarIcon(R.drawable.journal, R.string.navbar_journal, "journal"),
        NavbarIcon(R.drawable.bookmark, R.string.navbar_bookmark, "bookmark"),
        NavbarIcon(R.drawable.settings, R.string.navbar_settings, "settings")
    )

    fun loadFeature(): List<FeatureIcon> = listOf(
        FeatureIcon(R.drawable.plant_journal, R.string.plant_journal, "plantJournal"),
        FeatureIcon(R.drawable.lens, R.string.plant_lens, "plantLens"),
        FeatureIcon(R.drawable.encyclopedia, R.string.plant_encyclopedia, "plantEncyclopedia"),
        FeatureIcon(R.drawable.news, R.string.plant_news, "plantNews")
    )

    // ==============================
    //       PREPARATION
    // ==============================

    enum class PlantType(val key: String) {
        VEGETABLE("vegetable"),
        ORNAMENTAL("ornamental"),
        HERB("herb"),
        FRUIT("fruit")
    }

    fun loadPlantType(): List<PlantType> = PlantType.values().toList()

    fun PlantType.toStringRes(): Int = when (this) {
        PlantType.VEGETABLE -> R.string.vegetable
        PlantType.ORNAMENTAL -> R.string.ornamental
        PlantType.HERB -> R.string.herb
        PlantType.FRUIT -> R.string.fruit
    }

    enum class SourceType(val key: String) {
        SEED("seed"),
        SAPLING("sapling"),
        YOUNG_PLANT("young_plant"),
        CUTTING("cutting")
    }

    fun loadSource(): List<SourceType> = SourceType.values().toList()

    fun SourceType.toStringRes(): Int = when (this) {
        SourceType.SEED -> R.string.seed
        SourceType.SAPLING -> R.string.sapling
        SourceType.YOUNG_PLANT -> R.string.young_plant
        SourceType.CUTTING -> R.string.cutting
    }

    enum class SoilType(val key: String) {
        HUMUS("humus"),
        SAND_MIX("sand_mix"),
        POTTING_MIX("potting_mix")
    }

    fun loadSoilType(): List<SoilType> = SoilType.values().toList()

    fun SoilType.toStringRes(): Int = when (this) {
        SoilType.HUMUS -> R.string.humus
        SoilType.SAND_MIX -> R.string.sand_mix
        SoilType.POTTING_MIX -> R.string.potting_mix
    }

    enum class FertilizerType(val key: String) {
        COMPOST("compost"),
        ORGANIC("organic"),
        NPK("npk")
    }

    fun loadFertilizerType(): List<FertilizerType> = FertilizerType.values().toList()

    fun FertilizerType.toStringRes(): Int = when (this) {
        FertilizerType.COMPOST -> R.string.compost
        FertilizerType.ORGANIC -> R.string.organic
        FertilizerType.NPK -> R.string.npk
    }

    // ==============================
    //       PLANTING
    // ==============================

    enum class PlantingMethod(val key: String) {
        SOIL("soil"),
        CONTAINER("container"),
        HYDROPONIC("hydroponic")
    }

    fun loadPlantingMethod(): List<PlantingMethod> =
        PlantingMethod.values().toList()

    fun PlantingMethod.toStringRes(): Int = when (this) {
        PlantingMethod.SOIL -> R.string.soil
        PlantingMethod.CONTAINER -> R.string.container
        PlantingMethod.HYDROPONIC -> R.string.hydroponic
    }

    enum class LocationType(val key: String) {
        INDOOR("indoor"),
        OUTDOOR("outdoor"),
        POT("pot"),
        PLANTING_BED("planting_bed")
    }

    fun loadLocation(): List<LocationType> = LocationType.values().toList()

    fun LocationType.toStringRes(): Int = when (this) {
        LocationType.INDOOR -> R.string.indoor
        LocationType.OUTDOOR -> R.string.outdoor
        LocationType.POT -> R.string.pot
        LocationType.PLANTING_BED -> R.string.planting_bed
    }

    enum class FrequencyType(val key: String) {
        LOW("low"),
        MEDIUM("medium"),
        HIGH("high"),
        VERY_HIGH("very_high")
    }

    fun loadFrequency(): List<FrequencyType> = FrequencyType.values().toList()

    fun FrequencyType.toStringRes(): Int = when (this) {
        FrequencyType.LOW -> R.string.low_frequency
        FrequencyType.MEDIUM -> R.string.medium_frequency
        FrequencyType.HIGH -> R.string.high_frequency
        FrequencyType.VERY_HIGH -> R.string.veryHigh_frequency
    }

    enum class AmountType(val key: String) {
        LOW("low"),
        MEDIUM("medium"),
        HIGH("high"),
        VERY_HIGH("very_high")
    }

    fun loadAmount(): List<AmountType> = AmountType.values().toList()

    fun AmountType.toStringRes(): Int = when (this) {
        AmountType.LOW -> R.string.low_amount
        AmountType.MEDIUM -> R.string.medium_amount
        AmountType.HIGH -> R.string.high_amount
        AmountType.VERY_HIGH -> R.string.veryHigh_amount
    }

    // ==============================
    //       TREATMENT
    // ==============================

    enum class PlantCondition(val key: String) {
        HEALTHY("healthy"),
        WILTED("wilted"),
        PEST_DISEASE("pest_disease"),
        NUTRIENT("nutrient")
    }

    fun loadCondition(): List<PlantCondition> =
        PlantCondition.values().toList()

    fun PlantCondition.toStringRes(): Int = when (this) {
        PlantCondition.HEALTHY -> R.string.healthy
        PlantCondition.WILTED -> R.string.wilted
        PlantCondition.PEST_DISEASE -> R.string.pestDisease
        PlantCondition.NUTRIENT -> R.string.nutrient
    }

    enum class TreatmentType(val key: String) {
        FERTILIZING("fertilizing"),
        WATERING("watering"),
        PRUNING("pruning"),
        PEST_CONTROL("pest_control")
    }

    fun loadTreatment(): List<TreatmentType> =
        TreatmentType.values().toList()

    fun TreatmentType.toStringRes(): Int = when (this) {
        TreatmentType.FERTILIZING -> R.string.fertilizing
        TreatmentType.WATERING -> R.string.watering_treatment
        TreatmentType.PRUNING -> R.string.pruning_treatment
        TreatmentType.PEST_CONTROL -> R.string.pestDisease_control
    }
}
