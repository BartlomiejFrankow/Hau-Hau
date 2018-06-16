package com.example.my_pc.hauhau.ui.home

import com.example.my_pc.hauhau.ui.base.BaseViewModel

/**
 * Created by my_pc on 11/06/2018.
 */

class HomeViewModel : BaseViewModel<HomeNavigator>() {

    fun onListenButtonClick(){
        getNavigator().onListenButtonClick()
    }

}