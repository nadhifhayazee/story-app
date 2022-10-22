package com.nadhif.hayazee.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nadhif.hayazee.auth.R
import com.nadhif.hayazee.auth.databinding.FragmentLoginBinding
import com.nadhif.hayazee.baseview.customview.edittext.FormValidator
import com.nadhif.hayazee.baseview.customview.edittext.validator.EmailValidator
import com.nadhif.hayazee.baseview.customview.edittext.validator.PasswordValidator
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.baseview.fragment.navigateDeepLink
import com.nadhif.hayazee.common.DeepLinkRoutes
import com.nadhif.hayazee.common.extension.gone
import com.nadhif.hayazee.common.extension.showErrorSnackBar
import com.nadhif.hayazee.common.extension.visible
import com.nadhif.hayazee.model.common.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var loginVmFactory: LoginViewModel.Factory
    private val loginViewModel by viewModels<LoginViewModel> { loginVmFactory }

    private lateinit var formValidator: FormValidator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAnimation()
        setupView()
        observeVm()

    }

    private fun setupAnimation() {
        val appTitle = ObjectAnimator.ofFloat(
            binding.tvAppTitle,
            View.TRANSLATION_X,
            -30f,
            binding.tvAppTitle.width.toFloat()
        ).apply {
            duration = 500
        }
        val email =
            ObjectAnimator.ofFloat(
                binding.inputLayoutEmail,
                View.TRANSLATION_X,
                -30f,
                binding.inputLayoutEmail.width.toFloat()
            ).apply {
                duration = 500
            }
        val password =
            ObjectAnimator.ofFloat(
                binding.inputLayoutPassword,
                View.TRANSLATION_X,
                -30f,
                binding.inputLayoutPassword.width.toFloat()
            ).apply {
                duration = 500
            }


        val login = ObjectAnimator.ofFloat(
            binding.btnLogin,
            View.TRANSLATION_X,
            -30f,
            binding.btnLogin.width.toFloat()
        ).apply {
            duration = 500
        }
        val signIn =
            ObjectAnimator.ofFloat(binding.tvToRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playTogether(appTitle, email, password, login, signIn)
            start()
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

    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            loginViewModel.loginState.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {
                        binding.progressBar.visible()
                    }
                    is ResponseState.Success -> {
                        binding.progressBar.gone()
                        navigateToHome()
                    }

                    is ResponseState.Error -> {
                        binding.apply {
                            progressBar.gone()
                            root.showErrorSnackBar(it.message)
                        }

                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        findNavController().popBackStack()
        findNavController().navigateDeepLink(DeepLinkRoutes.homePage.toUri())
    }


}