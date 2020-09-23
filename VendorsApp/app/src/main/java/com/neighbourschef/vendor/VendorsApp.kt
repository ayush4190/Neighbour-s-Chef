package com.neighbourschef.vendor

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.neighbourschef.vendor.util.common.HyperlinkedDebugTree
import com.neighbourschef.vendor.util.common.ReleaseTree
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber

class VendorsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )

        AndroidThreeTen.init(this)
        if (BuildConfig.DEBUG) Timber.plant(HyperlinkedDebugTree()) else Timber.plant(ReleaseTree())
    }
}
