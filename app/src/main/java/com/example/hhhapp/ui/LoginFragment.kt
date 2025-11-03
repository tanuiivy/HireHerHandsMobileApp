package com.example.hhhapp.ui

import android.animation.LayoutTransition
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.hhhapp.R
import com.example.hhhapp.database.HireHerHandsDatabase
import com.example.hhhapp.database.UserDao
import com.example.hhhapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment: Fragment(/*R.layout.fragment_login*/) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //Create room objects - database & dao
    private lateinit var database: HireHerHandsDatabase
    private lateinit var userDao: UserDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup? , savedInstanceState: Bundle?):
    // Initialize binding object
    View{
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize database + dao
        database = HireHerHandsDatabase.getDatabase(requireContext())
        userDao = database.UserDao()


        //Login button implementation
        binding.loginBtn.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //login using coroutines
            viewLifecycleOwner.lifecycleScope.launch { // Launch a coroutine tied to the fragment's lifecycle
                val user = withContext(Dispatchers.IO) { // Switch the coroutine to a background thread for I/O operations
                    userDao.login(email, password) // Performs the query to the db
                }

                if (user != null) {
                    //Save login state
                    saveLoginState(user.userId, user.userRole)

                    Toast.makeText(requireContext(), "Welcome ${user.userName}!", Toast.LENGTH_SHORT).show()

                    //Navigate based on role
                    //navigateToDashboard(user.userRole)
                    clearFields()
                } else {
                    Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Go to signup button implementation
        binding.goToSignupBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun saveLoginState(userId: Int, userRole: String) {
        val sharedPref = requireActivity().getSharedPreferences("HireHerHands", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", true)
            putInt("userId", userId)
            putString("userRole", userRole)
            apply()
        }
    }

    /*private fun navigateToDashboard(role: String) {
        val fragment = when (role.lowercase()) {
            "customer", "client" -> CustomerDashboardFragment()
            "worker" -> WorkerDashboardFragment()
            "admin" -> AdminDashboardFragment()
            else -> {
                Toast.makeText(requireContext(), "Unknown role", Toast.LENGTH_SHORT).show()
                return
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }*/

    private fun clearFields() {
        binding.email.text.clear()
        binding.password.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}