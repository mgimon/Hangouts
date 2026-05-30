package com.example.hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // fill map with array of binary objects
        // "pdus" -> Object[]
        Bundle bundle = intent.getExtras();
        if (bundle == null) return;

        Object[] pdus = (Object[]) bundle.get("pdus");
        if (pdus == null) return;

        DbHelper db = new DbHelper(context);

        for (Object pdu : pdus) {

            // get sms info from pdu
            SmsMessage sms =
                    SmsMessage.createFromPdu((byte[]) pdu);

            String phone = sms.getOriginatingAddress();
            String body = sms.getMessageBody();
            long date = sms.getTimestampMillis();

            Contact contact = db.getContactByPhone(phone);

            // insert in db
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

        // notify app
        Intent update = new Intent("NEW_SMS");
        context.sendBroadcast(update);
    }
}