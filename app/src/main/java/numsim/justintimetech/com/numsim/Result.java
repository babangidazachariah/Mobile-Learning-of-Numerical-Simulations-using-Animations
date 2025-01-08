package  numsim.justintimetech.com.numsim;

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

public class Result extends Activity {
    String[] theX, theY, theDerivative;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        //get the three arrays to display result
        Bundle receiveResult = getIntent().getExtras();

        theX = receiveResult.getStringArray("theX");
        theY = receiveResult.getStringArray("theY");
        theDerivative = receiveResult.getStringArray("theDerivative");

        TextView theResult = (TextView) findViewById(R.id.txtResult);

        //iterate through the arrays using a FOR_NEXT loop.

        StringBuilder buildResult = new StringBuilder(); //to help build result.

        DecimalFormat formatX = new DecimalFormat("0.00000");
        formatX.setMaximumFractionDigits(2);
        DecimalFormat formatY = new DecimalFormat("0.00000");
        formatY.setMaximumFractionDigits(3);
        DecimalFormat format = new DecimalFormat("0.000000");
        format.setMaximumFractionDigits(4);

        double valueX = 0, valueY = 0, value = 0;
        buildResult.append("   " + theX[0] + "      |      " + "   " + theY[0] + "        |" + "       " + theDerivative[0] + "       " + "\n");

        for(int i = 1; i < theX.length; i++){
            valueX = Double.parseDouble(theX[i]);
            valueY = Double.parseDouble(theY[i]);
            value = Double.parseDouble(theDerivative[i]);
            buildResult.append(formatX.format(valueX) + "     |      " + formatY.format(valueY) + "     |      " + format.format(value) + "\n");
        }

        //assign the result to the textview for display.
        theResult.setText(buildResult.toString());



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
                Intent sendResult = new Intent(Result.this,GraphStuffsActivity.class);

                Bundle send = new Bundle();

                //pass the three arrays to the activity Result.

                send.putStringArray("theX", theX);
                send.putStringArray("theY", theY);
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
