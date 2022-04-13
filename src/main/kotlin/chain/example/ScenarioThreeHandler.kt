package chain.example

import chain.Handler
import data.Event


/**
 * A handler that processes all the data it receives.
 *
 * @param next Te next handler.
 */
class ScenarioThreeHandler(next: Handler<Event>? = null) : BaseDataHandler(next) {

    override fun process(event: Event) {
        // Default and final handler in the chain: always handle.
        println("ScenarioThreeHandler handling ${event.name}")
    }
}
