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

public class FourthRungeKuttaMethod extends Activity {
    String[] theRK4Content, myRK4Content,rk4TheX,rk4TheY,rk4TheDerivative, rk1,rk2,rk3,rk4;
    String theFunction ="";
    double initialX = 0.0, initialY = 0.0,stepSizeH = 0.0;
    int numberOfStepsN = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            String x, y, s,n,f;
            //get the user inputs to display .
            Bundle receiveResult = getIntent().getExtras();
            f = receiveResult.get("function").toString();
            x = receiveResult.get("initialx").toString();
            y = receiveResult.get("initialy").toString();
            s = receiveResult.get("stepSize").toString();
            n = receiveResult.get("numberOfSteps").toString();


            EditText function = (EditText) findViewById(R.id.function);
            EditText initialx = (EditText) findViewById(R.id.initialX);
            EditText initialy = (EditText) findViewById(R.id.initialY);
            EditText stepsizeh = (EditText) findViewById(R.id.stepSize);
            EditText numberofstepsn = (EditText) findViewById(R.id.steps);


            function.setText(f);
            initialx.setText(x);
            initialy.setText(y);
            stepsizeh.setText(s);
            numberofstepsn.setText(n);

        }catch(Exception e){
            Log.d("Intent", "Not Received yet");
        }


        setContentView(R.layout.fourthrungekuttamethod);

        Button rk4Compute = (Button) findViewById(R.id.btn_RK4Compute);
        rk4Compute.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                EditText function = (EditText) findViewById(R.id.RK4function);
                EditText initialx = (EditText) findViewById(R.id.RK4initialX);
                EditText initialy = (EditText) findViewById(R.id.RK4initialY);
                EditText stepsizeh = (EditText) findViewById(R.id.RK4stepSize);
                EditText numberofstepsn = (EditText) findViewById(R.id.RK4steps);


                try{
                    theFunction = function.getText().toString();
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(FourthRungeKuttaMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of x. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(FourthRungeKuttaMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    initialX = Double.parseDouble(initialx.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(FourthRungeKuttaMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your function. \n It should be in the form: (f(x,y)) \n That is, (x-y).");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(FourthRungeKuttaMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    initialY = Double.parseDouble(initialy.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(FourthRungeKuttaMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of y. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(FourthRungeKuttaMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    stepSizeH = Double.parseDouble(stepsizeh.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(FourthRungeKuttaMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Step Size Value. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(FourthRungeKuttaMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    numberOfStepsN = Integer.parseInt(numberofstepsn.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(FourthRungeKuttaMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Number of Iterations. \n It should be an Integer in the form: 00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(FourthRungeKuttaMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }




                //test the numberOfStepsN variable to see if it is not given, we assign five to it.
                if(!(numberOfStepsN > 0 )){
                    numberOfStepsN = 5;
                }
                //complete the declaration of the three arrays using the numberOfStepsN.
                int steps = 0;
                rk4TheX = new String[numberOfStepsN + 1];
                rk4TheY = new String[numberOfStepsN + 1];
                rk4TheDerivative = new String[numberOfStepsN + 1];
                rk1 =  new String[numberOfStepsN + 1];
                rk2 = new String[numberOfStepsN + 1];
                rk3 =  new String[numberOfStepsN + 1];
                rk4 =  new String[numberOfStepsN + 1];
                //initialize the three arrays theX[] theY[] theDerivative[].
                rk4TheX[steps] = "x";
                rk4TheY[steps] = "y";
                rk4TheDerivative[steps] = "y'";
                rk1[steps] = "K1";
                rk2[steps] = "K2";
                rk3[steps] = "K3";
                rk4[steps] = "K4";


                //create euler's object
                EulerMethod rkUseEuler = new EulerMethod();

                theRK4Content = rkUseEuler.CreateArrayExpression(theFunction, theRK4Content);

                //ValidateInput(theContent);
                boolean error = false;

                for(int i = 0; i < theRK4Content.length-1; i++){
                    if(!(theRK4Content[i].equalsIgnoreCase("x")) && !(theRK4Content[i].equalsIgnoreCase("y"))
                            &&  !(theRK4Content[i].equalsIgnoreCase("-"))  && !(theRK4Content[i].equalsIgnoreCase("+"))
                            && !(theRK4Content[i].equalsIgnoreCase("*")) && !(theRK4Content[i].equalsIgnoreCase("^"))
                            &&  !(theRK4Content[i].equalsIgnoreCase("/")) &&  !(theRK4Content[i].equalsIgnoreCase("sin"))
                            &&  !(theRK4Content[i].equalsIgnoreCase("cos")) &&  !(theRK4Content[i].equalsIgnoreCase("sinh"))
                            &&  !(theRK4Content[i].equalsIgnoreCase("cosh"))  && !(theRK4Content[i].equalsIgnoreCase("tan"))
                            &&  !(theRK4Content[i].equalsIgnoreCase("tanh")) &&  !(theRK4Content[i].equalsIgnoreCase("sec"))
                            &&  !(theRK4Content[i].equalsIgnoreCase("sech")) && !(theRK4Content[i].equalsIgnoreCase("cosec"))
                            && !(theRK4Content[i].equalsIgnoreCase("cosech")) && !(rkUseEuler.CheckForNumeric(theRK4Content[i]))
                            && !(theRK4Content[i].equalsIgnoreCase("(")) && !(theRK4Content[i].equalsIgnoreCase(")")) && !(theRK4Content[i].equalsIgnoreCase("$"))){

                        error = true;

                        break;
                    }else if(theRK4Content[i].equalsIgnoreCase("$")){
                        error = false;

                        break;
                    }

                }

                if(error){
                    //dialog box to tell the user that he has wrong input.
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(FourthRungeKuttaMethod.this);
                    noSelectionMade.setTitle("Problem With Input.");
                    noSelectionMade.setMessage("Check your Function for wrong input!\nYou can only use the variables such as\n 'X' and 'Y'\n your arithmetic operators and any of \nthe Trignometric Function.");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(FourthRungeKuttaMethod.this, FourthRungeKuttaMethod.class);
                            Bundle send = new Bundle();
                            send.putCharSequence("function", theFunction);
                            send.putDouble("initialx", initialX);
                            send.putDouble("initialy", initialY);
                            send.putDouble("stepS", stepSizeH);
                            send.putInt("numberOfSteps", numberOfStepsN);
                            intent.putExtras(send);

                            //starActivity(getIntent());
                            startActivity(intent);
                            finish();
                        }
                    });

                    noSelectionMade.show();


                }else{




                    myRK4Content = new String[theRK4Content.length];
                    double k1 = 0,k2 = 0,k3 = 0,k4 = 0, y = 0;
                    steps += 1;
                    while(steps <= numberOfStepsN ){
                        //for k1:
                        myRK4Content = rkUseEuler.MakeCopy(theRK4Content, myRK4Content);
                        rkUseEuler.SubstituteValues(myRK4Content, initialX, initialY);

                        k1 = rkUseEuler.MyEvaluation(myRK4Content);
                        rk1[steps] = Double.toString(k1);

                        // for k2:
                        myRK4Content = rkUseEuler.MakeCopy(theRK4Content, myRK4Content);
                        rkUseEuler.SubstituteValues(myRK4Content, (initialX + (stepSizeH/2)), (initialY + (stepSizeH * (k1/2))));

                        k2 = rkUseEuler.MyEvaluation(myRK4Content);
                        rk2[steps] = Double.toString(k2);

                        //for k3
                        myRK4Content = rkUseEuler.MakeCopy(theRK4Content, myRK4Content);
                        rkUseEuler.SubstituteValues(myRK4Content, (initialX + (stepSizeH/2)), (initialY + (stepSizeH * (k2/2))));

                        k3 = rkUseEuler.MyEvaluation(myRK4Content);
                        rk3[steps] = Double.toString(k3);

                        //for k4:
                        myRK4Content = rkUseEuler.MakeCopy(theRK4Content, myRK4Content);
                        rkUseEuler.SubstituteValues(myRK4Content, (initialX + stepSizeH), (initialY + (stepSizeH * k3)));

                        k4 = rkUseEuler.MyEvaluation(myRK4Content);
                        rk4[steps] = Double.toString(k4);
                        y = initialY + ((stepSizeH/6) * (k1 + (2 * k2) + (2 * k3) + k4));

                        initialX += stepSizeH;
                        rk4TheX[steps] = Double.toString(initialX);
                        rk4TheY[steps] = Double.toString(initialY);
                        rk4TheDerivative[steps] = Double.toString(y);

                        //re-initialize y

                        initialY = y;
                        steps += 1;
                    }

                    Intent sendResult = new Intent(FourthRungeKuttaMethod.this,RungResult.class);

                    Bundle send = new Bundle();

                    //pass the three arrays to the activity Result.

                    send.putStringArray("theX", rk4TheX);
                    send.putStringArray("theRk1", rk1);
                    send.putStringArray("theRk2", rk2);
                    send.putStringArray("theRk3", rk3);
                    send.putStringArray("theRk4", rk4);
                    send.putStringArray("theY", rk4TheY);
                    send.putStringArray("theDerivative", rk4TheDerivative);

                    sendResult.putExtras(send);

                    //start the Result Activity.
                    startActivity(sendResult);
                }
            }
        });

        Button rk4Back = (Button) findViewById(R.id.btn_RK4Back);
        rk4Back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
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
                switchView = new Intent(this,EulerMethod.class);
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
