package com.map.xyz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class ReciveSMS extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //Log.d("CONSTRAINTS",bundle.toString());
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        Log.d("CONSTRAINTS",msg_from);
                        String msgBody = msgs[i].getMessageBody();
                        Toast.makeText (context, ""+msgBody, Toast.LENGTH_SHORT).show ();
                        Intent i1=new Intent ();
                        i1.setClassName("com.map.xyz", "com.map.xyz.Verifier");
                        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i1.putExtra ("msg",msgBody) ;
                        i1.putExtra ("from",msg_from) ;
                        context.startActivity(i1);
                    }
                }catch(Exception e){
                          Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }
}


