package chaindsl

import chain.Handler

/**
 * A handling link in the chain.
 *
 * Mainly composed of [by] and [whenever] calls.
 */
@ChainOfResponsibilityDsl
class HandlerLink<T> : LinkableHandler<T> {

    override lateinit var predicateSupplier: (T.() -> Boolean)

    override lateinit var handlerSupplier: () -> Handler<T>

    /**
     * Specifies the handler that will process the data when the associate predicate is true.
     *
     * @param handlerSupplier A function that will provide the handler.
     */
    override fun by(handlerSupplier: () -> Handler<T>) {
        if (this::handlerSupplier.isInitialized) {
            throw IllegalArgumentException("Can't have more than one 'by' clause!")
        }

        this.handlerSupplier = handlerSupplier
    }

    /**
     * Sets up the predicate fot this handler to consume the data.
     *
     * When the predicate evaluates to true, the [Handler] in the associated
     * `handle` clause will be used to process the data.
     *
     * @param predicateSupplier A function that will receive the data and evaluate it,
     * returning `true` if it should be handled, `false` otherwise.
     */
    override fun whenever(predicateSupplier: (T.() -> Boolean)) {
        if (this::predicateSupplier.isInitialized) {
            throw IllegalArgumentException("Can't have more than one 'whenever' clause!")
        }

        this.predicateSupplier = predicateSupplier
    }
}