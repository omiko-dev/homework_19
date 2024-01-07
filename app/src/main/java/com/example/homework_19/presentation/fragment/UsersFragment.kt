package com.example.homework_19.presentation.fragment

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_19.BaseFragment
import com.example.homework_19.data.common.Resource
import com.example.homework_19.databinding.FragmentUsersBinding
import com.example.homework_19.presentation.adapter.UserCardRecyclerAdapter
import com.example.homework_19.presentation.event.UsersEvent
import com.example.homework_19.presentation.model.User
import com.example.homework_19.presentation.view_model.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var adapter: UserCardRecyclerAdapter
    private var userList: MutableList<User> = mutableListOf()
    private var removeUsers: MutableList<User> = mutableListOf()

    override fun observe() {
        usersObserve()
    }

    override fun bind() {
        bindRecycler()
    }

    override fun listener() {
        goToUserPageListener()
        removeUsersListener()
        fillRemovedUserListListener()
    }

    private fun usersObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usersStateFlow.collect {
                    when (it) {
                        is Resource.Success -> {
                            userList = it.success.toMutableList()
                            adapter.submitList(it.success)
                        }
                        is Resource.Error -> {
                            Log.i("omiko", it.error)
                        }
                        is Resource.Loader -> binding.loader.visibility = View.VISIBLE
                        is Resource.Idle -> binding.loader.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun goToUserPageListener() {
        adapter.onClick = {
            findNavController().navigate(
                UsersFragmentDirections.actionUsersFragmentToUserFragment(it)
            )
        }
    }

    private fun fillRemovedUserListListener() {
        adapter.onRemoveClick = {
            removeUsers = it.toMutableList()
            showRemoveClick()
        }
    }

    private fun showRemoveClick(){
        if(removeUsers.isNotEmpty()){
            binding.removeItem.visibility = View.VISIBLE
        }else{
            binding.removeItem.visibility = View.GONE
        }
    }

    private fun removeUsersListener(){
        binding.removeItem.setOnClickListener {
            userList.removeAll(removeUsers)
            adapter.submitList(userList.toList())
            binding.removeItem.visibility = View.GONE
        }
    }

    private fun bindRecycler() {
        adapter = UserCardRecyclerAdapter()
        with(binding) {
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.onEvent(UsersEvent.GetAllUser)
    }
}