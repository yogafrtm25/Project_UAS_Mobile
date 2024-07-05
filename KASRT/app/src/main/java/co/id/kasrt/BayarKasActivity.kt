package co.id.kasrt


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class BayarKasActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etJumlah: EditText
    private lateinit var rgMetodeTransfer: RadioGroup
    private lateinit var ivBuktiTransfer: ImageView
    private lateinit var etKeterangan: EditText
    private lateinit var btnSimpan: Button
    private lateinit var btnUploadBukti: Button
    private lateinit var imageUri: Uri
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bayar_kas)

        etNama = findViewById(R.id.etNama)
        etJumlah = findViewById(R.id.etJumlah)
        rgMetodeTransfer = findViewById(R.id.rgMetodeTransfer)
        ivBuktiTransfer = findViewById(R.id.ivBuktiTransfer)
        etKeterangan = findViewById(R.id.etKeterangan)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnUploadBukti = findViewById(R.id.btnUploadBukti)

        storageReference = FirebaseStorage.getInstance().reference.child("bukti_transfer")

        btnUploadBukti.setOnClickListener {
            selectImage()
        }

        btnSimpan.setOnClickListener {
            if (::imageUri.isInitialized) {
                uploadImageToFirebase()
            } else {
                saveDataToFirebase("")
            }
        }

        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data!!
            ivBuktiTransfer.setImageURI(imageUri)
        }
    }

    private fun uploadImageToFirebase() {
        val fileName = UUID.randomUUID().toString()
        val ref = storageReference.child(fileName)
        ref.putFile(imageUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    saveDataToFirebase(uri.toString())
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Upload Gambar Gagal: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveDataToFirebase(buktiTransferUrl: String) {
        val nama = etNama.text.toString()
        val jumlah = etJumlah.text.toString()
        val metodeTransfer = when (rgMetodeTransfer.checkedRadioButtonId) {
            R.id.rbBank -> "BNI - 1212612 A/N YOGA PRATAMA"
            R.id.rbEWallet -> "DANA - 085771449774 A/N YOGA PRATAMA"
            else -> ""
        }
        val keterangan = etKeterangan.text.toString()

        if (nama.isEmpty() || jumlah.isEmpty() || metodeTransfer.isEmpty()) {
            Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance().reference
        val kasId = database.push().key
        val kas = Kas(nama, jumlah, metodeTransfer, keterangan, buktiTransferUrl)

        if (kasId != null) {
            database.child("kas").child(kasId).setValue(kas).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LaporanKasActivity::class.java))
                } else {
                    Toast.makeText(this, "Data gagal disimpan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

data class Kas(
    val nama: String,
    val jumlah: String,
    val metodeTransfer: String,
    val keterangan: String,
    val buktiTransferUrl: String
)
