package work.nich.tima.common.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import android.view.View

object DisplayUtils {

    fun getStatusBarHeight(r: Resources): Int {
        var result = 0
        val resourceId = r.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = r.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun setLightStatusBar(activity: Activity) {
        val isMIUI = setMIUIStatusBarStyle(activity)

        if (!isMIUI) {
            val window = activity.window
            val decor = window.decorView

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }
    }

    private fun setMIUIStatusBarStyle(activity: Activity): Boolean {
        var result = false
        val window = activity.window
        if (window != null) {
            val clazz = window.javaClass
            try {
                val darkModeFlag: Int
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField =
                    clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
                result = true

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            } catch (e: Exception) {
                // do nothing
            }
        }
        return result
    }

    fun dp2px(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.applicationContext.resources.displayMetrics
        ).toInt()
    }

    fun sp2px(context: Context, sp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.applicationContext.resources.displayMetrics
        ).toInt()
    }

    fun setFullScreen(activity: Activity) {
        val window = activity.window
        val decor = window.decorView

        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

}