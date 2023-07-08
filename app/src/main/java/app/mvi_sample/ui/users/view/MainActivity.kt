package app.mvi_sample.ui.users.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.mvi_sample.R
import app.mvi_sample.data.api.ApiHelperImpl
import app.mvi_sample.data.api.RetrofitBuilder
import app.mvi_sample.data.model.User
import app.mvi_sample.databinding.ActivityMainBinding
import app.mvi_sample.ui.users.adapter.UserAdapter
import app.mvi_sample.ui.users.intent.UserIntent
import app.mvi_sample.ui.users.viewmodel.UsersViewModel
import app.mvi_sample.ui.users.viewstate.UsersState
import app.mvi_sample.util.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var usersViewModel: UsersViewModel
    private var adapter = UserAdapter(arrayListOf())
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        binding.buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                usersViewModel.userIntent.send(UserIntent.FetchUsers)
            }
        }
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerView.context,
                    (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        usersViewModel = ViewModelProviders.of(
            this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService))
        )[UsersViewModel::class.java]
    }


    private fun observeViewModel() {
        lifecycleScope.launch {
            usersViewModel.state.collect {
                when (it) {
                    is UsersState.Idle -> {

                    }

                    is UsersState.Loading -> {
                        binding.buttonFetchUser.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is UsersState.Users -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchUser.visibility = View.GONE
                        renderList(it.users)
                    }

                    is UsersState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        binding.recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers ->
            listOfUsers.let {
                adapter.addData(it)
            }
        }
        adapter.notifyDataSetChanged()
    }


}