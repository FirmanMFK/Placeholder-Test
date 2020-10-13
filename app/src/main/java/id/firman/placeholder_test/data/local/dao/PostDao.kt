package id.firman.placeholder_test.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.firman.placeholder_test.data.model.post.PostResponse

@Dao
interface PostDao{

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListPosts(posts: List<PostResponse>)

    @Transaction
    fun insertPosts(posts: List<PostResponse>){
        for (i in 0 until 20) {
            posts[i].taken = true
        }
        insertListPosts(posts)
    }

    @Query("SELECT * FROM posts")
    fun fetchPosts(): List<PostResponse>

    @Query("SELECT * FROM posts WHERE id=:id")
    fun fetchPost(id:Int):PostResponse

    @Query("SELECT * FROM posts WHERE favorite=1")
    fun fetchFavoritesPosts(): List<PostResponse>

    @Query("UPDATE posts SET favorite=:isFavorite WHERE id=:id")
    fun updateFavoritePost(id:Int, isFavorite:Boolean)

    @Query("UPDATE posts set taken=1 WHERE id IN(SELECT id from posts LIMIT 20)")
    fun updateBlueIndicatorPosts()

    @Query("UPDATE posts set taken=0 WHERE id=:id")
    fun takenPost(id:Int)

    @Query("DELETE FROM posts WHERE id=:id")
    fun deletePost(id:Int)

    @Query("DELETE FROM posts")
    fun deletePosts()

    @Query("SELECT * FROM posts WHERE title LIKE :query")
    fun getSearchPosts(query: String): List<PostResponse>


}