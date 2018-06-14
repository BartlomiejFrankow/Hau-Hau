package com.example.my_pc.hauhau.ui.listen

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.example.my_pc.hauhau.BR
import com.example.my_pc.hauhau.R
import com.example.my_pc.hauhau.databinding.FragmentListeninBinding
import com.example.my_pc.hauhau.ui.base.BaseFragment
import com.example.my_pc.hauhau.ui.home.HomeActivity

/**
 * Created by my_pc on 13/06/2018.
 */

class ListenFragment : BaseFragment<HomeActivity, FragmentListeninBinding, ListenViewModel>(), ListenNavigator{

    override fun provideViewModel(): ListenViewModel = ViewModelProviders.of(this).get(ListenViewModel::class.java)
    override fun getBindingVariable(): Int = BR.obj
    override fun getLayoutId(): Int = R.layout.fragment_listenin

    companion object {
        fun newInstance(): ListenFragment {
            return ListenFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBaseActivity().startPostponedEnterTransition()
        viewModel.setNavigator(this)
    }

}