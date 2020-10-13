package id.firman.placeholder_test.views.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import id.firman.placeholder_test.R
import id.firman.placeholder_test.databinding.ActivityDetailBinding
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by inject<DetailPostViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val args = intent.extras

        if (args != null) {
            val id = args.getInt("id_post")
            viewModel.fetchPost(id)
        }


        viewModel.postDetail.observe(this, Observer { post->
            binding.bodyText.text = post.body
            binding.tittleText.text = post.title
        })


        binding.backImage.setOnClickListener {
            super.onBackPressed()
        }


    }

}