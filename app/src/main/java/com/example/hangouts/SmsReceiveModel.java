package com.example.hangouts;

import android.content.Context;

public class SmsReceiveModel {

    private final DbHelper db;

    public SmsReceiveModel(Context context) {
        this.db = new DbHelper(context);
    }

    public void handleSms(String phone, String body, long date) {

        Contact contact = db.getContactByPhone(phone);

        if (contact != null) {
            db.insertMessage(
                    contact.getId(),
                    body,
                    0,
                    String.valueOf(date),
                    String.valueOf(date)
            );
        }
    }
}
