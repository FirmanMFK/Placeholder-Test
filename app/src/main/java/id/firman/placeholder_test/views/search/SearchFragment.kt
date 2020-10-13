package id.firman.placeholder_test.views.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.firman.placeholder_test.R
import id.firman.placeholder_test.data.model.post.PostResponse
import id.firman.placeholder_test.databinding.SearchFragmentBinding
import id.firman.placeholder_test.utils.smoothSnapToPosition
import id.firman.placeholder_test.views.SharedViewModel
import id.firman.placeholder_test.views.main.PostListFragment
import id.firman.placeholder_test.views.main.adapter.PostAdapter
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() =
            PostListFragment()
    }

    private lateinit var binding: SearchFragmentBinding
    private val viewModel by inject<SearchViewModel> ()
    private val sharedViewModel by inject<SharedViewModel> ()

    private lateinit var postsList : MutableList<PostResponse>

    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = PostAdapter{
            if (it.clickOnImage){
                viewModel.updateFavoritePostStatus(it.postId, it.isFavorite)
            }else{
                goToDetail(it.postId)
            }
        }

        binding.searchPost.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.searchPost.adapter = adapter


        viewModel.postList.observe(viewLifecycleOwner, Observer {
            postsList = it.toMutableList()
            adapter.submitList(postsList)
        })

        sharedViewModel.needRemoteData.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.fetchPosts(true)
                sharedViewModel.needRemoteData(false)
                binding.searchPost.smoothSnapToPosition(0)
            }
        })


        return binding.root
    }

    override fun onPause() {
        super.onPause()
        adapter.notifyDataSetChanged()
    }



    override fun onStart() {
        super.onStart()
        viewModel.fetchLocalPosts()
        adapter.notifyDataSetChanged()

    }

    private fun goToDetail(postId: Int) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailPostActivity(postId))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.etSearchPost.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(e: Editable?) {
                val queryText = e.toString()
                viewModel.searchPosts(queryText)
                adapter.notifyDataSetChanged()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        super.onViewCreated(view, savedInstanceState)
    }


}