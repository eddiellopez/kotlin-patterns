package chain

import chain.example.ScenarioOneHandler
import chain.example.ScenarioThreeHandler
import chain.example.ScenarioTwoHandler
import data.Event

/**
 * A Chain builder.
 */
object ChainBuilder {

    /**
     * Builds a chain.
     *
     * @return A new Chain of Responsibility.
     */
    fun build(): Handler<Event> {
        return ScenarioOneHandler(
            ScenarioTwoHandler(
                ScenarioThreeHandler()
            )
        )
    }
}
