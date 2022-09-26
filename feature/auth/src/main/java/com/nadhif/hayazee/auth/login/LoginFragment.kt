package com.nadhif.hayazee.auth.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nadhif.hayazee.auth.R
import com.nadhif.hayazee.auth.databinding.FragmentLoginBinding
import com.nadhif.hayazee.baseview.customview.edittext.FormValidator
import com.nadhif.hayazee.baseview.customview.edittext.validator.EmailValidator
import com.nadhif.hayazee.baseview.customview.edittext.validator.PasswordValidator
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.datastore.AppDataStore
import com.nadhif.hayazee.model.common.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var loginVmFactory: LoginViewModel.Factory
    private val loginViewModel by viewModels<LoginViewModel> { loginVmFactory }

    @Inject
    lateinit var dataStore: AppDataStore

    private lateinit var formValidator: FormValidator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeVm()

    }

    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            loginViewModel.loginState.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.Success -> {
                        it.data?.loginResult?.let { it1 -> dataStore.saveUser(it1) }
                        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                    }

                    is ResponseState.Error -> {

                    }
                }
            }
        }
    }

    private fun setupView() {
        binding.apply {
            tvToRegister.setOnClickListener {
                findNavController().navigate(R.id.action_login_fragment_to_register_fragment)
            }

            btnLogin.setOnClickListener {
                loginViewModel.login(
                    inputLayoutEmail.editText?.text.toString(),
                    inputLayoutPassword.editText?.text.toString()
                )
            }

            setupForm()

        }
    }

    private fun setupForm() {
        binding.apply {
            formValidator = FormValidator(listOf(inputLayoutEmail, inputLayoutPassword))
            inputLayoutEmail.setValidator(EmailValidator())
            inputLayoutPassword.setValidator(PasswordValidator())
            formValidator.isFormValidated.observe(viewLifecycleOwner) {
                btnLogin.isEnabled = it
            }
        }
    }
}