package chaindsl

import chain.Handler
import data.Event
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ChainTest {

    @MockK
    private lateinit var handleableContext: HandleableContext<Event>

    @MockK
    private lateinit var handleableContextCreator: () -> HandleableContext<Event>

    @MockK
    private lateinit var handler2: Handler<Event>

    @MockK
    private lateinit var handler3: Handler<Event>

    @MockK
    private lateinit var handler: Handler<Event>

    @MockK
    private lateinit var predicateValidator: () -> Unit

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        every { handleableContextCreator.invoke() } returns handleableContext
    }

    @Test
    fun processEmptyChain() {
        // Considering an empty chain:
        val emptyChain = Chain.chainOf<Event> { }

        // When calling process, we expect an exception.
        assertThrows<IllegalArgumentException> {
            emptyChain.process(Event("Name"))
        }
    }

    @Test
    fun processIncompleteHandler() {
        // Considering the following chain (formed by an incomplete handler):
        val chain = Chain.chainOf<Event> {
            handle {
                by { handler }
            }
        }

        // When calling process, we expect an exception.
        assertThrows<UninitializedPropertyAccessException> {
            chain.process(Event("Name"))
        }
    }

    @Test
    fun processIncompleteHandlerToo() {
        // Considering the following chain (formed by an incomplete handler):
        val chain = Chain.chainOf<Event> {
            handle {
                whenever { name.isNotEmpty() }
            }
        }

        // When calling process, we expect an exception.
        assertThrows<UninitializedPropertyAccessException> {
            chain.process(Event("Name"))
        }
    }

    @Test
    fun processNoHandler() {
        // Considering the following chain (where no handler can handle the data):
        val chain = Chain.chainOf<Event> {
            handle {
                whenever { false }
            }
        }

        // When calling process, we expect an exception.
        assertThrows<IllegalArgumentException> {
            chain.process(Event("Name"))
        }
    }

    @Test
    fun process() {
        // Considering the following chain:
        val chain = Chain.chainOf<Event> {
            handle {
                by { handler }
                whenever { predicateValidator.invoke(); true }
            }
        }

        // We expect one link on the chain.
        assertThat(chain.links.size, `is`(1))

        // Given the predicate is satisfied:
        // When calling process...
        val data = Event("Name")
        chain.process(data)

        // We expect the predicate to be tested.
        verify { predicateValidator.invoke() }

        // We expect the handler to be called.
        verify { handler.process(data) }
    }

    @Test
    fun processSomeHandlers() {
        // Considering the following chain of three handlers:
        val chain = Chain.chainOf<Event> {
            handle {
                by { handler }
                whenever { predicateValidator.invoke(); false }
            }

            handle {
                by { handler2 }
                whenever { predicateValidator.invoke(); false }
            }

            // Where only the last one processes:
            handle {
                by { handler3 }
                whenever { predicateValidator.invoke(); true }
            }
        }

        // We expect one link on the chain.
        assertThat(chain.links.size, `is`(3))

        // When calling process...
        chain.process(Event("Name"))

        // We expect the predicate to be tested three times.
        verify(exactly = 3) { predicateValidator.invoke() }

        // We expect the handler to be called.
        verify(exactly = 1) { handler3.process(any()) }
        verify(exactly = 0) { handler.process(any()) }
        verify(exactly = 0) { handler2.process(any()) }
    }

    @Test
    fun chainOfDuplicatedWhenever() {
        // Considering the following chain (formed by a duplicated 'whenever' clause):
        // When building the chain, we expect an exception.
        assertThrows<IllegalArgumentException> {

            Chain.chainOf<Event> {
                handle {
                    by { handler }
                    whenever { name.isNotEmpty() }
                    whenever { name.isEmpty() }
                }
            }
        }
    }

    @Test
    fun chainOfDuplicatedBy() {
        // Considering the following chain (formed by a duplicated 'by' clause):
        // When building the chain, we expect an exception.
        assertThrows<IllegalArgumentException> {

            Chain.chainOf<Event> {
                handle {
                    by { handler }
                    by { handler }
                    whenever { name.isEmpty() }
                }
            }
        }
    }

    @Test
    fun chainOf() {
        // Given a handlers context consumer such as:
        val handlersContextConsumer = mockk<(HandleableContext<*>) -> Unit>()
        every { handlersContextConsumer.invoke(any()) } just Runs

        // When building a chain...
        val chain = Chain.chainOf(handleableContextCreator, handlersContextConsumer)

        // We expect the creator is requested a new context.
        verify { handleableContextCreator.invoke() }
        // We expect the consumer to be invoked.
        verify { handlersContextConsumer.invoke(any()) }
        // And a non-null chain produced.
        assertThat(chain, `is`(notNullValue()))
    }
}