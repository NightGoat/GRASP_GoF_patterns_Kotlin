package gof.builder

fun main() {
    val pizza = Pizza.build {
        addIngredient(Ingredient.Cheese)
        addIngredient(Ingredient.Seafood)
    }

    val pizza2 = Pizza.Builder()
        .addIngredient(Ingredient.Cheese)
        .addIngredient(Ingredient.Bacon)
        .build()
}