package com.example.canvastest.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.canvastest.R
import com.example.canvastest.databinding.DialogFragmentSaveLabBinding

class SaveLabDialogFragment:DialogFragment() {

    //private val args: DepartureOptionsDialogArgs by navArgs()
    //private val viewModel: DepartureOptionsDialogFragmentViewModel by viewModels {
    //    InjectorUtils.provideDepartureOptionsDialogFragmentFactory(requireContext())
    //}
    lateinit var binding:DialogFragmentSaveLabBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<DialogFragmentSaveLabBinding>(inflater,
            R.layout.dialog_fragment_save_lab, container, false)

        return binding.root//inflater.inflate(R.layout.departure_dialog_fragment, container, false)
    }
}
