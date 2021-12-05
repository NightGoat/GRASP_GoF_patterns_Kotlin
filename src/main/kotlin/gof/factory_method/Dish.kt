package gof.factory_method

sealed class Dish() {
    class ApplePie: Dish()
    class ChickenSoup: Dish()
    class Candy: Dish()
}