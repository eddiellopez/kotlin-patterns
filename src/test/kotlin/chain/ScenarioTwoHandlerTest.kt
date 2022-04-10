package chain

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScenarioTwoHandlerTest {

    @MockK
    private lateinit var data: Data

    @MockK
    private lateinit var nextHandler: Handler<Data>

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
            data.name
        } returns "Other"

        // When processing...
        handler.process(data)

        // Whe expect the data to be passed to the next handler.
        verify {
            handler.passToNext(data)
        }
    }

    @Test
    fun process() {
        // Considering the handler under test:
        val handler = spyk(ScenarioTwoHandler(nextHandler))

        // Given the data name starts with "B":
        every {
            data.name
        } returns "Beats"

        // When processing...
        handler.process(data)

        // Whe expect the data NOT to be passed to the next handler.
        verify(exactly = 0) {
            handler.passToNext(data)
        }

        // We expect the data to be processed.
        verify {
            data.name
        }
    }
}