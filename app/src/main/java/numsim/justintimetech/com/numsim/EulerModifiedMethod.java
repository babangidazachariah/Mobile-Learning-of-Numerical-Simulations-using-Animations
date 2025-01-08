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

public class EulerModifiedMethod extends Activity{

    int sPointer,pPointer, index, steps = 0;
    double initialX = 0, theDiff = 0;
    String[] theEulerCauchyContent,myEulerCauchyContent,eulerCauchyMyPostFix, eulerCauchyStack,eulerCauchyTheX,eulerCauchyTheY, eulerCauchyTheDerivative; //theContent[] will be containing the function supplied by the user,

    double stepSizeH = 0.0,initialY = 0.0;
    int numberOfStepsN = 0;

    String theFunction = "";
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
        setContentView(R.layout.eulermodifiedmethod);


        Button btnEulerModifiedBack = (Button) findViewById(R.id.btn_EulerModifiedBack);
        btnEulerModifiedBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        Button btnEulerModifiedCompute = (Button) findViewById(R.id.btn_EulerModifiedCompute);

        btnEulerModifiedCompute.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                EditText function = (EditText) findViewById(R.id.function);
                EditText initialx = (EditText) findViewById(R.id.initialX);
                EditText initialy = (EditText) findViewById(R.id.initialY);
                EditText stepsizeh = (EditText) findViewById(R.id.stepSize);
                EditText numberofstepsn = (EditText) findViewById(R.id.steps);

                try{
                    theFunction = function.getText().toString();
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerModifiedMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your function. \n It should be in the form: (f(x,y)) \n That is, (x-y).");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerModifiedMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();

                }

                try{
                    initialX = Double.parseDouble(initialx.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerModifiedMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of x. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerModifiedMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();

                }
                try{
                    initialY = Double.parseDouble(initialy.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerModifiedMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of y. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerModifiedMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();

                }
                try{
                    stepSizeH = Double.parseDouble(stepsizeh.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerModifiedMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Step Size Value. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerModifiedMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();

                }
                try{
                    numberOfStepsN = Integer.parseInt(numberofstepsn.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerModifiedMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Number of Iterations. \n It should be an Integer in the form: 00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerModifiedMethod.this, SearchActivity.class);
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
                eulerCauchyTheX = new String[numberOfStepsN + 2];
                eulerCauchyTheY = new String[numberOfStepsN + 2];
                eulerCauchyTheDerivative = new String[numberOfStepsN + 2];
                //initialize the three arrays theX[] theY[] theDerivative[].
                eulerCauchyTheX[steps] = "x";
                eulerCauchyTheY[steps] = "y";
                eulerCauchyTheDerivative[steps] = "y'";


                //PROCEDURE FOR EULER-CAUCHY METHOD:
					/*
					 * FOR A GIVEN FUNCTION f(x,y)
					 * 1) 	SOLVE FOR y' BY EVALUATING THE FUNCTION f(x,y)
					 * 2)	ADD INITIAL x0, y0 AND y' TO TABLE
					 * 3)	INCREMENT THE INITIAL x BY THE STEP VALUE h TO HAVE x1
					 * 4)	CALCULATE SOME YP1 = y0 + h(y')
					 * 5)	CALCULATE SOME YC1 = y0 + (1/2)*h*[y' + f(x1, YP1)]
					 * 6)	CALCULATE THE NOW y' = YC1 - x1
					 * 7) 	SET THE NEXT ROW OF THE TABLE USING x1, YC1, y'
					 */



                //CREATE EulerMethod object.
                EulerMethod useEuler = new EulerMethod();

                //create an array of expression by calling euler's CreateArrayExpressio(theFunction);
                theEulerCauchyContent = useEuler.CreateArrayExpression(theFunction, theEulerCauchyContent);
                //ValidateInput(theContent);
                boolean error = false;

                for(int i = 0; i < theEulerCauchyContent.length-1; i++){
                    if(!(theEulerCauchyContent[i].equalsIgnoreCase("x")) && !(theEulerCauchyContent[i].equalsIgnoreCase("y"))
                            &&  !(theEulerCauchyContent[i].equalsIgnoreCase("-"))  && !(theEulerCauchyContent[i].equalsIgnoreCase("+"))
                            && !(theEulerCauchyContent[i].equalsIgnoreCase("*")) && !(theEulerCauchyContent[i].equalsIgnoreCase("^"))
                            &&  !(theEulerCauchyContent[i].equalsIgnoreCase("/")) &&  !(theEulerCauchyContent[i].equalsIgnoreCase("sin"))
                            &&  !(theEulerCauchyContent[i].equalsIgnoreCase("cos")) &&  !(theEulerCauchyContent[i].equalsIgnoreCase("sinh"))
                            &&  !(theEulerCauchyContent[i].equalsIgnoreCase("cosh"))  && !(theEulerCauchyContent[i].equalsIgnoreCase("tan"))
                            &&  !(theEulerCauchyContent[i].equalsIgnoreCase("tanh")) &&  !(theEulerCauchyContent[i].equalsIgnoreCase("sec"))
                            &&  !(theEulerCauchyContent[i].equalsIgnoreCase("sech")) && !(theEulerCauchyContent[i].equalsIgnoreCase("cosec"))
                            && !(theEulerCauchyContent[i].equalsIgnoreCase("cosech")) && !(useEuler.CheckForNumeric(theEulerCauchyContent[i]))
                            && !(theEulerCauchyContent[i].equalsIgnoreCase("(")) && !(theEulerCauchyContent[i].equalsIgnoreCase(")")) && !(theEulerCauchyContent[i].equalsIgnoreCase("$"))){

                        error = true;

                        break;
                    }else if(theEulerCauchyContent[i].equalsIgnoreCase("$")){
                        error = false;

                        break;
                    }

                }

                if(error){
                    //dialog box to tell the user that he has wrong input.
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerModifiedMethod.this);
                    noSelectionMade.setTitle("Problem With Input.");
                    noSelectionMade.setMessage("Check your Function for wrong input!\nYou can only use the variables such as\n 'X' and 'Y'\n your arithmetic operators and any of \nthe Trignometric Function.");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerModifiedMethod.this, EulerModifiedMethod.class);
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



                    //Call and pass theFunction to CreateArrayExpression to create the expression.
                    myEulerCauchyContent = new String[theEulerCauchyContent.length];
                    myEulerCauchyContent = useEuler.MakeCopy(theEulerCauchyContent, myEulerCauchyContent);


                    //substitute the values of x and y.
                    useEuler.SubstituteValues(myEulerCauchyContent, initialX, initialY);

                    //evaluate the user expression
                    theDiff = useEuler.MyEvaluation(myEulerCauchyContent);


                    //assign the values of initialX, initialY and theDiff to a three arrays.
                    steps += 1;
                    eulerCauchyTheX[steps] = Double.toString(initialX);

                    eulerCauchyTheY[steps] = Double.toString(initialY);

                    eulerCauchyTheDerivative[steps] = Double.toString(theDiff);

                    steps += 1;
                    while(steps <= numberOfStepsN + 1){

                        initialX += stepSizeH;

                        double yP1 = useEuler.TheEuler(theDiff, initialY, stepSizeH);

                        useEuler.MakeCopy(theEulerCauchyContent, myEulerCauchyContent);

                        useEuler.SubstituteValues(myEulerCauchyContent, initialX, yP1);

                        double resultingFunction = useEuler.MyEvaluation(myEulerCauchyContent);

                        double yC1 = theEulerModified(initialY,stepSizeH,theDiff,resultingFunction);

                        initialY = yC1;

                        theDiff = yC1 - initialX;

                        //assign the values of initialX, initialY and theDiff to a three arrays.
                        eulerCauchyTheX[steps] = Double.toString(initialX);

                        eulerCauchyTheY[steps] = Double.toString(initialY);

                        eulerCauchyTheDerivative[steps] = Double.toString(theDiff);

                        steps += 1;

                    }
                    Intent sendResult = new Intent(EulerModifiedMethod.this,Result.class);

                    Bundle send = new Bundle();

                    //pass the three arrays to the activity Result.

                    send.putStringArray("theX", eulerCauchyTheX);
                    send.putStringArray("theY", eulerCauchyTheY);
                    send.putStringArray("theDerivative", eulerCauchyTheDerivative);

                    sendResult.putExtras(send);

                    //start the Result Activity.
                    startActivity(sendResult);
                }
            }
        });

    }
    public double theEulerModified(double y0, double h, double diff, double resul){


        double result = y0 + 0.5*h*(diff + resul);



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
        MenuItem mnu5 = menu.add(0, 4, 4, "Euler Tutor");
        {
            mnu5.setAlphabeticShortcut('K');
        }
        MenuItem mnu6 = menu.add(0, 5, 5, "SMS Center");
        {
            mnu6.setAlphabeticShortcut('S');
        }

    }
    private boolean MenuChoice(MenuItem item){

        Intent switchView;
        switch (item.getItemId()) {
            case 0:
                switchView = new Intent(this,EulerMethod.class);
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
                switchView = new Intent(this,EulerTutor.class);
                startActivity(switchView);
                break;
            case 5:
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
