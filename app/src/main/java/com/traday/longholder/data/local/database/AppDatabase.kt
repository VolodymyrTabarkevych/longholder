package com.traday.longholder.data.local.database

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.traday.longholder.data.local.database.dao.CryptoDao
import com.traday.longholder.data.local.database.dao.NotificationDao
import com.traday.longholder.data.local.database.dao.UserDao
import com.traday.longholder.data.local.entity.CryptoEntity
import com.traday.longholder.data.local.entity.NotificationEntity
import com.traday.longholder.data.local.entity.ReportEntity
import com.traday.longholder.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        CryptoEntity::class,
        NotificationEntity::class,
        ReportEntity::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    var isDataBaseCreated = MutableLiveData<Boolean>()

    companion object {

        private const val dbName = "DB_LONG_HOLDER"
        private var dbInstance: AppDatabase? = null

        fun getInstance(app: Context): AppDatabase {
            if (dbInstance == null) {
                synchronized(AppDatabase::class.java) {
                    dbInstance = createDataBase(app)
                    dbInstance!!.updateDatabaseCreated(app)
                }
            }
            return dbInstance!!
        }

        private fun createDataBase(app: Context): AppDatabase {
            return Room.databaseBuilder(app, AppDatabase::class.java, dbName)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(dbName).exists()) {
            provideDatabaseCreated()
        }
    }

    private fun provideDatabaseCreated() {
        isDataBaseCreated.postValue(true)
    }

    abstract fun userDao(): UserDao

    abstract fun notificationDao(): NotificationDao

    abstract fun cryptoDao(): CryptoDao
}