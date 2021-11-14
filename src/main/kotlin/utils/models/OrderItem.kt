package utils.models

import utils.FakeRepo

class OrderItem(val goods: Map<Product, Int>) {
    fun getSubSum(): Int {
        return goods.map { map ->
            map.key.price * map.value
        }.sumOf { it }
    }

    fun getSubSumFaster(): Int {
        var sum = 0
        goods.forEach { map ->
            sum += map.key.price * map.value
        }
        return sum
    }
}