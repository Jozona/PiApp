package lic.jeeg.piapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Setup
        val bundle = intent.extras
        val email =  bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup(email ?: "", provider ?: "")

    }

    private fun setup(email:String, provider: String){
        title = "Home"
        val emailView = findViewById<TextView>(R.id.emailTextView)
        val providerView = findViewById<TextView>(R.id.passTextView)

        emailView.text = email
        providerView.text = provider

        val logOut = findViewById<TextView>(R.id.btnLogOut)
        logOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

}