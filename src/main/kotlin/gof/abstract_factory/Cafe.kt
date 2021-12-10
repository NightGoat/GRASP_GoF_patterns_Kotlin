package gof.abstract_factory

import gof.abstract_factory.factories.AmericanCuisine
import gof.abstract_factory.factories.CuisineFactory
import gof.abstract_factory.factories.CuisineFactory.Companion.AMERICAN_CUISINE_MENU_NUMBER
import gof.abstract_factory.factories.CuisineFactory.Companion.JAPANESE_CUISINE_MENU_NUMBER
import gof.abstract_factory.factories.CuisineFactory.Companion.UKRAINIAN_CUISINE_MENU_NUMBER
import gof.abstract_factory.factories.JapaneseCuisine
import gof.abstract_factory.factories.UkrainianCuisine
import gof.abstract_factory.models.Customer
import java.util.*

class Cafe(
    private val menus: List<Int> = listOf(JAPANESE_CUISINE_MENU_NUMBER, AMERICAN_CUISINE_MENU_NUMBER, UKRAINIAN_CUISINE_MENU_NUMBER),
    private val workingInterval: IntRange = IntRange(10, 21),
) {
    private val servedCustomers = mutableListOf<Customer>()

    val isAllCustomersHappy
        get() = servedCustomers.isEmpty() || servedCustomers.all { !it.isHungry}

    private var currentHour: Int = Calendar.HOUR_OF_DAY
    private val isWorkingHours
        get() = currentHour in workingInterval

    private val door = Door()

    fun work(worksEndCallback: () -> Unit) {
            while (isWorkingHours && isAllCustomersHappy) {
                val customer = door.letCustomerIn()
                customer?.run {
                    val order = selectMenu(menus)
                    val cuisine = selectCuisine(order)
                    val lunch = cuisine.cookLunch()
                    receiveOrder(lunch)
                    servedCustomers.add(this)
                    currentHour+=1
                }
            }
        worksEndCallback()
    }

    private fun selectCuisine(menu: Int): CuisineFactory {
        return when(menu) {
            JAPANESE_CUISINE_MENU_NUMBER -> JapaneseCuisine
            AMERICAN_CUISINE_MENU_NUMBER -> AmericanCuisine
            UKRAINIAN_CUISINE_MENU_NUMBER -> UkrainianCuisine
            else -> throw IllegalAccessException("No such menu!")
        }
    }

    inner class Door {
        fun letCustomerIn(): Customer? {
            return Customer().takeIf { isWorkingHours }
        }
    }
}