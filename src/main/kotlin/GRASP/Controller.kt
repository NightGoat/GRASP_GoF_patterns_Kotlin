package GRASP

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sun.awt.Mutex
import utils.models.Calculator
import utils.models.ClickType
import utils.models.withLock
import java.awt.Button
import java.awt.TextField
import kotlin.coroutines.CoroutineContext

/**
 * WIKI:
 * The controller pattern assigns the responsibility of dealing with system events to a non-UI class that represents
 * the overall system or a use case scenario. A controller object is a non-user interface object responsible for
 * receiving or handling a system event.
 *
 * Problem: Who should be responsible for handling an input system event?
 * Solution: A use case controller should be used to deal with all system events of a use case, and may be used for more
 * than one use case. For instance, for the use cases Create User and Delete User, one can have a single class called
 * UserController, instead of two separate use case controllers.

 * The controller is defined as the first object beyond the UI layer that receives and coordinates ("controls")
 * a system operation. The controller should delegate the work that needs to be done to other objects; it coordinates
 * or controls the activity. It should not do much work itself. The GRASP Controller can be thought of as being a part
 * of the application/service layer[4] (assuming that the application has made an explicit distinction between the
 * application/service layer and the domain layer) in an object-oriented system with common layers in an information
 * system logical architecture.
 * */

/**
 * Actually best example in kotlin would be from Android Jetpack Fragment + ViewModel example (MVVM)
 * but i'll try to get simpler: we have simple calculation application, with three fields (first two input and third is evaluation)
 * and two buttons Plus and Minus. User input in this example is multithreaded: On each user action new coroutine will
 * be created
 * */
abstract class Application: CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val firstNumberField: TextField = TextField()
    val secondNumberField: TextField = TextField()
    val finalNumberField: TextField = TextField()
    val plusButton: Button = Button()
    val minusButton: Button = Button()

    abstract fun main()

    fun Button.onUserAction(doFun: suspend () -> Unit) {
        this.addActionListener {
            launch {
                doFun()
            }
        }
    }
}

/** A bad application would react on multithreaded user input by itself, user can change their input between clicks on
 * plus and minus buttons, so final field value would be unpredictable */
class BadApplication(): Application() {
    override fun main() {
        plusButton.onUserAction {
            val x = firstNumberField.text.toIntOrNull() ?: 0
            val y = secondNumberField.text.toIntOrNull() ?: 0
            val sum = x + y
            finalNumberField.text = sum.toString()
        }

        minusButton.onUserAction {
            val x = firstNumberField.text.toIntOrNull() ?: 0
            val y = secondNumberField.text.toIntOrNull() ?: 0
            val minus = x - y
            finalNumberField.text = minus.toString()
        }
    }
}

/** Good application delegates async code to a uiController class that has synchronized field in it (Mutex)
 * */
class GoodApplication(): Application() {
    private val uiController = UiController()
    override fun main() {
        plusButton.onUserAction {
            val x = firstNumberField.text
            val y = secondNumberField.text
            finalNumberField.text = uiController.newInput(x, y).onUserClick(ClickType.PLUS)
        }

        minusButton.onUserAction {
            val x = firstNumberField.text
            val y = secondNumberField.text
            finalNumberField.text = uiController.newInput(x, y).onUserClick(ClickType.PLUS)
        }
    }
}

class UiController {
    private val mutex = Mutex()
    private var inputField: Pair<String, String>? = null
    private val calculator = Calculator()

    private val x: Int
        get() = inputField?.first?.toIntOrNull() ?: 0
    private val y: Int
        get() = inputField?.second?.toIntOrNull() ?: 0

    suspend fun newInput(inputX: String, inputY: String): UiController {
        mutex.withLock {
            inputField = Pair(inputX, inputY)
        }
        return this
    }

    fun onUserClick(clickType: ClickType): String {
        return inputField?.let {
            when(clickType) {
                ClickType.PLUS -> calculator.sum(x, y)
                ClickType.MINUS -> calculator.minus(x, y)
            }.toString()
        } ?: "ERROR"
    }
}

/** Another ok solution, is just create each time new async job worker
 * */
class AnotherGoodApplication: Application() {
    override fun main() {
        plusButton.onUserAction {
            val x = firstNumberField.text
            val y = secondNumberField.text
            finalNumberField.text = UiController().newInput(x, y).onUserClick(ClickType.PLUS)
        }

        minusButton.onUserAction {
            val x = firstNumberField.text
            val y = secondNumberField.text
            finalNumberField.text = UiController().newInput(x, y).onUserClick(ClickType.PLUS)
        }
    }
}
