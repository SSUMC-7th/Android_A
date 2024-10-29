package com.example.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.memo.databinding.DialogRewriteBinding

class RewriteDialog(
    private val onYesClicked: () -> Unit,
    private val onNoClicked: () -> Unit,
): DialogFragment() {
    private lateinit var binding: DialogRewriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogRewriteBinding.inflate(layoutInflater)
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonYes.setOnClickListener {
            onYesClicked()
            dismiss()
        }
        binding.buttonNo.setOnClickListener {
            onNoClicked()
            dismiss()
        }
    }
}