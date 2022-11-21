@file:Suppress("DEPRECATION")

package com.solanacode.challange8.fragement

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.solanacode.challange8.R
import com.solanacode.challange8.databinding.FragmentProfileBinding
import com.solanacode.challange8.viewmodel.AuthViewModel
import com.solanacode.challange8.viewmodel.BlurViewModel
import com.solanacode.challange8.worker.IMAGE_BLURRED
import com.solanacode.challange8.worker.OUTPUT_PATH
import com.solanacode.challange8.worker.PROFILE_IMAGE
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*



class ProfileFragment : Fragment() {


    private lateinit var binding : FragmentProfileBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var authViewModel: AuthViewModel
    private val blur by lazy{
        activity?.application?.let { BlurViewModel(it) }
    }
    private var uri : Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        sharedPref = context?.getSharedPreferences("getdataUser", Context.MODE_PRIVATE)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sp = sharedPref.getString("id","not")
        authViewModel.getUser(sp.toString())
        authViewModel.dataUser.observe(requireActivity()){
            if(it != null){
                binding.apply {
                    tvusernameProfile.text = it.username
                    tvnameProfile.text = it.name
                }
            }
        }
        binding.tvLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
        }

        val showUsername = sharedPref.getString("username","not")
        binding.tvusernameProfile.text = showUsername
        updateUser()
        binding.tv.setOnClickListener {
            setLocale("id")
        }
        openGallery()
        setImageBlur()

    }

    private fun updateUser(){
        binding.btnpdateProfile.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.updateProfileFragment)
        }
    }

    @Suppress("SameParameterValue", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res = resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
        Navigation.findNavController(binding.root).navigate(R.id.profileFragment)

    }


    private val useGallery = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it != null){
            uri = it
            Toast.makeText(requireActivity(), "$it", Toast.LENGTH_LONG).show()
            binding.profileImage.setImageURI(it)
            blur?.setImageUri(it)
            if(uri != null){
                saveImage()
            }
        }else{
            Toast.makeText(requireActivity(), "Null Baby", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery(){
        binding.btnchangeProfile.setOnClickListener {
            activity?.intent?.type = "image/*"
            useGallery.launch("image/*")
        }
    }

    private fun setImageBlur(){
        val image = BitmapFactory.decodeFile(requireActivity().applicationContext.filesDir.path + File.separator + OUTPUT_PATH + File.separator + IMAGE_BLURRED)
        binding.profileImage.setImageBitmap(image)
    }

    private fun saveImage(){
        val resolver = requireActivity().applicationContext.contentResolver
        val picture = BitmapFactory.decodeStream(
            resolver.openInputStream(Uri.parse(uri.toString())))
        saveImageProfile(requireContext(), picture)
        blur?.applyBlur()
    }


    private fun saveImageProfile(applicationContext: Context, bitmap: Bitmap): Uri {
        val name = "profile_image.png"
        val outputDir = File(applicationContext.filesDir, PROFILE_IMAGE)
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
        } finally {
            out?.let {
                try {
                    it.close()
                } catch (ignore: IOException) {
                }

            }
        }
        return Uri.fromFile(outputFile)
    }


}