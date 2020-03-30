package android.myc.usergenerator.db

import android.myc.usergenerator.bean.UserBean
import androidx.room.*
import io.reactivex.Maybe

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(userBean: List<UserBean>?): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userBean: UserBean): Long

    @Query("select * from users")
    fun getAllTasks(): List<UserBean>
}