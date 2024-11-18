package umc.study.umc_7th.ui.viewmodel

import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun signUpWithEmail(email: String, password: String, passwordCheck: String, onResult: (Boolean, String?) -> Unit) {
        if (password == passwordCheck) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                            if (tokenTask.isSuccessful) {
                                val idToken = tokenTask.result?.token
                                onResult(true, idToken)
                            } else {
                                onResult(false, "Failed to get ID Token")
                            }
                        }
                    } else {
                        onResult(false, task.exception?.message)
                    }
                }
        }
        else {
            onResult(false, "Passwords do not match")
        }
    }
}