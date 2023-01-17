package app.lahzebar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.lahzebar.data.local.dao.CommentDao
import app.lahzebar.data.local.dao.PostDao
import app.lahzebar.data.local.dao.UserDao
import app.lahzebar.data.local.entitys.CommentEntity
import app.lahzebar.data.local.entitys.PostEntity
import app.lahzebar.data.local.entitys.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PostEntity::class,
        CommentEntity::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class RoomManager : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val postDao: PostDao
    abstract val commentDao: CommentDao
}
