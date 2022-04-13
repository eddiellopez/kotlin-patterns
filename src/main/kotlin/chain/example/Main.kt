package chain.example

import chain.ChainBuilder
import data.Event

fun main() {
    // Build a chain
    val chain = ChainBuilder.build()

    // Process upcoming data.
    with(chain) {
        process(Event(name = "A new event"))
        process(Event(name = "Other event"))
    }
}