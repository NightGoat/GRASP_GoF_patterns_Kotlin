package gof.abstract_factory.models

sealed class Lunch {
    object Sushi: Lunch()
    object Burger: Lunch()
    object Borsch: Lunch()
}