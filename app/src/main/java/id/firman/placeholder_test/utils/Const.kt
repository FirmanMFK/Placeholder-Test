package id.firman.placeholder_test.utils

object Const {
    val dbName = "posts.db"
    const val URL_BASE = "https://jsonplaceholder.typicode.com"
    fun Log(message:Any){
        android.util.Log.d("Log", message.toString())
    }
}