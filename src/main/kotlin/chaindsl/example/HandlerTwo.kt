package chaindsl.example

import chain.Handler
import data.Event

class HandlerTwo : Handler<Event> {

    override fun process(event: Event) {
        println("HandlerTwo handling $event")
    }
}