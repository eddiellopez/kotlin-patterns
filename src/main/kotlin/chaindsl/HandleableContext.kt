package chaindsl

/**
 * A context that supports handling.
 */
interface HandleableContext<T> {

    /**
     * A list of the links on the chain.
     */
    val links: MutableList<LinkableHandler<T>>

    /**
     * Allows defining how to handle using `by` and `whenever`.
     *
     * @param handlerLinkConsumer A consumer of a handler link.
     */
    fun handle(handlerLinkConsumer: LinkableHandler<T>.() -> Unit)
}