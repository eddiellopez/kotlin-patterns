package chain


class ScenarioThreeHandler(next: Handler<Data>? = null) : BaseDataHandler(next) {
    override fun process(data: Data) {
        // Default and final handler in the chain: always handle.
        println("ScenarioThree handling ${data.name}")
    }
}
