package utils.models

class Order(val items: MutableList<OrderItem>) {

    fun getSum(): Int {
        return items.sumOf { it.getSubSum() }
    }

    fun addOrderItem(orderItem: OrderItem) {
        items.add(orderItem)
    }

    fun addOrderItem(products: List<Product>) {
        products.forEachIndexed { index, product ->
            val orderItemFoo = OrderItem(
                mapOf(product to (index + 3))
            )
            items.add(orderItemFoo)
        }
    }
}