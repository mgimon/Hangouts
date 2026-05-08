package com.example.hangouts;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditContact extends AppCompatActivity {

    private EditText nameEt, companyEt, phoneEt, emailEt, noteEt;
    private FloatingActionButton fab;

    // String variables
    private String name, company, phone, email, note;

    // Action bar
    private ActionBar actionBar;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // init db
        dbHelper = new DbHelper(this);

        // init action bar
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.add_contact);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // get data from view
        nameEt = findViewById(R.id.nameEt);
        companyEt = findViewById(R.id.companyEt);
        phoneEt = findViewById(R.id.phoneEt);
        emailEt = findViewById(R.id.emailEt);
        noteEt = findViewById(R.id.noteEt);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> saveData());

        // handle back button of device
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish(); // default behavior: close Activity
            }
        });
    }

    private void saveData() {
        name = nameEt.getText().toString();
        company = companyEt.getText().toString();
        phone = phoneEt.getText().toString();
        email = emailEt.getText().toString();
        note = noteEt.getText().toString();

        String timeStamp = ""+System.currentTimeMillis();

        // info on any 1 field saves contact
        if (!name.isEmpty() || !company.isEmpty() || !phone.isEmpty() || !email.isEmpty() || !note.isEmpty()) {

            long id = dbHelper.insertContact(name, company, phone, email, note, timeStamp, timeStamp);

            Toast.makeText(this, getString(R.string.contact_saved) + ": " + id, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.nothing_to_save), Toast.LENGTH_SHORT).show();
        }
    }

    // handle back button in action bar
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close Activity
        return true;
    }
}