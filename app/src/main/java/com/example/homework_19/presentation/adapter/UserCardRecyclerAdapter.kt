package com.example.homework_19.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_19.R
import com.example.homework_19.databinding.UserCardBinding
import com.example.homework_19.presentation.model.User

class UserCardRecyclerAdapter :
    ListAdapter<User, UserCardRecyclerAdapter.UserCardViewHolder>(UserDiffUtil) {

    companion object {
        val UserDiffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    lateinit var onClick: (Int) -> Unit
    lateinit var onRemoveClick: (List<User>) -> Unit
    private val selectedItems = mutableListOf<User>()

    inner class UserCardViewHolder(private val binding: UserCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding) {
                val user = currentList[adapterPosition]
                tvEmail.text = user.email
                tvFirstName.text = user.firstName
                tvLastName.text = user.lastName
                Glide
                    .with(binding.root)
                    .load(user.avatar)
                    .into(ivUserImage)

                root.setOnLongClickListener {
                    selectItems()
                    return@setOnLongClickListener true
                }
            }
        }

        private fun selectItems() {
            val user = currentList[adapterPosition]

            with(binding) {
                if (selectedItems.contains(user)) {
                    selectedItems.remove(user)
                    onRemoveClick(selectedItems)
                    root.setBackgroundColor(Color.TRANSPARENT)
                } else {
                    selectedItems.add(user)
                    onRemoveClick(selectedItems)
                    root.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.gray
                        )
                    )
                }
            }
        }

        fun goToUser() {
            with(binding) {
                val user = currentList[adapterPosition]
                root.setOnClickListener {
                    if (!selectedItems.contains(user)) {
                        onClick(user.id)
                    } else {
                        selectedItems.remove(user)
                        onRemoveClick(selectedItems)
                        root.setBackgroundColor(Color.TRANSPARENT)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCardViewHolder {
        return UserCardViewHolder(
            UserCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserCardViewHolder, position: Int) {
        with(holder) {
            bind()
            goToUser()
        }
    }
}