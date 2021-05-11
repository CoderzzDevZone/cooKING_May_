package com.coderzz.cooking

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign.view.*
import kotlin.math.sign

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SignFragment : Fragment() {


    val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpFragment = SignUpFragment()
        view.signUpIntent.setOnClickListener {
            currentFragment(signUpFragment)
        }

        view.signIn_button.setOnClickListener {
            if (TextUtils.isEmpty(view.email_address.text.toString()) or TextUtils.isEmpty(view.password.text.toString())){
                view.textView2.visibility = View.VISIBLE
            }else{
                signInwithEmail(view.email_address.text.toString(), view.password.text.toString(), view)
            }
        }

    }
    fun currentFragment(fragment: Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment, fragment)
        transaction?.commit()
    }

    private fun signInwithEmail(email : String , password : String, view: View){
        activity?.let {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) {task ->
                    if (task.isSuccessful){
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                        activity!!.finish()
                    }else{
                        view.textView2.visibility = View.VISIBLE
                        Toast.makeText(activity, task.exception?.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}