package com.example.hhhapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hhhapp.R
import com.example.hhhapp.database.HireHerHandsDatabase
import com.example.hhhapp.database.User
import com.example.hhhapp.database.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity: AppCompatActivity() {


    private lateinit var database: HireHerHandsDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize database
        database = HireHerHandsDatabase.getDatabase(this)
        userDao = database.UserDao()

        //Insert hardcoded admin on first run
        insertAdminIfNotExists()

        // Load the LoginFragment first when the app starts
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        }
    }

    private fun insertAdminIfNotExists() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                // Check if admin already exists
                val adminEmail = "admin@hireherands.com"
                val existingAdmin = userDao.checkEmailExists(adminEmail)

                if (existingAdmin == null) {
                    // Create hardcoded admin user
                    val admin = User(
                        userId = 0,
                        userName = "Admin",
                        userRole = "admin",
                        userEmail = adminEmail,
                        userPassword = "admin123"
                    )
                    userDao.insertUser(admin)
                }
            }
        }
    }

}