package android.myc.usergenerator.db

import android.content.Context
import android.myc.usergenerator.bean.UserBean
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserBean::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UsersDatabase::class.java, "users.db"
            ).build()
    }
}