package com.coderzz.cooking

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.coderzz.cooking.database.SaveUser
import com.coderzz.cooking.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import java.net.PasswordAuthentication


class SignUpFragment : Fragment() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInFragment = SignFragment()
        view.back.setOnClickListener {
            currentFragment(signInFragment)
        }
        Toast.makeText(activity, "hii", Toast.LENGTH_LONG).show()
        view.SignUp.setOnClickListener {
            if (TextUtils.isEmpty(view.name.text.toString()) or TextUtils.isEmpty(view.email_address.text.toString()) or TextUtils.isEmpty(view.phone.text.toString()) or TextUtils.isEmpty(view.password.text.toString()) or TextUtils.isEmpty(view.confirm_password.text.toString())){
                view.warning.visibility = View.VISIBLE
            }else if (view.password.length() < 6){
                view.password.error = "Password must be 6 or greater then 6 letter"
                view.warning.text = "Password must be 6 or greater then 6 letter"
                view.warning.visibility = View.VISIBLE
            }else if(view.password.text.toString() != view.confirm_password.text.toString()){
                view.confirm_password.error = "Password is not matched"
                view.warning.text = "Password is not matched"
                view.warning.visibility = View.VISIBLE
            }else{
                signUpemail(view.name.text.toString(), view.email_address.text.toString(), view.phone.text.toString(), view.password.text.toString())
            }
        }

    }

    private  fun  signUpemail(name: String, email : String, phone: String, password : String){
        activity?.let {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task->
                    if (task.isSuccessful){
                        val user = mAuth.currentUser
                        val userDetails = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                        user!!.updateProfile(userDetails)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful){
                                        Log.d("check", "sucesss")
                                    }
                                }
                        Log.d("hii", "1")
                        val saveUser =  User(name, phone, email)
                        SaveUser().addUser(saveUser)
                        Log.d("hii", "2")
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                        Log.d("hii", "3")
                        activity!!.finish()

                    }else{
                        Toast.makeText(it, "Account creation failed\n${task.exception?.message.toString()}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    fun currentFragment(fragment: Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment, fragment)
        transaction?.commit()
    }
}