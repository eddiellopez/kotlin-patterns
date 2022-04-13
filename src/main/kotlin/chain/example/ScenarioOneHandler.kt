package chain.example

import chain.Handler
import data.Event

/**
 * A handler that processes data whose [Event.name] starts with the letter A.
 *
 * @param next The next handler.
 */
class ScenarioOneHandler(next: Handler<Event>? = null) : BaseDataHandler(next) {

    override fun process(event: Event) {
        // Inspect the data looking for a handling trait.
        if (event.name.startsWith("A")) {
            println("ScenarioOneHandler handling $event")
        } else {
            // Can't handle this data, calling next link in the chain.
            passToNext(event)
        }
    }
}