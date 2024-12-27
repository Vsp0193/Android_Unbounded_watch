package com.unbounded.realizingself.utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ConvertJsonToMap {
    @Throws(JSONException::class)
    fun jsonToMapKotlin(json: JSONObject): Map<String, String> {
        val retMap: MutableMap<String, String> = HashMap()
        if (json !== JSONObject.NULL) {
            retMap.putAll(toMapKotlin(json))
        }
        return retMap
    }
    @Throws(JSONException::class)
    fun toMapKotlin(`object`: JSONObject): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        val keysItr = `object`.keys()
        while (keysItr.hasNext()) {
            val key = keysItr.next()
            var value: Any? = `object`[key]
            if (value is JSONArray) {
                value = toList(value)
            } else if (value is JSONObject) {
                value = toMapKotlin(value)
            }
            if (value != null) {
                map[key] = value.toString()
            }
        }
        return map
    }

    @Throws(JSONException::class)
    fun jsonToMap(json: JSONObject): Map<String?, Any?> {
        var retMap: Map<String?, Any?> = HashMap()
        if (json !== JSONObject.NULL) {
            retMap = toMap(json)
        }
        return retMap
    }

    @Throws(JSONException::class)
    fun jsonToMapManish(json: JSONObject): Map<String?, Any?> {
        var retMap: Map<String?, Any?> = HashMap()

        retMap = toMap(json)

        return retMap
    }

    @Throws(JSONException::class)
    fun toMap(`object`: JSONObject): Map<String?, Any?> {
        val map: MutableMap<String?, Any?> = HashMap()
        val keysItr = `object`.keys()
        while (keysItr.hasNext()) {
            val key = keysItr.next()
            var value = `object`[key]
            if (value is JSONArray) {
                value = toList(value)
            } else if (value is JSONObject) {
                value = toMap(value)
            }
            map[key] = value
        }
        return map
    }

    @Throws(JSONException::class)
    fun toList(array: JSONArray): List<Any> {
        val list: MutableList<Any> = ArrayList()
        for (i in 0 until array.length()) {
            var value = array[i]
            if (value is JSONArray) {
                value = toList(value)
            } else if (value is JSONObject) {
                value = toMap(value)
            }
            list.add(value)
        }
        return list
    }
}