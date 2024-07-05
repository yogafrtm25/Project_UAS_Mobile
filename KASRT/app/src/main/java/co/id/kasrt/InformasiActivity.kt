package co.id.kasrt

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException


class InformasiActivity : AppCompatActivity() {
    private var infoLayout: LinearLayout? = null
    private var selectedImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi)
        infoLayout = findViewById(R.id.infoLayout)
    }

    fun onAddInfoClicked(view: View?) {
        // Menampilkan dialog untuk menambah informasi
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tambah Informasi")
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_info, null)
        builder.setView(dialogView)
        val nameEditText = dialogView.findViewById<EditText>(R.id.nameEditText)
        val descriptionEditText = dialogView.findViewById<EditText>(R.id.descriptionEditText)
        val senderEditText = dialogView.findViewById<EditText>(R.id.senderEditText)
        val timeEditText = dialogView.findViewById<EditText>(R.id.timeEditText)
        val selectImageButton = dialogView.findViewById<Button>(R.id.selectImageButton)
        selectImageButton.setOnClickListener { // Membuka galeri untuk memilih gambar
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Pilih Gambar"),
                InformasiActivity.Companion.PICK_IMAGE_REQUEST
            )
        }
        builder.setPositiveButton(
            "Simpan"
        ) { dialog, which ->
            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val sender = senderEditText.text.toString()
            val time = timeEditText.text.toString()

            // Menambah informasi ke layout
            addInfo(name, description, sender, time, selectedImageUri)
        }
        builder.setNegativeButton(
            "Batal"
        ) { dialog, which -> dialog.cancel() }
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == InformasiActivity.Companion.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
        }
    }

    private fun addInfo(
        name: String,
        description: String,
        sender: String,
        time: String,
        imageUri: Uri?
    ) {
        // Membuat layout baru untuk informasi yang ditambahkan
        val newInfoLayout = layoutInflater.inflate(R.layout.info_item, null) as LinearLayout
        val nameTextView = newInfoLayout.findViewById<TextView>(R.id.infoName)
        val descriptionTextView = newInfoLayout.findViewById<TextView>(R.id.infoDescription)
        val senderTextView = newInfoLayout.findViewById<TextView>(R.id.infoSender)
        val timeTextView = newInfoLayout.findViewById<TextView>(R.id.infoTime)
        val infoImageView = newInfoLayout.findViewById<ImageView>(R.id.infoImage)
        nameTextView.text = name
        descriptionTextView.text = description
        senderTextView.text = sender
        timeTextView.text = time

        // Mengatur gambar yang dipilih
        if (imageUri != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    contentResolver, imageUri
                )
                infoImageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        infoLayout!!.addView(newInfoLayout)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
