package numsim.justintimetech.com.numsim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        String port = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                if (i==0) {
                    //---get the sender address/phone number---
                    str += msgs[i].getOriginatingAddress();
                    str += " :  ";
                }
                //---get the message body---
                str += msgs[i].getMessageBody().toString();
            }

            port = str.substring(str.length()-4);
            if(port.equalsIgnoreCase("1234")){
                str = str.substring(0,str.length()-4);

                //---display the new SMS message---
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                Log.d("SMSReceiver", str);
                //---send a broadcast intent to update the SMS received in the activity---
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("SMS_RECEIVED_ACTION");
                broadcastIntent.putExtra("sms", str);
                context.sendBroadcast(broadcastIntent);
            }
        }
    }
}
