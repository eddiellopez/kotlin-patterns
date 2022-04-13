package chaindsl

import chain.Handler
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class HandlerLinkTest {

    @MockK
    private lateinit var handler: Handler<String>

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun getPredicateSupplier() {
    }

    @Test
    fun getHandlerSupplier() {
    }

    @Test
    fun byMultiple() {
        // Considering the following link:
        assertThrows<IllegalArgumentException> {
            with(HandlerLink<String>()) {
                // When calling by multiple times, we expect an exception
                by { handler }
                by { handler }
            }
        }
    }

    @Test
    internal fun by() {
        // Considering the following link:
        with(HandlerLink<String>()) {
            // When calling by...
            by { handler }

            // We expect a handler supplier to be recorded.
            assertThat(handlerSupplier, `is`(notNullValue()))
            assertThat(handlerSupplier.invoke(), `is`(handler))
        }
    }

    @Test
    fun wheneverMultiple() {
        // Considering the following link:
        assertThrows<IllegalArgumentException> {
            with(HandlerLink<String>()) {
                // When calling whenever multiple times, we expect an exception
                whenever { true }
                whenever { true }
            }
        }
    }

    @Test
    internal fun whenever() {
        // Considering the following link:
        with(HandlerLink<String>()) {
            // When calling whenever...
            whenever { true }

            // We expect a handler supplier to be recorded.
            assertThat(predicateSupplier, `is`(notNullValue()))
            assertThat(predicateSupplier.invoke("Data"), `is`(true))
        }
    }
}