package com.ar.municipalityevents

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ar.municipalityevents.databinding.FragmentLoginBinding
import android.content.Intent
import android.widget.ProgressBar
import com.ar.municipalityevents.service.login.LoginContract
import com.ar.municipalityevents.service.login.LoginService


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment(), LoginContract.View {

    lateinit var service: LoginService
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        service = LoginService()
        service.attachView(this)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            this.signIn()
        }
        this.navigateToRegister()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun signIn() {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()
        if(service.checkEmptyFields(email, password))
            this.showMessage("Uno o ambos campos son vacios")
        else
            service.signInWithEmailAndPassword(email, password, activity as Context)
    }

    override fun navigateToCalendar() {
        startActivity(Intent(activity as Context, CalendarActivity::class.java))
    }

    override fun navigateToRegister() {
        binding.navButtonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
        }
    }

    override fun showMessage(msg: String) {
        Toast.makeText(activity as Context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar() {
        binding.progressBarSignIn.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBarSignIn.visibility = View.GONE
    }
}