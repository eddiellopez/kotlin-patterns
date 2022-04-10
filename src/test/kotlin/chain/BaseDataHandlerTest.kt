package chain

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class BaseDataHandlerTest {

    @MockK
    private lateinit var data: Data

    @MockK
    private lateinit var nextHandler: Handler<Data>

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun passToNextNot() {
        // Considering a chain based on a TestBaseDataHandler, with a next handler:
        val chain = spyk(TestBaseDataHandler(nextHandler))

        // Given the data should be processed:
        chain.shouldProcess = true

        // When calling process on some data...
        chain.process(data)

        // We expect it to be processed.
        verify { data.name }
        // We don't expect the next handler to be called.
        verify(exactly = 0) {
            chain.passToNext(any())
        }
    }

    @Test
    fun passToNextNull() {
        // Considering a chain based on a TestBaseDataHandler, with a null next handler:
        val chain = spyk(TestBaseDataHandler(null))

        // Given the data should not be processed:
        chain.shouldProcess = false

        assertThrows<IllegalArgumentException> {
            // When calling process on some data, since the next is null, we expect an exception.
            chain.process(data)
        }
    }

    @Test
    fun passToNext() {
        // Considering a chain based on a TestBaseDataHandler, with a valid next handler:
        val chain = spyk(TestBaseDataHandler(nextHandler))

        // Given the data should not be processed:
        chain.shouldProcess = false

        // When calling process on some data...
        chain.process(data)

        // We don't expect it to be processed.
        verify(exactly = 0) {
            data.name
        }

        // We expect the next handler to be called.
        verify {
            chain.passToNext(any())
        }
    }

    class TestBaseDataHandler(next: Handler<Data>?) : BaseDataHandler(next) {
        var shouldProcess: Boolean = false

        override fun process(data: Data) {
            // Conditionally process, for testing
            if (shouldProcess) {
                data.name
            } else {
                passToNext(data)
            }
        }
    }
}