package numsim.justintimetech.com.numsim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondRungeKutta extends Activity{
    int sPointer,pPointer, index, steps = 0;
    double initialX = 0, theDiff = 0;
    String[] theContent,myContent,stack,theX,theY, theDerivative; //theContent[] will be containing the function supplied by the user,
    //postFix[] will be containing the post-Fix expression of the supplied function.
    //stack[] will be containing the operators.
    //myPostFix[] will be containing the the copy of the postFix[] after it has been created.


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rungekuttamethod);

        Button rk4Compute = (Button) findViewById(R.id.btn_RK4Compute);
        rk4Compute.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText function = (EditText) findViewById(R.id.RK4function);
                EditText initialx = (EditText) findViewById(R.id.RK4initialX);
                EditText initialy = (EditText) findViewById(R.id.RK4initialY);
                EditText stepsizeh = (EditText) findViewById(R.id.RK4stepSize);
                EditText numberofstepsn = (EditText) findViewById(R.id.RK4steps);

                String theFunction = function.getText().toString();
                double initialX = Double.parseDouble(initialx.getText().toString());
                double initialY = Double.parseDouble(initialy.getText().toString());
                double stepSizeH = Double.parseDouble(stepsizeh.getText().toString());
                int numberOfStepsN = Integer.parseInt(numberofstepsn.getText().toString());


                //test the numberOfStepsN variable to see if it is not given, we assign five to it.
                if(!(numberOfStepsN > 0 )){
                    numberOfStepsN = 5;
                }

                //complete the declaration of the three arrays using the numberOfStepsN.
                steps = 0;
                theX = new String[numberOfStepsN + 1];
                theY = new String[numberOfStepsN + 1];
                theDerivative = new String[numberOfStepsN + 1];
                //initialize the three arrays theX[] theY[] theDerivative[].
                theX[steps] = "x";
                theY[steps] = "y";
                theDerivative[steps] = "y'";

                //Call and pass theFunction to the PostFix to create the post-fix expression.

                theContent = useEuler.CreateArrayExpression(theFunction, theContent);
                //Create Copy of the user expression so as to enable repeated evaluation until
                //the number of steps are satisfied. it is copied into myContent[] array.
                //myContent = new String[theContent.length];
                //myContent = useEuler.MakeCopy(theContent, myContent);
                steps += 1;

                double K1 = 0, K2 = 0, K3 = 0, K4 = 0;

                while(steps <= numberOfStepsN){
	        			/*K1 = hf(x,y)
	        				K2 = hf(x + (1/2)h, y + (1/2)K1)
	        				y = yn + K2	
	        				
	        			*/
                    K1 = RKutta(theContent,initialX,initialY);

                    K2 = RKutta(theContent,(initialX + (stepSizeH/2)),(initialY + (K1/2)));

                    //K3 = RKutta(theContent,(initialX + (stepSizeH/2)),(initialY + (K2/2)));

                    initialY += K2;

                    theX[steps] = Double.toString(initialX);
                    theY[steps] = Double.toString(initialY);
                    theDerivative[steps] = "y'";

                    initialX += stepSizeH;
                    //K4 = RKutta(theContent,initialX,(initialY + K3));


                    steps += 1;
                }
            }
        });
    }
    EulerMethod useEuler = new EulerMethod();
    public double RKutta(String[] theContent, double x, double y){
        double result = 0;
        String[] myContent = new String[theContent.length];
        myContent = useEuler.MakeCopy(theContent, myContent);
        useEuler.SubstituteValues(myContent, x, y);
        result = useEuler.MyEvaluation(myContent);
        return result;
    }

    //create menu options
    private void CreateMenu(Menu menu)
    {
        MenuItem mnu1 = menu.add(0, 0, 0, "Euler-Cauchy Method");
        {
            mnu1.setAlphabeticShortcut('E');
            mnu1.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Euler Method");
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
        MenuItem mnu5 = menu.add(0, 4, 4, "SMS Center");
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
