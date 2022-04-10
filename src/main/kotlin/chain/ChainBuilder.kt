package chain

/**
 * A Chain builder.
 */
object ChainBuilder {

    fun build(): Handler<Data> {
        return ScenarioOneHandler(
            ScenarioTwoHandler(
                ScenarioThreeHandler()
            )
        )
    }
}
