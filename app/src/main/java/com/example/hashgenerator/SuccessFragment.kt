package com.example.hashgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hashgenerator.databinding.FragmentSuccessBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class SuccessFragment : Fragment() {

    private val args: SuccessFragmentArgs by navArgs()

    private var _binding: FragmentSuccessBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSuccessBinding.inflate(inflater , container , false)
        binding.hashTextView.text = args.hash

        binding.copyButton.setOnClickListener {
            onCopyClicked()
        }

        return binding.root
    }

    private fun onCopyClicked() {
   lifecycleScope.launch {
       copyToClipboard(args.hash)
       showSnackBar("COPIED !")
   }
    }

    private fun copyToClipboard(hash: String) {
        val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipdata = ClipData.newPlainText("Encrypted Text" , hash)
        clipboardManager.setPrimaryClip(clipdata)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun showSnackBar(message : String){
        val snack: Snackbar = Snackbar.make(binding.successRootLayout, message, Snackbar.LENGTH_LONG)
        val view = snack.view
        snack.setBackgroundTint(ContextCompat.getColor(requireContext() , R.color.blue))
        snack.setActionTextColor(ContextCompat.getColor(requireContext() , R.color.white))
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()
    }

}