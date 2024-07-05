package co.id.kasrt


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        val editTextNik = findViewById<TextInputEditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<TextInputEditText>(R.id.editTextPassword)
        val btnRegister: Button = findViewById(R.id.btnRegister)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        // Set onClickListener for Register button
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Set onClickListener for Login button
        btnLogin.setOnClickListener {
            val email = editTextNik.text.toString().trim() // Assuming NIK is used for email login
            val password = editTextPassword.text.toString()

            // Validate if email and password are not empty
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Perform Firebase authentication with email and password
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login successful
                        val user = auth.currentUser
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                        // Example: Redirect to MenuActivity after successful login
                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)
                        finish() // Close login activity to prevent returning back
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Authentication failed. Check your email and password.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
