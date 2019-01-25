package com.vice.currentactivityfragment

import android.app.Application
import com.vice.curacfrlib.CurAcFr

/**
 * author:vice
 * email:vicedev1001@gmail.com
 * date:2019/1/23 19:32
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        CurAcFr.init(this)
    }

}