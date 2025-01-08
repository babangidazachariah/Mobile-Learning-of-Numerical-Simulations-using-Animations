package  numsim.justintimetech.com.numsim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class RKResult extends Activity{
    String[] theX, theY, theDerivative, RK1,RK2,RK3,RK4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        //get the three arrays to display result
        Bundle receiveResult = getIntent().getExtras();

        theX = receiveResult.getStringArray("theX");
        theY = receiveResult.getStringArray("theY");
        theDerivative = receiveResult.getStringArray("theDerivative");
        RK1 = receiveResult.getStringArray("rk1");
        RK2 = receiveResult.getStringArray("rk2");
        RK3 = receiveResult.getStringArray("rk3");
        RK4 = receiveResult.getStringArray("rk4");


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
        buildResult.append("   " + theX[0] + "    |    " + "   " + theY[0] + "       |" + "     " + theDerivative[0] + "     " + "\n");

        for(int i = 1; i < theX.length; i++){
            valueX = Double.parseDouble(theX[i]);
            valueY = Double.parseDouble(theY[i]);
            value = Double.parseDouble(theDerivative[i]);
            buildResult.append(formatX.format(valueX) + "   |    " + formatY.format(valueY) + "   |    " + format.format(value) + "\n");
        }

        //assign the result to the textview for display.
        theResult.setText(buildResult.toString());



        Button closeResult = (Button) findViewById(R.id.btn_CloseResult);
        closeResult.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                //finish();
                Intent sendResult = new Intent(RKResult.this,GraphStuffsActivity.class);

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
}
