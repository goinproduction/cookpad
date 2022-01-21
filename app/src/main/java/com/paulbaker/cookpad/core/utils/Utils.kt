package com.paulbaker.cookpad.core.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.paulbaker.cookpad.data.datasource.remote.Data
import com.paulbaker.cookpad.data.datasource.local.FoodHomeModel
import com.paulbaker.cookpad.feature.home.adapter.FoodHomeAdapter

class Utils {
    companion object {
        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }

        fun pxToDp(px: Int): Int {
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

        fun groupFoodByCategory(data: MutableList<Data>?): MutableList<FoodHomeModel> {
            val listFood: MutableList<FoodHomeModel> = mutableListOf()
            val listFoodGroup = data?.groupBy { it.category }
            listFoodGroup?.entries?.forEachIndexed { index, entry ->
                if (index == 0) {
                    listFood.add(
                        FoodHomeModel(
                            type = FoodHomeAdapter.typeTwoByThree,
                            category = entry.key,
                            item = entry.value
                        )
                    )
                } else {
                    listFood.add(
                        FoodHomeModel(
                            type = FoodHomeAdapter.typeOneByTwo,
                            category = entry.key,
                            item = entry.value
                        )
                    )
                }
            }
            return listFood
        }
    }
}