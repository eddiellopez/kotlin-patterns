package chaindsl

import chain.Handler

/**
 * A link handler that provides `by` and `whenever`.
 */
interface LinkableHandler<T> {

    /**
     * A predicate supplier.
     */
    val predicateSupplier: (T.() -> Boolean)

    /**
     * The handler of this link.
     */
    val handlerSupplier: () -> Handler<T>

    /**
     * States the handler object.
     *
     * @param handlerSupplier A lambda that will provide the handler.
     */
    fun by(handlerSupplier: () -> Handler<T>)

    /**
     * Sets up the predicate fot this handler to consume the data.
     *
     * @param predicateSupplier A lambda that will receive the data and evaluate it,
     * returning `true` if should be handled, `false` otherwise.
     */
    fun whenever(predicateSupplier: T.() -> Boolean)
}