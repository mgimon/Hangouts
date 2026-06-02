package com.example.hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // fill map with array of binary objects
        // "pdus" -> Object[]
        Bundle bundle = intent.getExtras();
        if (bundle == null) return;

        Object[] pdus = (Object[]) bundle.get("pdus");
        if (pdus == null) return;

        SmsReceiveModel model = new SmsReceiveModel(context);

        for (Object pdu : pdus) {

            // get sms info from pdu
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);

            String phone = sms.getOriginatingAddress();
            String body = sms.getMessageBody();
            long date = sms.getTimestampMillis();

            model.handleSms(phone, body, date);

        }

        Log.d("SMS", "RECEIVER EXECUTED");

        // notify app
        Intent update = new Intent("NEW_SMS");
        context.sendBroadcast(update);
    }
}