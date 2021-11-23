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
class BadApi {
    private var apiVersion = SystemVersion.V_1

    fun setApiVersion(newApiVersion: SystemVersion): BadApi {
        apiVersion = newApiVersion
        return this
    }

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
}

class System1 {
    var api = BadApi()

    fun main() {
        api.setApiVersion(SystemVersion.V_2)
        api.updateData()
        api.sendData()
    }
}

/**
 * Good way to handle this situation is to have interface or abstract class for an api, and classes that would
 * implement its behaviour for every api version
 * */
interface GoodApi {
    fun updateData()
    fun sendData()
}

class ApiV1: GoodApi {
    override fun updateData() = println("updated v_1")
    override fun sendData() = println("send v_1")
}

class ApiV2: GoodApi {
    override fun updateData() =  println("updated v_2")
    override fun sendData() = println("send v_2")
}

class ApiV3: GoodApi {
    override fun updateData() = println("updated v_3")
    override fun sendData() = println("send v_3")
}

class System2 {
    var api: GoodApi = ApiV1()

    fun main() {
        api = ApiV2()
        api.updateData()
        api.sendData()
    }
}
