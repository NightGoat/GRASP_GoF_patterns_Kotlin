package gof.abstract_factory.models

class Customer() {
    var lunch: Lunch? = null

    val isHungry: Boolean
        get() = lunch == null

    fun selectMenu(menus: List<Int>): Int {
        return menus.random()
    }

    fun receiveOrder(newLunch: Lunch) {
       lunch = newLunch
    }
}