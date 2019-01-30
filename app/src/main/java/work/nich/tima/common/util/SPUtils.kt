package work.nich.tima.common.util

import android.content.Context
import work.nich.tima.Tima

object SPUtils {
    private val mSp by lazy {
        Tima.app.applicationContext.getSharedPreferences("Tima", Context.MODE_PRIVATE)
    }

    fun setString(key: String, value: String) {
        mSp.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return mSp.getString(key, "")
    }

    fun getString(key: String, defaultValue: String): String? {
        return mSp.getString(key, defaultValue)
    }

    fun setInt(key: String, value: Int) {
        mSp.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return mSp.getInt(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return mSp.getInt(key, defaultValue)
    }

    fun setLong(key: String, value: Long) {
        mSp.edit().putLong(key, value).apply()
    }

    fun getLong(key: String): Long {
        return mSp.getLong(key, 0L)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return mSp.getLong(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        mSp.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return mSp.getBoolean(key, false)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return mSp.getBoolean(key, defaultValue)
    }
}