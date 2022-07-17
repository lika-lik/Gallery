package com.example.gallery.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallery.R
import com.example.gallery.activity.LoginActivity
import com.example.gallery.databinding.FragmentProfileBinding
import com.example.gallery.dialogFragment.DialogExistFragment
import com.example.gallery.dialogFragment.LoginActivityOpener
import com.example.gallery.models.User
import com.example.gallery.storage.SharedPrefManager
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)
        val sharedPrefManager = activity?.application?.let { SharedPrefManager.getInstance(it) }
        var user = sharedPrefManager?.user
        if (user != null){
            init(user)
        }

        binding.buttonExist.setOnClickListener{
            val dialog = DialogExistFragment(object: LoginActivityOpener {
                override fun open() {
                    val context = requireContext()
                    val newIntent = Intent(context, LoginActivity::class.java)
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    context.startActivity(newIntent)
                }

            })
            dialog.show(parentFragmentManager, "dialogExist")
        }

        return binding.root
    }


    private fun init(user: User){
        binding.textViewfFirstName.text = user.firstName
        binding.textViewLastName.text = user.lastName
        binding.textViewAbout.text = "\"${user.about}\""
        binding.textViewCity.text = user.city
        binding.textViewPhone.text = phoneFormat(user.phone)
        binding.textViewEmail.text = user.email
        Picasso.get()
            .load(user.avatar)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.imageViewAvatar)
    }

    private fun phoneFormat(phone: String) = buildString {
        append(phone[0])
        append(phone[1])
        append(" ")
        append("(")
        append(phone[2])
        append(phone[3])
        append(phone[4])
        append(")")
        append(" ")
        append(phone[5])
        append(phone[6])
        append(phone[7])
        append(" ")
        append(phone[8])
        append(phone[9])
        append(" ")
        append(phone[10])
        append(phone[11])
    }
}