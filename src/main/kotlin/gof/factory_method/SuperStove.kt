package gof.factory_method

object SuperStove {
    fun cook(ingredient: MainIngredient): Dish {
        val stove: Stove = when (ingredient) {
            MainIngredient.APPLE -> PieStove()
            MainIngredient.CHICKEN -> SoupStove()
            MainIngredient.SUGAR -> CandyStove()
        }
        return stove.cook()
    }
}