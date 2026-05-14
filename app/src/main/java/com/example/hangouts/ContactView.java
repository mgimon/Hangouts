package com.example.hangouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactView extends AppCompatActivity {

    private ActionBar actionBar;
    private DbHelper dbHelper;
    private FloatingActionButton deletefab, editfab;
    private ImageButton sendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DbHelper(this);

        Contact contact = dbHelper.getContactById(getIntent().getIntExtra("contact_id", -1));
        if (contact == null) {
            finish();
            return;
        }

        // init action bar
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.contact);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // fill view with contact data
        TextView contactNameTv = findViewById(R.id.contactName);
        TextView contactPhoneTv = findViewById(R.id.contactPhone);
        TextView contactEmailTv = findViewById(R.id.contactEmail);
        TextView contactCompanyTv = findViewById(R.id.contactCompany);

        contactNameTv.setText(contact.getName());
        contactPhoneTv.setText(contact.getPhone());
        contactEmailTv.setText(contact.getEmail());
        contactCompanyTv.setText(contact.getCompany());

        // delete fab
        deletefab = findViewById(R.id.deletefab);
        deletefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popup
                new androidx.appcompat.app.AlertDialog.Builder(ContactView.this)
                        .setTitle(getString(R.string.delete))
                        .setMessage(getString(R.string.sure_delete))
                        .setPositiveButton(getString(R.string.delete), (dialog, which) -> {

                            // delete
                            dbHelper.deleteContact(contact.getId());
                            finish();

                        })
                        .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }
        });

        // edit fab - Fragment Dialog (No Activity)
        editfab = findViewById(R.id.editfab);
        editfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // popup config
                LinearLayout layout = new LinearLayout(ContactView.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                EditText nameInput = new EditText(ContactView.this);
                nameInput.setHint(R.string.name);
                nameInput.setText(contact.getName());

                EditText companyInput = new EditText(ContactView.this);
                companyInput.setHint(R.string.company);
                companyInput.setText(contact.getCompany());

                EditText phoneInput = new EditText(ContactView.this);
                phoneInput.setHint(R.string.phone_number);
                phoneInput.setText(contact.getPhone());

                EditText emailInput = new EditText(ContactView.this);
                emailInput.setHint(R.string.email);
                emailInput.setText(contact.getEmail());

                EditText noteInput = new EditText(ContactView.this);
                noteInput.setHint(R.string.note);
                noteInput.setText(contact.getNote());

                layout.addView(nameInput);
                layout.addView(companyInput);
                layout.addView(phoneInput);
                layout.addView(emailInput);
                layout.addView(noteInput);

                // popup
                new androidx.appcompat.app.AlertDialog.Builder(ContactView.this)
                        .setTitle(getString(R.string.edit))
                        .setView(layout)
                        .setPositiveButton(getString(R.string.save), (dialog, which) -> {

                            dbHelper.updateContact(
                                    contact.getId(),
                                    nameInput.getText().toString(),
                                    companyInput.getText().toString(),
                                    phoneInput.getText().toString(),
                                    emailInput.getText().toString(),
                                    noteInput.getText().toString(),
                                    String.valueOf(System.currentTimeMillis())
                            );

                            finish();

                        })
                        .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });

        // handle back button of device
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish(); // default behavior: close Activity
            }
        });
    }

    // handle back button in action bar
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close Activity
        return true;
    }
}