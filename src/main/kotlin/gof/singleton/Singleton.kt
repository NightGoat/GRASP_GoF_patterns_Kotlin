package gof.singleton

fun main() {
    val ordinarySingleton = Singleton
    val tenInstancesSingletonCreated = TenInstancesSingleton.getInstance(0) //will create instance
    val tenInstancesSingletonReturned = TenInstancesSingleton.getInstance(0) //will just return it
    val tenInstancesSingletonThrows = TenInstancesSingleton.getInstance(11) //will throw exception
}

object Singleton {
    var value: String = ""
}

class TenInstancesSingleton private constructor() {
    companion object {
        private const val MAX_INSTANCES = 10
        private val instances: MutableList<TenInstancesSingleton> = mutableListOf()
        fun getInstance(index: Int): TenInstancesSingleton {
            return if (index in 0..MAX_INSTANCES) {
                var instance = instances.getOrNull(index)
                if (instance == null) {
                    instance = TenInstancesSingleton()
                    instances.add(index, instance)
                }
                instance
            } else {
                throw IllegalArgumentException("this class can only have $MAX_INSTANCES instances!")
            }
        }
    }
}

class TenInstancesSingletonThreadSafe private constructor() {
    companion object {
        private const val MAX_INSTANCES = 10
        private val instances: List<TenInstancesSingletonThreadSafe> = List(MAX_INSTANCES) {
            TenInstancesSingletonThreadSafe()
        }

        fun getInstance(index: Int): TenInstancesSingletonThreadSafe {
            return if (index in 0..MAX_INSTANCES) {
                instances[index]
            } else {
                throw IllegalArgumentException("this class can only have $MAX_INSTANCES instances!")
            }
        }
    }
}