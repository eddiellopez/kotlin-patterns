package chaindsl.example

import chaindsl.Chain.Companion.chainOf
import data.Event

fun main() {
    // Building a chain.
    val chain = chainOf<Event> {
        handle {
            by { HandlerOne() }
            whenever { name.length < 7 }
        }

        handle {
            by { HandlerTwo() }
            whenever {
                name.length >= 5
            }
        }
    }

    // Process the events.
    with(chain) {
        process(Event(name = "A name"))
        process(Event(name = "Another name"))
    }
}