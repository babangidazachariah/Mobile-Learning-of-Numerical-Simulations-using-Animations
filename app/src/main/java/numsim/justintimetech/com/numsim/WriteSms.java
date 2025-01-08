package numsim.justintimetech.com.numsim;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WriteSms extends Activity{
    String phoneNumber = "",smsBody = "",content = "";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write);

        EditText phone = (EditText)findViewById(R.id.number);
        EditText smsbody = (EditText)findViewById(R.id.sms);
        try{
            //get the phone number and sms to display .
            Bundle receiveResult = getIntent().getExtras();
            content = receiveResult.get("con").toString();
            phoneNumber = receiveResult.get("pNum").toString();
        }catch(Exception e){
            Log.d("Intent", "Not Received yet");
        }
        try{
            //get the phone number and sms to display .
            Bundle receiveResult = getIntent().getExtras();
            phoneNumber = receiveResult.get("phone").toString();
            smsBody = receiveResult.get("sms").toString();
            phone.setText(phoneNumber);
            smsbody.setText(smsBody);
        }catch(Exception e){
            Log.d("Intent", "Not Received yet");
        }



        phone.setText(phoneNumber);

        //to assign focus to view, determine if phoeNumber == ""
        if(!(phoneNumber=="")){
            smsbody.requestFocus();
        }else{
            phone.requestFocus();
        }
        Button send = (Button)findViewById(R.id.btn_Send);
        Button cancel = (Button)findViewById(R.id.btn_Cancel);

        cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                //end activity
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText phone = (EditText)findViewById(R.id.number);
                EditText sms = (EditText)findViewById(R.id.sms);

                try{
                    phoneNumber = phone.getText().toString();
                }catch(Exception e){

                    //add a dialog box to promp user about phone number
                }
                try{
                    smsBody = sms.getText().toString();
                }catch(Exception e){

                    //add a dialog box to promp user about phone number
                }
                if(!(phoneNumber.equalsIgnoreCase("") && smsBody.equalsIgnoreCase(""))){
                    Intent switchView = new Intent(WriteSms.this,SmsActivity.class);

                    Bundle send = new Bundle();
                    send.putCharSequence("phone", phoneNumber);
                    send.putCharSequence("sms", smsBody);
                    send.putCharSequence("cont",content);

                    switchView.putExtras(send);

                    startActivity(switchView);
                    finish();
                }else{

                    //dialog to inform user.
                    //dialog box to tell the user that he has wrong input.
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(WriteSms.this);
                    noSelectionMade.setTitle("Problem With Input.");
                    noSelectionMade.setMessage("Check your Phone Number and SMS Body fields to ensure they are not empty!!");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(WriteSms.this, WriteSms.class);
                            Bundle send = new Bundle();
                            send.putCharSequence("phone", phoneNumber);
                            send.putCharSequence("sms", smsBody);

                            intent.putExtras(send);

                            //starActivity(getIntent());
                            startActivity(intent);
                            finish();
                        }
                    });

                    noSelectionMade.show();

                }
            }
        });


    }

    //create menu options
    private void CreateMenu(Menu menu)
    {
        MenuItem mnu1 = menu.add(0, 0, 0, "Send");
        {
            mnu1.setAlphabeticShortcut('E');
            mnu1.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Cancel");
        {
            mnu2.setAlphabeticShortcut('R');
            mnu2.setIcon(R.drawable.ic_launcher);
        }
    }
    private boolean MenuChoice(MenuItem item){

        // TODO Auto-generated method stub
        EditText phone = (EditText)findViewById(R.id.number);
        EditText sms = (EditText)findViewById(R.id.sms);

        try{
            phoneNumber = phone.getText().toString();
        }catch(Exception e){

            //add a dialog box to promp user about phone number
        }
        try{
            smsBody = sms.getText().toString();
        }catch(Exception e){

        }

        Intent switchView = new Intent(this,SmsActivity.class);

        switch (item.getItemId()) {
            case 0:

                Bundle send = new Bundle();
                send.putCharSequence("phone", phoneNumber);
                send.putCharSequence("sms", smsBody);
                send.putCharSequence("cont",content);

                startActivity(switchView);

                finish();
                break;
            case 1:
                //return to the sms interface.
                switchView = new Intent(this,SmsActivity.class);
                startActivity(switchView);
                finish();
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

    public void sendBundle(){

    }

}
