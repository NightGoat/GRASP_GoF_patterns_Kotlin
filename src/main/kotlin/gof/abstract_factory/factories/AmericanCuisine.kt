package gof.abstract_factory.factories

import gof.abstract_factory.models.Lunch

object AmericanCuisine: CuisineFactory {
    override fun cookLunch(): Lunch {
        return Lunch.Burger
    }
}