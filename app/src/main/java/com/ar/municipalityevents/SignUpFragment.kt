package com.ar.municipalityevents

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ar.municipalityevents.databinding.FragmentSignupBinding
import com.ar.municipalityevents.service.register.SignUpContract
import com.ar.municipalityevents.service.register.SignUpService

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignUpFragment : Fragment(), SignUpContract.View {

    lateinit var service: SignUpService
    private var _binding: FragmentSignupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        service = SignUpService()
        service.attachView(this)
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.date.setOnClickListener {showDatePickerDialog()}

        binding.buttonSignup.setOnClickListener {
           this.signUp()
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int){
        if(day < 10 && month+1 < 10){
            binding.date.setText("$year-0${month+1}-0$day")
        }else if (day < 10){
            binding.date.setText("$year-${month+1}-0$day")
        }else if (month+1 < 10){
            binding.date.setText("$year-0${month+1}-$day")
        }else{
            binding.date.setText("$year-${month+1}-$day")
        }

    }

    override fun signUp() {
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

        service.signUp(email, password, name, surname, date, country, activity as Context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun navigateToCalendar() {
        startActivity(Intent(activity as Context, CalendarActivity::class.java))
    }
    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun showMessage(msg: String) {
        Toast.makeText(activity as Context,msg,Toast.LENGTH_SHORT).show()
    }

    override fun saveToken(token: String) {
        MunicipalityEventsApplication.prefs.saveToken(token)
    }
}