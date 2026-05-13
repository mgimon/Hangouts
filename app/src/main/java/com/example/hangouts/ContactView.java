package com.example.hangouts;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

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

    private String contactName, contactPhone, contactEmail, contactCompany;
    private FloatingActionButton deletefab, editfab;
    private ImageButton sendButton;



    public ContactView() {
        super();
    }

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