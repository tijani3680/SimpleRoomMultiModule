package app.lahzebar.features.home.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.lahzebar.features.home.adapters.PostAdapter
import app.lahzebar.features.home.databinding.FragmentHomeBinding
import core.views.base.BaseFragmentWithVM
import core.views.util.instancestate.instanceState

class HomeFragment : BaseFragmentWithVM<FragmentHomeBinding, HomeState, HomeEffect, HomeViewModel>(
    HomeViewModel::class.java
) {

    private lateinit var postAdapter: PostAdapter
    private var currentPosition by instanceState(0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        postAdapter = PostAdapter(onItemClickListener = { currentPost, position ->

            this.currentPosition = position
            val action =
                HomeFragmentDirections.actionFragmentHomeToPostDetailsFragment(currentPost.posWithComment[0].post.postId)
            findNavController().navigate(action)
        }, likeClickListener = {currentPost, position ->
                currentPosition = 0
                viewModel.emitAction(HomeAction.UpdateLikeCount(currentPost.posWithComment[0].post))
            })

        binding.postRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.postRecycler.adapter = postAdapter
    }

    override fun renderEffect(effect: HomeEffect) {
        when (effect) {
            is HomeEffect.ShowPosts -> {
                postAdapter.submitList(effect.posts.toList()).also {
                    if (currentPosition != 0)
                        binding.postRecycler.scrollToPosition(currentPosition)
                }
            }
        }
    }

    override fun renderState(state: HomeState) {
        when (state) {
            HomeState.Init ->
                viewModel.emitAction(HomeAction.FetchData)
        }
    }
}
