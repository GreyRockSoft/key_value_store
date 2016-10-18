package com.greyrock.keystore.store

import java.util.*

class Store {

    private val map = HashMap<String, String>()

    fun getValue(key: String): String? {
        return map[key]
    }

    fun storeValue(key: String, value: String): Unit {
        map[key] = value
    }
}
