package ru.skillbranch.devintensive.extensions

import android.util.TypedValue
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.devintensive.R

fun Snackbar.applyStyle():Snackbar{
    val theme = this.context.theme

    val colorAccent = TypedValue()
    val backgroundColor = TypedValue()
    val textColor = TypedValue()

    theme.resolveAttribute(R.attr.colorAccent, colorAccent, true)
    theme.resolveAttribute(R.attr.colorItemTextTitle, backgroundColor, true)
    theme.resolveAttribute(R.attr.colorItemBackground, textColor, true)

    this.setActionTextColor(colorAccent.data)
    this.view.setBackgroundColor(backgroundColor.data)
    this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(textColor.data)
    return this
}