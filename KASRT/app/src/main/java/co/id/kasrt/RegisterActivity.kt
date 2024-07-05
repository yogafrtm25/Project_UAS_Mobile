package co.id.kasrt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("users") // Adjust this based on your database structure

        val btnRegister: Button = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.editTextEmail).text.toString()
            val password = findViewById<TextInputEditText>(R.id.editTextPassword).text.toString()
            val name = findViewById<TextInputEditText>(R.id.editTextName).text.toString()
            val nik = findViewById<TextInputEditText>(R.id.editTextNik).text.toString()

            // Check if email and password are not empty
            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || nik.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Process user registration with Firebase Auth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Registration successful.", Toast.LENGTH_SHORT).show()

                        // Save user data to Firebase Realtime Database
                        user?.uid?.let { uid ->
                            val userRef = database.child(uid)
                            userRef.child("email").setValue(email)
                            userRef.child("name").setValue(name)
                            userRef.child("nik").setValue(nik)
                        }

                        // Redirect back to LoginActivity
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    } else {
                        // Registration failed, display an error message
                        Toast.makeText(baseContext, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
