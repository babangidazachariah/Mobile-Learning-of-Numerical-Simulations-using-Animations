package numsim.justintimetech.com.numsim;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;
public class SmsActivity extends Activity {

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED",phoneNumber = "",smsBody = "",content = "";
    PendingIntent sentPI, deliveredPI;
    IntentFilter intentFilter;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    TextView SMSes;
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //---display the SMS received in the TextView---
            String sms = intent.getExtras().getString("sms");
            TextView SMSes = (TextView) findViewById(R.id.textView1);
            SMSes.setText( SMSes.getText().toString() + "\n" +  sms);

            //SMSes.scrollTo(0,150);
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smsview);
        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0,new Intent(DELIVERED), 0);

        //---intent to filter for SMS messages received---
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        SMSes = (TextView) findViewById(R.id.textView1);
        SMSes.setText("Welcome to SMS Center! \n Press Menu Button to Send SMS.");
        content = SMSes.getText().toString();
        SMSes.setFocusable(true);
        Button Back = (Button) findViewById(R.id.btn_smsBack);
        Back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (!(phoneNumber.equals(""))){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(SmsActivity.this);
                    noSelectionMade.setTitle("Exit SMS Test?");
                    noSelectionMade.setMessage("Exiting this without having submited your results will attract penalty!\nHave you submitted and want to exit SMS Test?");

                    noSelectionMade.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            String sms ="Student Has Exited SMS Test!";
                            sendSMS(phoneNumber,sms);
                            finish();
                        }
                    });
                    noSelectionMade.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {

                        }
                    });

                    noSelectionMade.show();
                }else{
                    finish();
                }
            }


        });
					 /*/@Override
						SMSes.setOnKeyListener(new View.OnKeyListener() {
							
							public boolean onKey(View v, int keyCode, KeyEvent event) {
								// TODO Auto-generated method stub
								
						
								 * if back key is pressed, and phoneNumber is not empty the it means student is taking test
								 * but wants to do something funny. therefore, report the student
								 
								if(!(phoneNumber.equalsIgnoreCase(""))){
									String sms ="";
									//switch (keyCode){
										if(keyCode == KeyEvent.KEYCODE_BACK){
											sms = "Student has pressed the BACK key";
											
										
										}else if(keyCode == KeyEvent.KEYCODE_BUTTON_START){
											sms = "Student is RESTARTING his phone";
											
										}else if(keyCode == KeyEvent.KEYCODE_HOME){
												
											
											sms = "Student has pressed the HOME KEY key";
											
									}
								
									sendSMS(phoneNumber,sms);
								}
								return false;
							}
						});*/


        try{
            //get the phone number and sms to display .
            Bundle receiveResult = getIntent().getExtras();
            phoneNumber = receiveResult.get("phone").toString();
            smsBody = receiveResult.get("sms").toString();
            content = receiveResult.get("cont").toString();

            //send the sms.
            sendSMS(phoneNumber,smsBody);
        }catch(Exception e){
            Log.d("Intent", "Not Received yet");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //String sms ="";
        if(!(phoneNumber.equalsIgnoreCase(""))){
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK :
                    //sms = "Student has pressed the BACK key";
                    break;
                case KeyEvent.KEYCODE_HOME :
                    //sms = "Student has pressed the HOME KEY key";
                    break;
                case KeyEvent.KEYCODE_BUTTON_START :
                    //sms = "Student is RESTARTING his phone";
                    break;

            }


        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        //---register the receiver---
        registerReceiver(intentReceiver, intentFilter);

        //---create the BroadcastReceiver when the SMS is sent---
        smsSentReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())

                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //---create the BroadcastReceiver when the SMS is delivered---
        smsDeliveredReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //---register the two BroadcastReceivers---
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
    }
    @Override
    public void onPause() {
        super.onPause();
        //---unregister the two BroadcastReceivers---
        unregisterReceiver(smsSentReceiver);

        unregisterReceiver(smsDeliveredReceiver);
    }

    //when SMSActivity is destroy, send SMS.
	/*@Override
	public void onDestroy(){
		super.onDestroy();
		String sms = "", s = switchView.getComponent().toString();
		if(!(s.equalsIgnoreCase("ComponentInfo{numsim.justintimetech.com.numsim/numsim.justintimetech.com.numsim.WriteSms}"))){
			if(!(phoneNumber.equalsIgnoreCase(""))){
				sms ="Student is closing this session";
		
				sendSMS(phoneNumber,sms);
			}
		}
	}*/

    //---sends an SMS message to another device---
    private void sendSMS(String phoneNumber, String message){

        message += "1234";
        StringTokenizer token = new StringTokenizer(phoneNumber,",;",false);
        while(token.hasMoreTokens()){ //transform the function in token form into an array.

            phoneNumber =  token.nextToken();


            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);

            message=  message.substring(0, message.length()-4);

            SMSes.setText( content + "\nMe :  " +  message);
            content = SMSes.getText().toString();
            SMSes.scrollTo(0,150);

        }

    }


    //create menu options
    private void CreateMenu(Menu menu)
    {
        MenuItem mnu1 = menu.add(0, 0, 0, "Write");
        {
            mnu1.setAlphabeticShortcut('W');
            mnu1.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Exit");
        {
            mnu2.setAlphabeticShortcut('E');
            mnu2.setIcon(R.drawable.ic_launcher);
        }

    }

    Intent switchView;

    private boolean MenuChoice(MenuItem item){


        switch (item.getItemId()) {
            case 0:

                switchView = new Intent(this,WriteSms.class);
                Bundle send = new Bundle();
                content = SMSes.getText().toString();
                send.putCharSequence("con", content);
                send.putCharSequence("pNum", phoneNumber);

                switchView.putExtras(send);

                startActivity(switchView);
                finish();
                break;
            case 1:
                if (!(phoneNumber.equals(""))){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(SmsActivity.this);
                    noSelectionMade.setTitle("Exit SMS Test?");
                    noSelectionMade.setMessage("Exiting this without having submited your results will attract penalty!\nHave you submitted and want to exit SMS Test?");

                    noSelectionMade.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            String sms ="Student Has Exited SMS Test!";
                            sendSMS(phoneNumber,sms);
                            finish();
                        }
                    });
                    noSelectionMade.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {

                        }
                    });

                    noSelectionMade.show();
                }else{
                    finish();
                }

                break;

        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }





}