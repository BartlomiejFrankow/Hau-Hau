package com.example.my_pc.hauhau.commons

import android.support.annotation.AnimRes
import com.example.my_pc.hauhau.R
import com.example.my_pc.hauhau.R.anim.*

/**
 * Created by my_pc on 11/06/2018.
 */

enum class TransactionAnim(@AnimRes val animOpenFragmentWithFadeIn: Int, @AnimRes val noAnimOpenFragment: Int, @AnimRes val animCloseFragmentWithFadeIn: Int, @AnimRes val noAnimCloseFragment: Int) {
    FADE_OUT_LONG(R.anim.fade_in, fade_out, fade_in_long, fade_out)
}