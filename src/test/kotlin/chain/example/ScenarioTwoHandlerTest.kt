package chain.example

import chain.Handler
import data.Event
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScenarioTwoHandlerTest {

    @MockK
    private lateinit var event: Event

    @MockK
    private lateinit var nextHandler: Handler<Event>

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun processNot() {
        // Considering the handler under test:
        val handler = spyk(ScenarioTwoHandler(nextHandler))

        // Given the data name doesn't start with "B":
        every {
            event.name
        } returns "Other"

        // When processing...
        handler.process(event)

        // Whe expect the data to be passed to the next handler.
        verify {
            handler.passToNext(event)
        }
    }

    @Test
    fun process() {
        // Considering the handler under test:
        val handler = spyk(ScenarioTwoHandler(nextHandler))

        // Given the data name starts with "B":
        every {
            event.name
        } returns "Beats"

        // When processing...
        handler.process(event)

        // Whe expect the data NOT to be passed to the next handler.
        verify(exactly = 0) {
            handler.passToNext(event)
        }

        // We expect the data to be processed.
        verify {
            event.name
        }
    }
}