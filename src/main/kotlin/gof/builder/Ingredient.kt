package gof.builder

sealed class Ingredient() {
    object Cheese : Ingredient()
    object Bacon : Ingredient()
    object Pineapple : Ingredient()
    object Mushrooms : Ingredient()
    object Seafood : Ingredient()
}