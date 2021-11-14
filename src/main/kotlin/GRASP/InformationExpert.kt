package GRASP

/**
 * Information expert
See also: Information hiding

Problem: What is a basic principle by which to assign responsibilities to objects?
Solution: Assign responsibility to the class that has the information needed to fulfill it.

Information expert (also expert or the expert principle) is a principle used to determine where to delegate
responsibilities such as methods, computed fields, and so on.

Using the principle of information expert, a general approach to assigning responsibilities is to look at a given
responsibility, determine the information needed to fulfill it, and then determine where that information is stored.

This will lead to placing the responsibility on the class with the most information required to fulfill it.
 * */
abstract class InformationExpertExample() {
    val orders = FakeRepo().orders
    abstract fun getSum(): Int
}

/**
 * Wrong realisation: instead of using methods responsible for getting data, class tries to compute everything by itself.
 * If code in inner classes will change, method getSum will fail.
 * */
class Wrong(): InformationExpertExample() {
    override fun getSum(): Int {
        return orders.map { order ->
            order.items.sumOf { item ->
                item.goods.map { map ->
                    map.key.price * map.value
                }.sumOf { it }
            }
        }.sumOf { it }
    }
}

/**
 * Good realisation:
 * */
class Ok(): InformationExpertExample() {
    override fun getSum(): Int {
        return orders.map {
            it.getSum()
        }.sumOf { it }
    }
}


class Order(val items: List<OrderItem>) {
    fun getSum(): Int {
        return items.sumOf { it.getSubSum() }
    }
}

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

data class Product(val price: Int)