package se.warting.roboelectricinsertwithonconflict.room

import androidx.room.*

@Entity(indices = [Index(value = ["firstName"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo val firstName: String?,
    @ColumnInfo val lastName: String?
)


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(users: User) : Long
}

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}