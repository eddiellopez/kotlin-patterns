package chain

class ScenarioTwoHandler(next: Handler<Data>? = null) : BaseDataHandler(next) {
    override fun process(data: Data) {
        if (data.name.startsWith("B")) {
            println("ScenarioTwo handling $data")
        } else {
            passToNext(data)
        }
    }
}