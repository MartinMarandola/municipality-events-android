package com.ar.municipalityevents.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ar.municipalityevents.R
import com.ar.municipalityevents.config.MunicipalityEventsApplication
import com.ar.municipalityevents.databinding.FragmentSignupBinding
import com.ar.municipalityevents.service.register.SignUpService
import com.ar.municipalityevents.translator.SignUpTranslator
import java.time.LocalDate

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignUpFragment : Fragment(){

    private lateinit var service: SignUpService
    private var _binding: FragmentSignupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        service = SignUpService()
        service.attachView(this, activity as Context)
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.date.setOnClickListener {showDatePickerDialog()}

        binding.buttonSignup.setOnClickListener {
           this.signUp()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(parentFragmentManager, "datePicker")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDateSelected(day: Int, month: Int, year: Int){
        return binding.date.setText(LocalDate.of(year, month, day).toString())
    }

     private fun signUp() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val name = binding.name.text.toString().trim()
        val surname = binding.surname.text.toString().trim()
        val date = binding.date.text.toString()
        val country = binding.country.text.toString().trim()

        if(service.checkEmail(binding.email)) {
            binding.email.error = "Campo requerido"
        }
        if(service.checkString(name)){
            binding.name.error = "Campo requerido"
        }
        if(service.checkString(surname)) {
            binding.surname.error = "Campo requerido"
        }
        if(service.checkString(date)){
            binding.date.error = "Campo requerido"
        }
        if(service.checkString(country)){
            binding.country.error = "Campo requerido"
        }
        if(service.checkString(password)){
            binding.password.error = "Campo requerido"
        }else if (service.checkLength(binding.password)){
            binding.password.error = "La contraseña debe tener entre 6 y 20 caracteres"
        }

        service.signUp(SignUpTranslator.toDto(email, password, name, surname, country, date))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateToLogin() {
        findNavController().navigate(R.id.action_SignUpFragment_to_LoginFragment)
    }

    fun showMessage(msg: String) {
        Toast.makeText(activity as Context,msg,Toast.LENGTH_SHORT).show()
    }

    fun saveToken(token: String) {
        MunicipalityEventsApplication.prefs.saveToken(token)
    }
}