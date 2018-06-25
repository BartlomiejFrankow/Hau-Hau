package com.example.my_pc.hauhau.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.my_pc.hauhau.BR
import com.example.my_pc.hauhau.R
import com.example.my_pc.hauhau.databinding.ActivityHomeBinding
import com.example.my_pc.hauhau.ui.base.BaseActivity

/**
 * Created by Bartlomie_Frankow on 11/06/2018.
 */

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun provideViewModel(): HomeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    override fun getBindingVariable(): Int = BR.obj
    override fun getLayoutId(): Int = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(HomeFragment.newInstance(), false)
    }

}