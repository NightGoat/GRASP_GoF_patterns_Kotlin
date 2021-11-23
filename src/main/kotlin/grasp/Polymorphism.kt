package grasp

import utils.models.SystemVersion

/**
 * WIKI
 * According to the polymorphism principle, responsibility for defining the variation of behaviors based on type
 * is assigned to the type for which this variation happens. This is achieved using polymorphic operations.
 * The user of the type should use polymorphic operations instead of explicit branching based on type.
 *
 * Problem: How to handle alternatives based on type? How to create pluggable software components?
 * Solution: When related alternatives or behaviors vary by type (class), assign responsibility for the
 * behavior—using polymorphic operations—to the types for which the behavior varies.
 * (Polymorphism has several related meanings. In this context,
 * it means "giving the same name to services in different objects".)
 * */

/**
 * Example of not using polymorphism: you wrote an api, time passes by and you update it, it gets v2, v3, v4...
 * And if you will handle it in every if else construction it will be big and unbearable.
 * */
class BadApi private constructor(private val apiVersion: SystemVersion){

    fun updateData() {
        when(apiVersion) {
            SystemVersion.V_1 -> println("updated v_1")
            SystemVersion.V_2 -> println("updated v_2")
            SystemVersion.V_3 -> println("updated v_3")
        }
    }

    fun sendData() {
        when(apiVersion) {
            SystemVersion.V_1 -> println("send v_1")
            SystemVersion.V_2 -> println("send v_2")
            SystemVersion.V_3 -> println("send v_3")
        }
    }

    companion object {
        fun build(systemVersion: SystemVersion): BadApi {
            return BadApi(systemVersion)
        }
    }
}

class System1 {
    var systemVersion = SystemVersion.V_2
    private var api = BadApi.build(systemVersion)

    fun main() {
        api.updateData()
        api.sendData()
    }
}

/**
 * Good way to handle this situation is to have interface or abstract class for an api, and classes that would
 * implement its behaviour for every api version. Classes stay small and easy to read.
 * */
interface GoodApi {
    val systemVersion: SystemVersion
    fun updateData()
    fun sendData()

    companion object {
        fun createApi(systemVersion: SystemVersion): GoodApi {
            return when(systemVersion) {
                SystemVersion.V_1 -> ApiV1()
                SystemVersion.V_2 -> ApiV2()
                SystemVersion.V_3 -> ApiV3()
            }
        }
    }
}

class ApiV1: GoodApi {
    override val systemVersion: SystemVersion = SystemVersion.V_1
    override fun updateData() = println("updated v_1")
    override fun sendData() = println("send v_1")
}

class ApiV2: GoodApi {
    override val systemVersion: SystemVersion = SystemVersion.V_2
    override fun updateData() =  println("updated v_2")
    override fun sendData() = println("send v_2")
}

class ApiV3: GoodApi {
    override val systemVersion: SystemVersion = SystemVersion.V_3
    override fun updateData() = println("updated v_3")
    override fun sendData() = println("send v_3")
}

class System2 {
    var systemVersion = SystemVersion.V_2
    private val api = GoodApi.createApi(systemVersion)

    fun main() {
        api.updateData()
        api.sendData()
    }
}
