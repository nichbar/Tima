package work.nich.tima.common.util

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

/**
 * Created by mikepenz on 14.03.15.
 * This class implements a hack to change the layout padding on bottom if the keyboard is shown
 * to allow long lists with editTextViews
 * Basic idea for this solution found here: http://stackoverflow.com/a/9108219/325479
 */
class KeyboardUtils(act: Activity, var contentView: View) {

    private var mDecorView: View = act.window.decorView
    private var mOnGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener

    init {
        mOnGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            val r = Rect()
            //r will be populated with the coordinates of your view that area still visible.
            mDecorView.getWindowVisibleDisplayFrame(r)

            //get screen height and calculate the difference with the usable area from the r
            val height = mDecorView.context.resources.displayMetrics.heightPixels
            val diff = height - r.bottom

            //if it could be a keyboard add the padding to the view
            if (diff != 0) {
                // if the use-able screen height differs from the total screen height we assume that it shows a keyboard now
                //check if the padding is 0 (if yes set the padding for the keyboard)
                if (contentView.paddingBottom != diff) {
                    //set the padding of the contentView for the keyboard
                    contentView.setPadding(0, 0, 0, diff)
                }
            } else {
                //check if the padding is != 0 (if yes reset the padding)
                if (contentView.paddingBottom != 0) {
                    //reset the padding of the contentView
                    contentView.setPadding(0, 0, 0, 0)
                }
            }
        }

        //only required on newer android versions. it was working on API level 19
        if (Build.VERSION.SDK_INT >= 19) {
            mDecorView.viewTreeObserver.addOnGlobalLayoutListener(mOnGlobalLayoutListener)
        }
    }

    fun enable() {
        if (Build.VERSION.SDK_INT >= 19) {
            mDecorView.viewTreeObserver.addOnGlobalLayoutListener(mOnGlobalLayoutListener)
        }
    }

    fun disable() {
        if (Build.VERSION.SDK_INT >= 19) {
            mDecorView.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
        }
    }

    /**
     * Helper to hide the keyboard
     *
     * @param act
     */
    fun hideKeyboard(act: Activity?) {
        if (act != null && act.currentFocus != null) {
            val inputMethodManager = act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(act.currentFocus?.windowToken, 0)
        }
    }
}