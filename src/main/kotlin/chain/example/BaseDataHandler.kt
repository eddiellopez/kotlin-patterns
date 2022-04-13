package chain.example

import chain.Handler
import data.Event

/**
 * A base handler class.
 *
 * All handlers for a particular data will extend from this class.
 * Hence, one of these will be required for each different use case.
 *
 * @param next The next handler.
 */
abstract class BaseDataHandler(val next: Handler<Event>?) : Handler<Event> {

    /**
     * Pass the data to the next handler in the chain.
     *
     * @param event The data.
     */
    fun passToNext(event: Event) {
        next?.process(event) ?: throw IllegalArgumentException("No handler could handle $event!")
    }
}