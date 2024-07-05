package co.id.kasrt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MessageActivity : AppCompatActivity() {

    private lateinit var messageListView: ListView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        messageListView = findViewById(R.id.message_list_view)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_button)

        // Initialize adapter with the current messages list
        adapter = MessageAdapter(this, messages)
        messageListView.adapter = adapter

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString()
            if (message.isNotEmpty()) {
                messages.add(message)
                adapter.notifyDataSetChanged()
                messageEditText.text.clear()
            } else {
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
