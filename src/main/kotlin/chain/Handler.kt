package chain

/**
 * A handler of arbitrary data.
 */
interface Handler<T> {
    /**
     * Processes the data.
     *
     * A handler will inspect the data to determine if it will consume it or pass it to the next.
     * When the data is consumed, no further handler in the chain will be called.
     *
     * @param data The data.
     */
    fun process(data: T)

    /**
     * Pass the data to the next handler in the chain.
     *
     * @param data The data.
     */
    fun passToNext(data: T)
}
