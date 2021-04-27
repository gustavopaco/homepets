package br.com.cotemig.homepets.util

import android.content.Context

class SharedPreferenceHelper {

    companion object{

        fun saveString(context: Context,filename : String?, key : String?, value : String?){

            val preferences = context.applicationContext.getSharedPreferences(filename,Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun readString(context: Context, filename: String?, key: String?, defaultvalue : String?) : String?{

            val preferences = context.applicationContext.getSharedPreferences(filename, Context.MODE_PRIVATE)
            return preferences.getString(key, defaultvalue)
        }

        fun saveBoolean(context: Context, filename: String?, key: String?, value: Boolean?){

            val preferences = context.applicationContext.getSharedPreferences(filename, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean(key, value!!)
            editor.apply()
        }

        fun readBoolean(context: Context, filename: String?, key: String?, defaultvalue: Boolean) : Boolean {

            val preferences = context.applicationContext.getSharedPreferences(filename, Context.MODE_PRIVATE)
            return preferences.getBoolean(key, defaultvalue)
        }

        fun saveInt(context: Context, filename: String?, key: String?, value : Int){

            val preferences = context.applicationContext.getSharedPreferences(filename, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun readInt(context: Context, filename: String?, key: String?, defaultvalue: Int) : Int{

            val preferences = context.applicationContext.getSharedPreferences(filename, Context.MODE_PRIVATE)
            return preferences.getInt(key, defaultvalue)
        }

        fun delete(context: Context, filename: String?, key: String?){

            val preferences = context.applicationContext.getSharedPreferences(filename, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.remove(key)
            editor.apply()
        }

    }
}