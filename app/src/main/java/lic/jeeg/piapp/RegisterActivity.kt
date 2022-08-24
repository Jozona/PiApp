package lic.jeeg.piapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog

import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    private lateinit var button: Button
    private lateinit var imageView: ImageView

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        //Setup
        setup()

        //Pick img
        button = findViewById(R.id.btnBrowseImg)
        imageView = findViewById(R.id.profilePic)

        button.setOnClickListener{
            pickImageGallery()
        }

    }

    //Photo
    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data?.data)

        }
    }



    private fun setup(){
        title ="Sign Up"

        val btnSignIn = findViewById<Button>(R.id.btnRegister)
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val pass = findViewById<EditText>(R.id.editTextTextPassword2)
        btnSignIn.setOnClickListener{
            if(email.text.isNotEmpty() && pass.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(), pass.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType){

        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

}