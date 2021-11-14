package utils

import utils.models.Order
import utils.models.OrderItem
import utils.models.Product

class FakeRepo {
    val productFoo = Product(1)
    val productBar = Product(2)

    val products = List(5) {
        Product(it)
    }

    val orderItems = List(5) { count ->
        OrderItem(
            products.associateWith { count }
        )
    }
    val order = Order(orderItems.toMutableList())

    val orders = List(5) {
        Order(orderItems.toMutableList())
    }
}