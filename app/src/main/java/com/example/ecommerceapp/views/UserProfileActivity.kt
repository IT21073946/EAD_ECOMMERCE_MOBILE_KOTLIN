package com.example.ecommerceapp.views

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.controllers.UserController
import com.example.ecommerceapp.models.User
import com.google.android.material.textfield.TextInputEditText
import java.io.ByteArrayOutputStream

class UserProfileActivity : AppCompatActivity() {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var contactNumberInput: TextInputEditText
    private lateinit var profileImageView: ImageView
    private lateinit var continueButton: Button
    private lateinit var deactivateButton: Button
    private lateinit var userController: UserController
    private var profilePictureBase64: String? = null
    private lateinit var currentUser: User

    private val pickImage = 100
    private lateinit var loggedInEmail: String // This will hold the logged-in user's email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        userController = UserController(this)

        usernameInput = findViewById(R.id.usernameInput)
        emailInput = findViewById(R.id.emailInput)
        contactNumberInput = findViewById(R.id.contactNumberInput)
        profileImageView = findViewById(R.id.profileImageView)
        continueButton = findViewById(R.id.continueButton)
        deactivateButton = findViewById(R.id.deactivateButton)

        // Retrieve the email of the logged-in user from SharedPreferences
        loggedInEmail = getLoggedInUserEmail()

        // Load user profile using email
        loadUserProfile(loggedInEmail)

        // Handle profile picture upload
        profileImageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        // Save changes
        continueButton.setOnClickListener {
            updateUserProfile()
        }

        // Handle deactivate button click
        deactivateButton.setOnClickListener {
            deactivateUserAccount()
        }

        val backIcon = findViewById<ImageView>(R.id.backIcon)
        backIcon.setOnClickListener {
            onBackPressed()
        }
    }

    // Load user profile by email
    private fun loadUserProfile(email: String) {
        userController.getUserByEmail(email) { user, message ->
            if (user != null) {
                currentUser = user
                populateUserFields(user)
            } else {
                Toast.makeText(this, message ?: "Failed to load user profile", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Populate UI fields with user data (showing UserId in the username field)
    private fun populateUserFields(user: User) {
        val displayName = "${user.username} (ID: ${user.id})" // Append UserId to username
        usernameInput.setText(displayName)
        emailInput.setText(user.email)
        contactNumberInput.setText(user.contactNumber)

        if (!user.profilePictureBase64.isNullOrEmpty()) {
            val bitmap = base64ToBitmap(user.profilePictureBase64)
            profileImageView.setImageBitmap(bitmap)
        } else {
            // Set a default image if the user doesn't have a profile picture
            profileImageView.setImageResource(R.drawable.ic_user)  // Replace with your default image drawable
        }
    }

    // Update user profile
    private fun updateUserProfile() {
        val username = usernameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val contactNumber = contactNumberInput.text.toString().trim()

        if (username.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Update the current user's details
        currentUser = currentUser.copy(username = username, email = email, contactNumber = contactNumber, profilePictureBase64 = profilePictureBase64)

        userController.updateUserProfile(currentUser) { success, message ->
            if (success) {
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, message ?: "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Deactivate user account
    private fun deactivateUserAccount() {
        currentUser.email?.let { email ->
            userController.deactivateUser(email) { success, message ->
                if (success) {
                    Toast.makeText(this, "Account deactivated", Toast.LENGTH_SHORT).show()

                    // Clear user session (if using SharedPreferences)
                    clearUserSession()

                    // Navigate to the login screen
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear the back stack
                    startActivity(intent)
                    finish() // Close the current activity
                } else {
                    Toast.makeText(this, message ?: "Failed to deactivate account", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Clear user session (Optional)
    private fun clearUserSession() {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("logged_in_email") // Clear the stored email or any other login data
        editor.apply()
    }


    // Get the logged-in user's email from SharedPreferences
    private fun getLoggedInUserEmail(): String {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("logged_in_email", "") ?: ""
    }

    // Handle image picking from the gallery and converting it to base64
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            profileImageView.setImageBitmap(bitmap)
            profilePictureBase64 = bitmapToBase64(bitmap)
        }
    }

    // Convert bitmap to Base64 string
    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun base64ToBitmap(base64String: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

}

