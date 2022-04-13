package chaindsl

/**
 * A place where multiple handling statements can be added.
 *
 * @param linkableHandlerCreator A class that creates new [LinkableHandler]s.
 */
@ChainOfResponsibilityDsl
class HandleableContextImpl<T>(
    private val linkableHandlerCreator: () -> LinkableHandler<T>
) : HandleableContext<T> {

    override val links: MutableList<LinkableHandler<T>> = mutableListOf()

    /**
     * Allows defining how to handle using `by` and `whenever`.
     *
     * @param handlerLinkConsumer A consumer of a handler link.
     */
    override fun handle(handlerLinkConsumer: LinkableHandler<T>.() -> Unit) {
        with(linkableHandlerCreator.invoke()) {
            handlerLinkConsumer.invoke(this)
            links.add(this)
        }
    }
}