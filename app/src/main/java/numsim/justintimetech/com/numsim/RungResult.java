package  numsim.justintimetech.com.numsim;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class RungResult extends Activity{


    String[] theX, theY, theDerivative,theRK1,theRK2, theRK3, theRK4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Button viewGraph =(Button)findViewById(R.id.btn_ViewGraph);
        viewGraph.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent sendResult = new Intent(RungResult.this,GraphStuffsActivity.class);

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
        Button close = (Button)findViewById(R.id.btn_CloseResult);
        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();

            }
        });
        //get the three arrays to display result
        Bundle receiveResult = getIntent().getExtras();

        theX = receiveResult.getStringArray("theX");
        theY = receiveResult.getStringArray("theY");
        theRK1 = receiveResult.getStringArray("theRk1");
        theRK2 = receiveResult.getStringArray("theRk2");
        theRK3 = receiveResult.getStringArray("theRk3");
        theRK4 = receiveResult.getStringArray("theRk4");
        theDerivative = receiveResult.getStringArray("theDerivative");

        TextView theResult = (TextView) findViewById(R.id.txtResult);

        //iterate through the arrays using a FOR_NEXT loop.

        StringBuilder buildResult = new StringBuilder(); //to help build result.

        DecimalFormat formatX = new DecimalFormat("0.000");
        formatX.setMaximumFractionDigits(2);
        DecimalFormat formatY = new DecimalFormat("0.000");
        formatY.setMaximumFractionDigits(3);
        DecimalFormat format = new DecimalFormat("0.0000");
        format.setMaximumFractionDigits(4);

        double valueX = 0, valueY = 0, value = 0,rk1 =0, rk2 =0, rk3 = 0, rk4 =0 ;
        buildResult.append(theX[0] + "     |    " + "   " + theY[0] + "        |    " + "   " + theRK1[0] + "      |    " + "   " + theRK2[0] + "      |    " + "   " + theRK3[0] + "     |    " + "   " + theRK4[0] + "        |" + "     " + theDerivative[0] + "     " + "\n");

        for(int i = 1; i < theX.length; i++){
            valueX = Double.parseDouble(theX[i]);
            rk1 = Double.parseDouble(theRK1[i]);
            rk2 = Double.parseDouble(theRK2[i]);
            rk3 = Double.parseDouble(theRK3[i]);
            rk4 = Double.parseDouble(theRK4[i]);
            valueY = Double.parseDouble(theY[i]);
            value = Double.parseDouble(theDerivative[i]);
            buildResult.append(formatX.format(valueX) + "   |    " + formatY.format(valueY) + "   |    " + formatY.format(rk1)+ "   |    " + formatY.format(rk2) + "   |    " + formatY.format(rk3) + "   |    " + formatY.format(rk4)  + "   |    " + format.format(value) + "\n");
        }

        //assign the result to the textview for display.
        theResult.setText(buildResult.toString());



        Button closeResult = (Button) findViewById(R.id.btn_CloseResult);
        closeResult.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                //finish();
                Intent sendResult = new Intent(RungResult.this,GraphStuffsActivity.class);

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
