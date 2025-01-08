package numsim.justintimetech.com.numsim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class IntegrationResult extends Activity {
    String[] simsontheX, simsontheY;
    double first,last,valueIs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        //get the three arrays to display result
        Bundle receiveResult = getIntent().getExtras();

        simsontheX = receiveResult.getStringArray("simsontheX");
        simsontheY = receiveResult.getStringArray("simsontheY");
        first = receiveResult.getDouble("first");
        last = receiveResult.getDouble("last");
        valueIs = receiveResult.getDouble("valueIs");

        TextView theResult = (TextView) findViewById(R.id.txtResult);

        //iterate through the arrays using a FOR_NEXT loop.

        StringBuilder buildX = new StringBuilder(); //to help build result.
        StringBuilder buildY = new StringBuilder(); //to help build result.
        DecimalFormat formatX = new DecimalFormat("0.0000");
        formatX.setMaximumFractionDigits(4);

        String result = "";
        double valueX = 0, valueY = 0;

        buildX.append("X" );
        buildY.append("X" );
        for(int i = 1; i < simsontheX.length; i++){
            valueX = Double.parseDouble(simsontheX[i]);
            valueY = Double.parseDouble(simsontheY[i]);
            buildX.append("  |  " + formatX.format(valueX));
            buildY.append("  |  " + formatX.format(valueY));
        }

        result = buildX.toString() +" \n" + buildY.toString() + "\n\n\n" + " Answer is : " + valueIs;



        //assign the result to the textview for display.
        theResult.setText(result);



        Button closeResult = (Button) findViewById(R.id.btn_CloseResult);
        closeResult.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                finish();
            }

        });

        Button viewGraph =(Button)findViewById(R.id.btn_ViewGraph);
        viewGraph.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent sendResult = new Intent(IntegrationResult.this,GraphStuffsActivity.class);

                Bundle send = new Bundle();

                //pass the three arrays to the activity Result.

                send.putStringArray("theX", simsontheX);
                send.putStringArray("theY", simsontheY);
                //send.putStringArray("theDerivative", theDerivative);

                sendResult.putExtras(send);

                //start the Result Activity.
                startActivity(sendResult);
            }
        });
    }
    //create menu options
    private void CreateMenu(Menu menu)
    {
        MenuItem mnu1 = menu.add(0, 0, 0, "Euler-Cauchy Method");
        {
            mnu1.setAlphabeticShortcut('E');
            mnu1.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Runge-Kutta Method");
        {
            mnu2.setAlphabeticShortcut('R');
            mnu2.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "Euler-Cauchy Tutor");
        {
            mnu3.setAlphabeticShortcut('C');
            mnu3.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu4 = menu.add(0, 3, 3, "Runge-Kutta Tutor");
        {
            mnu4.setAlphabeticShortcut('K');
        }
        MenuItem mnu5 = menu.add(0, 3, 4, "SMS Center");
        {
            mnu5.setAlphabeticShortcut('S');
        }
    }
    private boolean MenuChoice(MenuItem item){

        Intent switchView;
        switch (item.getItemId()) {
            case 0:
                switchView = new Intent(this,EulerModifiedMethod.class);
                startActivity(switchView);
                break;
            case 1:
                switchView = new Intent(this,FourthRungeKuttaMethod.class);
                startActivity(switchView);
                break;
            case 2:
                switchView = new Intent(this,EulerCauchyTutor.class);
                startActivity(switchView);
                break;
            case 3:
                switchView = new Intent(this,RungeKuttaTutor.class);
                startActivity(switchView);
                break;
            case 4:
                switchView = new Intent(this,SmsActivity.class);
                startActivity(switchView);
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
