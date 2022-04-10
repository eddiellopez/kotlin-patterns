package chain

/**
 * A base handler class.
 *
 * All handlers should extend from this class.
 *
 * @param next The next handler.
 */
abstract class BaseDataHandler(val next: Handler<Data>?) : Handler<Data> {
    override fun passToNext(data: Data) {
        next?.process(data) ?: throw IllegalArgumentException("No handler could handle $data!")
    }
}