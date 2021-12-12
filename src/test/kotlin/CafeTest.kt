import gof.abstract_factory.Cafe
import org.junit.Assert
import org.junit.Test

class CafeTest {

    @Test
    fun cafe_test_1() {
        val cafe = Cafe()
        cafe.work {
            Assert.assertTrue(cafe.isAllCustomersHappy)
        }
    }
}