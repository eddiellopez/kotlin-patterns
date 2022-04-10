package chain

/**
 * A handler that processes data with name starting with the letter A.
 */
class ScenarioOneHandler(next: Handler<Data>? = null) : BaseDataHandler(next) {
    override fun process(data: Data) {
        // Inspect the data looking for a handling trait.
        if (data.name.startsWith("A")) {
            println("ScenarioOne handling $data")
        } else {
            // Can't handle this data, calling next link in the chain.
            passToNext(data)
        }
    }
}