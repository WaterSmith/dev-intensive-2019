package ru.skillbranch.devintensive.ui.profile

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile_constraint.*
import ru.skillbranch.devintensive.R

class ProfileActivity : AppCompatActivity() {
    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }
    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_constraint)
        initViews(savedInstanceState)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickname" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )
        btn_edit.setOnClickListener {
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)}

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showCurrentMode(isEditable:Boolean) {
        val info = viewFields.filter { setOf("firstName","lastName","about","repository").contains(it.key) }
        for ((_,v) in info) {
            v.isEnabled = isEditable
            v.isFocusableInTouchMode = isEditable
            v.isFocusable = isEditable
            v.background.alpha = if (isEditable) 255 else 0
        }

        wr_about.isCounterEnabled = isEditable
        iv_eye.visibility = if(isEditable) View.GONE else View.VISIBLE

        with(btn_edit){
            val filter: ColorFilter? = if(isEditable) {
                PorterDuffColorFilter(resources.getColor(R.color.color_accent, theme),PorterDuff.Mode.SRC_IN)
            } else {
                null
            }

            val icon = if(isEditable){
                resources.getDrawable(R.drawable.ic_save_black_24dp)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }
}

