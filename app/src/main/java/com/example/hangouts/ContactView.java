package com.example.hangouts;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactView extends BaseActivity {

    private int contactId;
    private DbHelper dbHelper;
    private FloatingActionButton deletefab, editfab;
    private ImageButton sendButton;
    private ContentObserver smsObserver;

    // BcReceiver to load messages
    private BroadcastReceiver reloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadMessages(contactId);
        }
    };

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onStart() {
        super.onStart();

        getContentResolver().registerContentObserver(android.provider.Telephony.Sms.CONTENT_URI, true, smsObserver);

        IntentFilter newSms = new IntentFilter("NEW_SMS");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(reloadReceiver, newSms, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(reloadReceiver, newSms);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(reloadReceiver);
        getContentResolver().unregisterContentObserver(smsObserver);
    }

    private void loadMessages(int contactId) {
        LinearLayout board = findViewById(R.id.messageBoard);
        board.removeAllViews();

        long timeMillis;
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Message msg : dbHelper.getAllMessagesByContactId(contactId)) {

            View bubble;

            if (msg.getIsSent() == 1) {
                bubble = inflater.inflate(
                        R.layout.message_item_sent,
                        board,
                        false
                );
            } else {
                bubble = inflater.inflate(
                        R.layout.message_item_received,
                        board,
                        false
                );
            }

            TextView text = bubble.findViewById(R.id.messageText);
            TextView time = bubble.findViewById(R.id.messageTime);

            // Tv set text
            text.setText(msg.getMsg());
            // Tv set time
            timeMillis = Long.parseLong(msg.getTimestamp());

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
            String formattedTime = sdf.format(new java.util.Date(timeMillis));
            time.setText(formattedTime);

            // width and height wrap text
            LinearLayout.LayoutParams msgPosition = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // position
            if (msg.getIsSent() == 1) {
                msgPosition.gravity = android.view.Gravity.END;
            } else {
                msgPosition.gravity = android.view.Gravity.START;
            }

            bubble.setLayoutParams(msgPosition);
            board.addView(bubble);
        }
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

        contactId = getIntent().getIntExtra("contact_id", -1);

        Contact contact = dbHelper.getContactById(getIntent().getIntExtra("contact_id", -1));

        if (contact == null) {
            finish();
            return;
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

                // popup
                new androidx.appcompat.app.AlertDialog.Builder(
                        ContactView.this
                )
                        .setTitle(getString(R.string.delete))
                        .setMessage(getString(R.string.sure_delete))
                        .setPositiveButton(
                                getString(R.string.delete),
                                (dialog, which) -> {
                                    dbHelper.deleteContact(contact.getId());
                                    finish();
                                }
                        )
                        .setNegativeButton(
                                getString(R.string.cancel),
                                (dialog, which) -> dialog.dismiss()
                        )
                        .show();
            }
        });

        // edit fab - Fragment Dialog (No Activity)
        editfab = findViewById(R.id.editfab);

        editfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // popup config
                LinearLayout layout =
                        new LinearLayout(ContactView.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                EditText nameInput =
                        new EditText(ContactView.this);
                nameInput.setHint(R.string.name);
                nameInput.setText(contact.getName());

                EditText companyInput =
                        new EditText(ContactView.this);
                companyInput.setHint(R.string.company);
                companyInput.setText(contact.getCompany());

                EditText phoneInput =
                        new EditText(ContactView.this);
                phoneInput.setHint(R.string.phone_number);
                phoneInput.setText(contact.getPhone());

                EditText emailInput =
                        new EditText(ContactView.this);
                emailInput.setHint(R.string.email);
                emailInput.setText(contact.getEmail());

                EditText noteInput =
                        new EditText(ContactView.this);
                noteInput.setHint(R.string.note);
                noteInput.setText(contact.getNote());

                layout.addView(nameInput);
                layout.addView(companyInput);
                layout.addView(phoneInput);
                layout.addView(emailInput);
                layout.addView(noteInput);

                // popup
                new androidx.appcompat.app.AlertDialog.Builder(
                        ContactView.this
                )
                        .setTitle(getString(R.string.edit))
                        .setView(layout)
                        .setPositiveButton(
                                getString(R.string.save),
                                (dialog, which) -> {
                                    dbHelper.updateContact(
                                            contact.getId(),
                                            nameInput.getText().toString(),
                                            companyInput.getText().toString(),
                                            phoneInput.getText().toString(),
                                            emailInput.getText().toString(),
                                            noteInput.getText().toString(),
                                            String.valueOf(
                                                    System.currentTimeMillis()
                                            )
                                    );

                                    finish();
                                }
                        )
                        .setNegativeButton(
                                getString(R.string.cancel),
                                (dialog, which) -> dialog.dismiss()
                        )
                        .show();
            }
        });

        // load messages
        loadMessages(contact.getId());

        smsObserver = new ContentObserver(new android.os.Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);

                runOnUiThread(() -> {
                    loadMessages(contactId);
                });
            }
        };

        // handle back button of device
        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        finish(); // default behavior: close Activity
                    }
                }
        );
    }
}