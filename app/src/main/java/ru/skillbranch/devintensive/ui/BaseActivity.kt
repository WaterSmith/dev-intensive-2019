package ru.skillbranch.devintensive.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.R

abstract class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}