package com.paulbaker.cookpad.core.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class Utils {
    companion object{
        fun dpToPx(dp:Int):Int{
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }
        fun pxToDp(px:Int):Int{
            return (px / Resources.getSystem().displayMetrics.density).toInt()
        }
        fun showKeyboard(ctx: Context, editText: EditText?) {
            val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
        }

        fun hideKeyboard(context: Context, view: View) {
            val inputMethodManager =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun getDeviceHeight(context: Context): Int {
            val resources: Resources = context.resources
            val metrics: DisplayMetrics = resources.displayMetrics
            return metrics.heightPixels
        }

        fun getDeviceWidth(context: Context): Int {
            val resources: Resources = context.resources
            val metrics: DisplayMetrics = resources.displayMetrics
            return metrics.widthPixels
        }
    }
}