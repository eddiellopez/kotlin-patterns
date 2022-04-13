package chain

/**
 * A handler of arbitrary data.
 */
interface Handler<T> {
    /**
     * Processes the data.
     *
     * Implementations will inspect the data to determine if it will consume it or pass it to the next handler.
     * When the data is consumed, no further handler in the chain will be called.
     *
     * @param event The data.
     */
    fun process(event: T)
}
