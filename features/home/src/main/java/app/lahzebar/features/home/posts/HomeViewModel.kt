package app.lahzebar.features.home.posts

import android.util.Log
import app.lahzebar.domain.api.PostDataSource
import app.lahzebar.domain.api.UserDataSource
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userDataSource: UserDataSource,
    private val postDataSource: PostDataSource
) : BaseVM
<HomeAction, HomeMutation, HomeEffect, HomeState>(HomeState::class.java) {
    override fun initialState(): HomeState = HomeState.Init

    override fun handle(action: HomeAction): Flow<HomeMutation> = channelFlow {
        when (action) {
            is HomeAction.FetchData -> {

                userDataSource.getSuggested().onEach {

                    if (it.isNotEmpty()) {
                        emitEffect(HomeEffect.ShowPosts(it))
                        Log.d("tijaniididieieiied","$it ")
                    }
                }.collect()

                //
                //
                // userDataSource.getPostWithUserAndComment().onEach {
                //
                //     if (it.isNotEmpty()) {
                //         emitEffect(HomeEffect.ShowPosts(it))
                //         it.forEach {
                //             Log.d("tijaniididieieiied","${it.post.postId} ")
                //             Log.d("tijaniididieieiied","${it.comments} ")
                //
                //         }
                //
                //     }
                // }.collect()
            }

            is HomeAction.UpdateLikeCount -> {

                if (action.currentPost.yourLiked)
                    postDataSource.updateLikePost(
                        likeCount = action.currentPost.like - 1, yourLiked = false,
                        postId = action.currentPost.postId
                    )
                else
                    postDataSource.updateLikePost(
                        likeCount = action.currentPost.like + 1, yourLiked = true,
                        postId = action.currentPost.postId
                    )
            }
        }
    }
}
