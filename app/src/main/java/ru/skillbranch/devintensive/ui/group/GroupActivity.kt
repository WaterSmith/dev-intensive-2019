package ru.skillbranch.devintensive.ui.group

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.ui.adapters.UserAdapter
import ru.skillbranch.devintensive.viewmodels.GroupViewModel

class GroupActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var viewModel: GroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        initToolbar()
        initViews()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(GroupViewModel::class.java)
        viewModel.getUsersData().observe(this, Observer { userAdapter.updateData(it)})
    }

    private fun initViews() {
        userAdapter = UserAdapter { viewModel.handleSelectedItem(it.id) }
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        with(rv_user_list){
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@GroupActivity)
            addItemDecoration(divider)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }
}
