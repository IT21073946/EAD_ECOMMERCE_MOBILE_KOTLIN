package com.example.ecommerceapp.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserApprovalAdapter(
    private var users: MutableList<User>
) : RecyclerView.Adapter<UserApprovalAdapter.UserViewHolder>() {

    // Function to update the user list
    fun setUsers(userList: List<User>) {
        this.users = userList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_approval, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userEmail: TextView = itemView.findViewById(R.id.userEmail)
        private val userName: TextView = itemView.findViewById(R.id.userName)
        private val approveButton: Button = itemView.findViewById(R.id.approveButton)

        fun bind(user: User) {
            userEmail.text = "Email: ${user.email}"
            userName.text = "Username: ${user.username}"

            // Handle approve button click
            approveButton.setOnClickListener {
                // Call the approval API to approve the user
                approveUser(user)
            }
        }

        // Function to make an API call to approve the user
        private fun approveUser(user: User) {
            val call = ApiClient.userApi.approveUser(user.id ?: "")
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Remove the approved user from the list and update the UI
                        onUserApproved(user)
                    } else {
                        // Handle the case where approval failed
                        // You can show a toast or log the error
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Handle failure to connect to the API
                    // You can show a toast or log the error
                }
            })
        }

        // Function to handle the removal of the user from the list
        private fun onUserApproved(user: User) {
            // Remove the user from the list after approval
            users.remove(user)
            notifyDataSetChanged()
        }
    }
}
