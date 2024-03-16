package com.dahlaran.newmovshow.common.domain

import android.content.Context
import com.dahlaran.newmovshow.common.data.Error

class FrontSimpleCallbackImpl(context: Context) : FrontSimpleCallback(context) {

    override fun onSuccess() {
        // Do nothing
    }

    override fun onFailed(error: Error) {
        context.get()?.let {
            error.showUsingCodeOnly(it)
        }
    }
}