package id.firman.placeholder_test.data.local.db

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import id.firman.placeholder_test.data.local.dao.PostDao
import id.firman.placeholder_test.data.model.post.PostResponse
import id.firman.placeholder_test.utils.Const
import java.util.concurrent.Executors


@Database(entities = [
    PostResponse::class
],version = 1, exportSchema = false)
abstract class PostDb : RoomDatabase(){
    abstract val postdao: PostDao

}