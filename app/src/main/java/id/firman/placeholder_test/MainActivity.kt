package id.firman.placeholder_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import id.firman.placeholder_test.databinding.ActivityMainBinding
import id.firman.placeholder_test.views.SharedViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    var lastMenuId = 0

    private val sharedViewModel by inject<SharedViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val navController = findNavController(R.id.nav_host)
        binding.bottomNav.setOnNavigationItemSelectedListener {
            val menuId = it.itemId
            if (lastMenuId == menuId) return@setOnNavigationItemSelectedListener true
            lastMenuId = menuId
            when(lastMenuId){
                R.id.postListFragment -> {
                    navController.navigate(R.id.postListFragment)
                    hideOptions()
                }
                else -> {
                    navController.navigate(R.id.searchFragment)
                    hideOptions()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        binding.reloadPostsImage.setOnClickListener {
            sharedViewModel.needRemoteData(true)
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.titleToolbar.text = destination.label
        }

    }

    fun hideOptions(){
        binding.optionsConstraint.isVisible = !binding.optionsConstraint.isVisible
    }
}
