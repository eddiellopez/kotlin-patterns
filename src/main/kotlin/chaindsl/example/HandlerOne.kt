package chaindsl.example

import chain.Handler
import data.Event

class HandlerOne : Handler<Event> {

    override fun process(event: Event) {
        println("HandlerOne handling $event")
    }
}