package chaindsl


/**
 * A Chain of Responsibility.
 *
 * @param links The links of the chain.
 */
class Chain<T> private constructor(val links: List<LinkableHandler<T>>) {

    /**
     * Processes the data.
     *
     * @param data The data to process.
     * @throws IllegalArgumentException if no handler has been installed that can handle the data.
     */
    fun process(data: T) {
        links.firstOrNull { it.predicateSupplier.invoke(data) }
            ?.handlerSupplier
            ?.invoke()
            ?.process(data)
            ?: throw IllegalArgumentException("No handler set up to handle: $data")
    }

    companion object : ChainFactory {

        /**
         * Builds a chain to handle T.
         *
         * @param T the data type the chain will handle.
         * @param handleableContextCreator A creator of [HandleableContext]s, which supplies a new one each time.
         * @param handleableContextConsumer A consumer that passes a context to call [HandleableContextImpl.handle] on.
         */
        override fun <T> chainOf(
            handleableContextCreator: () -> HandleableContext<T>,
            handleableContextConsumer: HandleableContext<T>.() -> Unit
        ): Chain<T> {
            // Build a handleable context and pass it to the DSL calling code.
            with(handleableContextCreator.invoke()) {
                handleableContextConsumer.invoke(this)
                // Return a chain formed by the context links.
                return Chain(links)
            }
        }
    }
}

