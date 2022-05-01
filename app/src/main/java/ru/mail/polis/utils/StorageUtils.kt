package ru.mail.polis.utils

import android.content.Context
import java.lang.IllegalStateException

class StorageUtils private constructor() {
    enum class StorageKey(val key: String) {
        EMAIL("USER_EMAIL")
    }

    companion object {
        private const val COMMON_SECTION: String = "COMMON_SECTION"

        @Throws(IllegalStateException::class)
        fun getCurrentUserEmail(ctx: Context): String {
            return ctx.getSharedPreferences(
                COMMON_SECTION, Context.MODE_PRIVATE
            ).getString(StorageKey.EMAIL.key, null)
                ?: throw IllegalStateException("Email of current user is not exist")
        }

        fun setValue(ctx: Context, key: StorageKey, value: String?) {
            ctx.getSharedPreferences(
                COMMON_SECTION,
                Context.MODE_PRIVATE
            )
                ?.edit()
                ?.putString(key.key, value)
                ?.apply()
        }

        fun getValue(ctx: Context, key: StorageKey): String? {
            return ctx.getSharedPreferences(
                COMMON_SECTION, Context.MODE_PRIVATE
            ).getString(key.key, null)
        }
    }
}
