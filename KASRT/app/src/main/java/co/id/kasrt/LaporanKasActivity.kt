package co.id.kasrt

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LaporanKasActivity : AppCompatActivity() {

    private lateinit var tvNama: TextView
    private lateinit var tvJumlah: TextView
    private lateinit var tvMetodeTransfer: TextView
    private lateinit var tvDetailTransfer: TextView
    private lateinit var tvKeterangan: TextView
    private lateinit var ivBuktiTransfer: ImageView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_kas)

        tvNama = findViewById(R.id.tvNama)
        tvJumlah = findViewById(R.id.tvJumlah)
        tvMetodeTransfer = findViewById(R.id.tvMetodeTransfer)
        tvDetailTransfer = findViewById(R.id.tvDetailTransfer)
        tvKeterangan = findViewById(R.id.tvKeterangan)
        ivBuktiTransfer = findViewById(R.id.ivBuktiTransfer)

        database = FirebaseDatabase.getInstance().reference.child("kas")
        fetchKasData()
    }

    private fun fetchKasData() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (kasSnapshot in snapshot.children) {
                    val kas = kasSnapshot.getValue(Kas::class.java)
                    if (kas != null) {
                        tvNama.text = "Nama: ${kas.nama}"
                        tvJumlah.text = "Jumlah: ${kas.jumlah}"
                        tvMetodeTransfer.text = "Metode Transfer: ${kas.metodeTransfer}"
                        tvDetailTransfer.text = "Detail Transfer: ${kas.metodeTransfer}"
                        tvKeterangan.text = "Keterangan: ${kas.keterangan}"

                        if (kas.buktiTransferUrl.isNotEmpty()) {
                            Glide.with(this@LaporanKasActivity)
                                .load(kas.buktiTransferUrl)
                                .into(ivBuktiTransfer)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
