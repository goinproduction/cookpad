package com.paulbaker.library.core.extension

import android.graphics.Bitmap
import android.util.Base64
import java.io.*
import android.graphics.BitmapFactory


class Utils {
    companion object {
        fun encodeImageToString(file: File?): String {
            if (file == null)
                return ""
            val inputStream: InputStream =
                FileInputStream(file) // You can get an inputStream using any I/O API

            val bytes: ByteArray
            val buffer = ByteArray(8192)
            var bytesRead: Int
            val output = ByteArrayOutputStream()

            try {
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            bytes = output.toByteArray()
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        }

        fun decodeBase64ToBitMap(imageString: String?): Bitmap? {
            if (!imageString.isNotNull()) {
                if (!imageString!!.isValidValue()) return null
            }
            val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            options.inSampleSize = calculateInSampleSize(options, 100, 100)
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size, options)
        }

        private fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
        ): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize >= reqHeight
                    && halfWidth / inSampleSize >= reqWidth
                ) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }
    }


}