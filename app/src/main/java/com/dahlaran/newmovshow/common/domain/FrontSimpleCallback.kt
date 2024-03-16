package com.dahlaran.newmovshow.common.domain

import android.content.Context
import androidx.annotation.UiThread
import com.dahlaran.newmovshow.common.data.Error
import java.lang.ref.WeakReference

abstract class FrontSimpleCallback(context: Context) {

    val context: WeakReference<Context?> = WeakReference(context)

    @UiThread
    abstract fun onSuccess()

    @UiThread
    abstract fun onFailed(error: Error)
}
