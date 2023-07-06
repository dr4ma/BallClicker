package com.bclccasino.ballclicker.presentation.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.bclccasino.ballclicker.R
import com.google.android.material.snackbar.Snackbar
import java.util.UUID

fun generateUUID() : String{
    return UUID.randomUUID().toString()
}

fun showToast(context: Context?, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun hideKeyBoard(context : FragmentActivity?) {
    val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(context.window.decorView.windowToken, 0)
}

fun showSnackBar(context : Context?, view: View, title: String, color: Int) {
    val snack = context?.getColor(color)?.let {
        Snackbar.make(view, title, Snackbar.LENGTH_SHORT)
        .setTextColor(Color.WHITE)
        .setBackgroundTint(it)
        .setActionTextColor(context.getColor(R.color.white))
    }
    snack?.setAction(context.getString(R.string.cancel)) {
        snack.dismiss()
    }
    snack?.show()

}