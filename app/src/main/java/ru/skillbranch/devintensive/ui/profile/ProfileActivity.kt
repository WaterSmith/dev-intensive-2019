package ru.skillbranch.devintensive.ui.profile

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile_constraint.*
import kotlinx.android.synthetic.main.activity_profile_constraint.btn_edit
import kotlinx.android.synthetic.main.activity_profile_constraint.btn_switch_theme
import kotlinx.android.synthetic.main.activity_profile_constraint.et_about
import kotlinx.android.synthetic.main.activity_profile_constraint.et_first_name
import kotlinx.android.synthetic.main.activity_profile_constraint.et_last_name
import kotlinx.android.synthetic.main.activity_profile_constraint.et_repository
import kotlinx.android.synthetic.main.activity_profile_constraint.tv_nick_name
import kotlinx.android.synthetic.main.activity_profile_constraint.tv_rank
import kotlinx.android.synthetic.main.activity_profile_constraint.tv_rating
import kotlinx.android.synthetic.main.activity_profile_constraint.tv_respect
import kotlinx.android.synthetic.main.activity_profile_constraint.wr_about
import kotlinx.android.synthetic.main.activity_profile_constraint.wr_repository
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.utils.Utils
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {
    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private lateinit var viewModel: ProfileViewModel

    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_constraint)
        initViews(savedInstanceState)
        initViewModel()
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

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE,false)?:false

        showCurrentMode(isEditMode)

        btn_edit.setOnClickListener {
            if (isEditMode) saveProfileData()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)}

        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }

        with(et_repository) {
            addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onTextChanged(stringValue: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.onAdressRepositoryChanged(stringValue.toString())
                }

            })
        }
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
        viewModel.isRepoError().observe(this, Observer {updateRepoError(it)})
    }

    private fun updateRepoError(isError: Boolean) {
        wr_repository.isErrorEnabled = isError
        wr_repository.error = if(isError) "Невалидный адрес репозитория" else ""
    }

    private fun updateTheme(mode: Int) {
        delegate.setLocalNightMode(mode)
        updateDrawable(viewModel.getProfileData().value)
    }

    private fun updateDrawable(profile: Profile?){

        val initials = Utils.toInitials(profile?.firstName, profile?.lastName)
        val drawable = if (initials==null) {
            resources.getDrawable(R.drawable.ic_avatar, theme)
        } else {
            val color = TypedValue()
            theme.resolveAttribute(R.attr.colorAccent, color, true)
            ColorDrawable(color.data)
        }
        iv_avatar.setImageDrawable(drawable)
        iv_avatar.setText(initials)

    }

    private fun updateUI(profile: Profile) {

        updateDrawable(profile)

        profile.toMap().also {
            for ((k,v) in viewFields){
                v.text = it[k].toString()
            }
        }
    }

    private fun saveProfileData(){
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply { viewModel.saveProfileData(this) }
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
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_EDIT_MODE, isEditMode)
    }
}

