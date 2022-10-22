package com.nadhif.hayazee.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nadhif.hayazee.auth.databinding.FragmentRegisterBinding
import com.nadhif.hayazee.baseview.customview.edittext.FormValidator
import com.nadhif.hayazee.baseview.customview.edittext.validator.EmailValidator
import com.nadhif.hayazee.baseview.customview.edittext.validator.MustFilledValidator
import com.nadhif.hayazee.baseview.customview.edittext.validator.PasswordValidator
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.common.extension.gone
import com.nadhif.hayazee.common.extension.showErrorSnackBar
import com.nadhif.hayazee.common.extension.visible
import com.nadhif.hayazee.model.common.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    @Inject
    lateinit var registerVmFactory: RegisterViewModel.Factory
    private val registerViewModel by viewModels<RegisterViewModel> { registerVmFactory }

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

        val name =
            ObjectAnimator.ofFloat(
                binding.inputLayoutName,
                View.TRANSLATION_X,
                -30f,
                binding.inputLayoutName.width.toFloat()
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


        val register = ObjectAnimator.ofFloat(
            binding.btnRegister,
            View.TRANSLATION_X,
            -30f,
            binding.btnRegister.width.toFloat()
        ).apply {
            duration = 500
        }
        val login =
            ObjectAnimator.ofFloat(binding.tvToLogin, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playTogether(appTitle, email, password, register, login)
            start()
        }

    }

    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            registerViewModel.registerState.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {
                        binding.progressBar.visible()
                    }
                    is ResponseState.Success -> {
                        binding.apply {
                            progressBar.gone()
                        }
                        Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()

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

    private fun setupView() {
        binding.apply {
            tvToLogin.setOnClickListener {
                findNavController().popBackStack()
            }

            btnRegister.setOnClickListener {
                registerViewModel.register(
                    inputLayoutName.editText?.text.toString(),
                    inputLayoutEmail.editText?.text.toString(),
                    inputLayoutPassword.editText?.text.toString()
                )
            }

            setupForm()

        }
    }

    private fun setupForm() {
        binding.apply {
            val formValidator =
                FormValidator(listOf(inputLayoutName, inputLayoutEmail, inputLayoutPassword))
            inputLayoutName.setValidator(MustFilledValidator())
            inputLayoutEmail.setValidator(EmailValidator())
            inputLayoutPassword.setValidator(PasswordValidator())
            formValidator.isFormValidated.observe(viewLifecycleOwner) {
                btnRegister.isEnabled = it
            }
        }
    }


}