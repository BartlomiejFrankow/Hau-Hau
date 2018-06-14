package com.example.my_pc.hauhau.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.example.my_pc.hauhau.BR
import com.example.my_pc.hauhau.R
import com.example.my_pc.hauhau.commons.TransactionAnim
import com.example.my_pc.hauhau.databinding.FragmentHomeBinding
import com.example.my_pc.hauhau.ui.base.BaseFragment
import com.example.my_pc.hauhau.ui.listen.ListenFragment
import com.example.my_pc.hauhau.utils.helpers.BuilderManager
import com.github.fabtransitionactivity.SheetLayout
import com.nightonke.boommenu.BoomButtons.HamButton
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * Created by my_pc on 11/06/2018.
 */

class HomeFragment : BaseFragment<HomeActivity, FragmentHomeBinding, HomeViewModel>(), HomeNavigator, SheetLayout.OnFabAnimationEndListener{

    override fun provideViewModel(): HomeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    override fun getBindingVariable(): Int = BR.obj
    override fun getLayoutId(): Int = R.layout.fragment_home

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBaseActivity().startPostponedEnterTransition()
        viewModel.setNavigator(this)
        bottom_sheet.setFab(fab)
        bottom_sheet.setFabAnimationEndListener(this)

        setUpBoomButton()
    }

    override fun onListenButtonClick() {
            bottom_sheet.expandFab()
    }

    override fun onFabAnimationEnd() {
        getBaseActivity().replaceFragment(ListenFragment.newInstance(), true, TransactionAnim.FADE_OUT_LONG)
        bottom_sheet.contractFab()
    }

    fun setUpBoomButton() {
        val builder = HamButton.Builder()

        for (i in 0 until boom.piecePlaceEnum.pieceNumber()) {
            builder.normalImageRes(R.drawable.ic_box_empty)
                    .normalColorRes(R.color.colorPrimaryDark)
                    .listener { index ->
                        boomButtonClick(index)
                    }

            boom.addBuilder(builder)
        }
    }

    fun boomButtonClick(pos: Int){
        val boomButton = boom.getBoomButton(pos) ?: return
        boomButton.imageView?.setImageResource(BuilderManager.getImageResource())
        boomButton.textView?.text = "I'm changed!"
        boomButton.subTextView?.text = "I'm changed, too!"
    }

}