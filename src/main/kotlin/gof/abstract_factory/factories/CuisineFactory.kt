package gof.abstract_factory.factories

import gof.abstract_factory.models.Lunch

interface CuisineFactory {
    fun cookLunch(): Lunch

    companion object {
        const val JAPANESE_CUISINE_MENU_NUMBER = 1
        const val AMERICAN_CUISINE_MENU_NUMBER = 2
        const val UKRAINIAN_CUISINE_MENU_NUMBER = 3
    }
}