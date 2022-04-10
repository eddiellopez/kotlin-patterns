package chain

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScenarioThreeHandlerTest {

    @MockK
    private lateinit var data: Data

    @MockK
    private lateinit var nextHandler: Handler<Data>

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun process() {
        // Considering the handler under test:
        val handler = spyk(ScenarioThreeHandler(nextHandler))

        // Regardless of the data
        every {
            data.name
        } returns "Any name is OK"

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