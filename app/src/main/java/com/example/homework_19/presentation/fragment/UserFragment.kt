package com.example.homework_19.presentation.fragment

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.homework_19.BaseFragment
import com.example.homework_19.data.common.Resource
import com.example.homework_19.databinding.FragmentUserBinding
import com.example.homework_19.presentation.event.UserEvent
import com.example.homework_19.presentation.model.User
import com.example.homework_19.presentation.view_model.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()
    private val args: UserFragmentArgs by navArgs()

    override fun observe() {
        getUserData()
        observeUser()
    }


    private fun getUserData() {
        viewModel.onEvent(UserEvent.GetUser(args.id))
    }

    private fun bindUserInfo(user: User) {
        with(binding) {
            tvEmail.text = user.email
            tvFirstName.text = user.firstName
            tvLastName.text = user.lastName
            Glide
                .with(requireContext())
                .load(user.avatar)
                .into(ivUserImage)
        }
    }

    private fun observeUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userStateFlow.collect {
                    when (it) {
                        is Resource.Success -> {
                            with(binding) {
                                tvEmailTitle.visibility = View.VISIBLE
                                tvFirstNameTitle.visibility = View.VISIBLE
                                tvLastNameTitle.visibility = View.VISIBLE
                            }
                            bindUserInfo(it.success)
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
}