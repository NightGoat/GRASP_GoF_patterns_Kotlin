package grasp

import utils.FakeRepo
import utils.models.OrderItem

/**
 * WIKI:
 * Creator
 * See also: Factory pattern

 * The creation of objects is one of the most common activities in an object-oriented system. Which class is responsible for creating objects is a fundamental property of the relationship between objects of particular classes.

 * Problem: Who creates object A?
 * Solution: In general, Assign class B the responsibility to create object A if one, or preferably more, of the following apply:

 * Instances of B contain or compositely aggregate instances of A
 * Instances of B record instances of A
 * Instances of B closely use instances of A
 * Instances of B have the initializing information for instances of A and pass it on creation.[3]: 16:16.7 
 *
 * Related Pattern or Principle: Low Coupling, Factory pattern
 * */
abstract class CreatorExample() {
    private val repo = FakeRepo()
    val order
        get() = repo.order

    val productFoo = repo.productFoo
    val productBar = repo.productBar
}

/**
 * Bad example, class creates another classes not where they needed, it makes WrongCreatorExample and Order tighten up
 * with OrderItem creation.
 * */
class WrongCreatorExample(): CreatorExample() {

    fun addOrderItem() {
        val orderItemFoo = OrderItem(
            mapOf(productFoo to 3, productBar to 4)
        )
        val orderItemBar = OrderItem(
            mapOf(productBar to 4, productBar to 4)
        )
        order.addOrderItem(orderItemFoo)
        order.addOrderItem(orderItemBar)
    }
}

/** Good example, OkCreatorExample dont creates OrderItems, so it creates only where it is needed */
class OkCreatorExample() : CreatorExample() {
    fun addOrderItem() {
        val products = listOf(productFoo, productBar)
        order.addOrderItem(products)
    }
}