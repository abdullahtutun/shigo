package com.shi.shigo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarItemView
import com.shi.shigo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
        setBottomNavigation()
        hideSplashUI()


    }

    private fun initBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> binding.bottomNavigation.visibility = View.GONE
                R.id.registerFragment -> binding.bottomNavigation.visibility = View.GONE
                else -> binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

    }

    private fun hideSplashUI(){
        Handler(Looper.getMainLooper()).postDelayed({
            binding.navHostFragment.visibility = View.VISIBLE
            binding.layoutSplash.clSplash.visibility = View.GONE
        }, 2000)
    }

    private fun setBottomNavigation(){
        val menu = binding.bottomNavigation.menu
        val menuItem = menu.getItem(2)
        val navigationBarItemView: NavigationBarItemView = binding.bottomNavigation.findViewById(menuItem.itemId)
        navigationBarItemView.setBackgroundResource(R.drawable.ic_box)
        val iconView: View = navigationBarItemView.findViewById(com.google.android.material.R.id.navigation_bar_item_icon_view)
        val iconViewParams: FrameLayout.LayoutParams = iconView.layoutParams as FrameLayout.LayoutParams
        iconViewParams.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50F, resources.displayMetrics).toInt()
        iconViewParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50F, resources.displayMetrics).toInt()
        iconView.layoutParams = iconViewParams
    }
}