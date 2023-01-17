package app.lahzebar.domain.intractor

import app.lahzebar.domain.api.CommentDataSource
import app.lahzebar.domain.api.PostDataSource
import app.lahzebar.domain.api.StoreDataSource
import app.lahzebar.domain.api.UserDataSource
import app.lahzebar.domain.model.room.Comment
import app.lahzebar.domain.model.room.Post
import app.lahzebar.domain.model.room.User
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenerateDataInteractor @Inject constructor(
    private val userDataSource: UserDataSource,
    private val postDataSource: PostDataSource,
    private val commentDataSource: CommentDataSource,
    private val storeDataSource: StoreDataSource
) {

    suspend operator fun invoke(data: Triple<List<User>, List<Post>, List<Comment>>): Unit =
        coroutineScope {
            if (!storeDataSource.getGeneratePrimaryData().first()) {
                val insertUserJob = launch { userDataSource.insertUsers(data.first) }
                val insertPostJob = launch { postDataSource.insertPosts(data.second) }
                val insertCommentJob = launch { commentDataSource.insertComments(data.third) }
                joinAll(insertUserJob, insertPostJob, insertCommentJob)
                storeDataSource.saveGeneratePrimaryData(true)
            }
        }
}
