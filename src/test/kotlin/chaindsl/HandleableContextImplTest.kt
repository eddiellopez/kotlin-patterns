package chaindsl

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class HandleableContextImplTest {

    @MockK
    private lateinit var linkableHandler: LinkableHandler<String>

    @MockK
    private lateinit var linkableHandlerCreator: () -> LinkableHandler<String>

    @MockK
    private lateinit var handlerLinkConsumer: LinkableHandler<String>.() -> Unit

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        every { linkableHandlerCreator.invoke() } returns linkableHandler
    }

    @Test
    fun getLinks() {
        // Considering a handleable context implementation such as:
        with(HandleableContextImpl(linkableHandlerCreator)) {
            // Each call to handle, should tribute a new handler link to the list.
            handle { }
            handle { }
            handle { }

            assertThat(links, `is`(notNullValue()))
            assertThat(links.size, `is`(3))
        }
    }

    @Test
    fun handle() {
        // Considering a handleable context implementation such as:
        with(HandleableContextImpl(linkableHandlerCreator)) {
            // When calling handle...
            handle(handlerLinkConsumer)

            // We expect a new LinkableHandler to be created.
            verify { linkableHandlerCreator.invoke() }
            // We expect the consumer to be invoked.
            verify { handlerLinkConsumer.invoke(any()) }
        }
    }
}