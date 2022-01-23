package gof.builder

class Pizza constructor(
    val ingredients: MutableSet<Ingredient>
) {
    private constructor(builder: Builder) : this(builder.ingredients)

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {

        val ingredients: MutableSet<Ingredient> = mutableSetOf()

        fun addIngredient(ingredient: Ingredient) = apply {
            ingredients.add(ingredient)
        }

        fun build() = Pizza(this)
    }
}

