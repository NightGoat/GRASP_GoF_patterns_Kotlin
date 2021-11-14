package GRASP

class FakeRepo {
    val products = List(5) {
        Product(it)
    }

    val orderItems = List(5) { count ->
        OrderItem(
            products.associateWith { count }
        )
    }

    val orders = List(5) {
        Order(orderItems)
    }
}