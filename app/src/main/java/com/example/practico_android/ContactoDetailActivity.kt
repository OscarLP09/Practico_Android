package com.example.practico_android

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class ContactoDetailActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto_detail)

        nameEditText = findViewById(R.id.edit_name)
        phoneEditText = findViewById(R.id.edit_phone)
        emailEditText = findViewById(R.id.edit_email)
        saveButton = findViewById(R.id.save_button)


        val contactId = intent.getIntExtra("CONTACT_ID", -1)
        val contactName = intent.getStringExtra("CONTACT_NAME")
        val contactPhone = intent.getStringExtra("CONTACT_PHONE")
        val contactEmail = intent.getStringExtra("CONTACT_EMAIL")
        val isEditable = intent.getBooleanExtra("EXTRA_IS_EDITABLE", false)


        nameEditText.setText(contactName)
        phoneEditText.setText(contactPhone)
        emailEditText.setText(contactEmail)


        setFieldsEditable(isEditable)


        saveButton.setOnClickListener {
            if (isEditable) {
                saveContactDetails(contactId)
                finish()
            }
        }

    }

    private fun setFieldsEditable(isEditable: Boolean) {

        nameEditText.isEnabled = isEditable
        phoneEditText.isEnabled = isEditable
        emailEditText.isEnabled = isEditable
        saveButton.visibility = if (isEditable) View.VISIBLE else View.GONE
    }

    private fun saveContactDetails(contactId: Int) {
        val newName = nameEditText.text.toString()
        val newPhone = phoneEditText.text.toString()
        val newEmail = emailEditText.text.toString()

        val contactIndex = ContactoProvider.contactoList.indexOfFirst { it.id == contactId }
        if (contactIndex != -1) {
            val updatedContact = Contacto(
                id = contactId,
                name = newName,
                phone = newPhone,
                email = newEmail,
                image = ContactoProvider.contactoList[contactIndex].image
            )
            ContactoProvider.contactoList[contactIndex] = updatedContact


            setResult(RESULT_OK)
        }
    }



}
