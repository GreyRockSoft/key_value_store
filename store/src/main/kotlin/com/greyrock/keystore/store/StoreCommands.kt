package com.greyrock.keystore.store

data class StoreCommand(var command: Command, var key: String, var value: String)

interface Command {
    fun execute(store: Store, storeCommand: StoreCommand)
}

object SetValue : Command {
    override fun execute(store: Store, storeCommand: StoreCommand) {
        store.storeValue(storeCommand.key, storeCommand.value)
    }
}
