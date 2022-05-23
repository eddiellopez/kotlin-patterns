package chain.example

import chain.Handler
import data.Event

/**
 * A handler that processes data whose [Event.name] starts with the letter B.
 *
 * @param next The next handler.
 */
class ScenarioTwoHandler(next: Handler<Event>? = null) : BaseDataHandler(next) {

    override fun process(event: Event) {
        if (event.name.length < 11) {
            println("ScenarioTwoHandler handling $event")
        } else {
            passToNext(event)
        }
    }
}