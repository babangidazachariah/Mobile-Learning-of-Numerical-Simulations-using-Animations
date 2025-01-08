package  numsim.justintimetech.com.numsim;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.DecimalFormat;

public class RungeKuttaTutor extends Activity implements OnInitListener{
    public int MY_DATA_CHECK_CODE = 0;
    public TextToSpeech tts;
    public String text;
    public TypeWriter writer;

    String[] theRK4Content, myRK4Content,rk4TheX,rk4TheY,rk4TheDerivative, rk1,rk2,rk3,rk4;
    String theFunction ="";
    double initialX = 0.0,initialY = 0.0,stepSizeH = 0.0;
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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        final Button endButton = new Button(this);
        final android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        //---create a layout---
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        writer = new TypeWriter(this);

        Button rk4Compute = (Button) findViewById(R.id.btn_RK4Compute);
        rk4Compute.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                EditText function = (EditText) findViewById(R.id.RK4function);
                EditText initialx = (EditText) findViewById(R.id.RK4initialX);
                EditText initialy = (EditText) findViewById(R.id.RK4initialY);
                EditText stepsizeh = (EditText) findViewById(R.id.RK4stepSize);
                EditText numberofstepsn = (EditText) findViewById(R.id.RK4steps);
                boolean parseTheFunction = false, parseInitialY = false, parseInitialX = false, parseStepSize =false;

                try{
                    theFunction = function.getText().toString();
                    parseTheFunction = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(RungeKuttaTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of x. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(RungeKuttaTutor.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    initialX = Double.parseDouble(initialx.getText().toString());
                    parseInitialX = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(RungeKuttaTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your function. \n It should be in the form: (f(x,y)) \n That is, (x-y).");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(RungeKuttaTutor.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    initialY = Double.parseDouble(initialy.getText().toString());
                    parseInitialY =true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(RungeKuttaTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of y. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(RungeKuttaTutor.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }

                try{
                    stepSizeH = Double.parseDouble(stepsizeh.getText().toString());
                    parseStepSize = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(RungeKuttaTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Step Size Value. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(RungeKuttaTutor.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    numberOfStepsN = Integer.parseInt(numberofstepsn.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(RungeKuttaTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Number of Iterations. \n It should be an Integer in the form: 00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(RungeKuttaTutor.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }

                if(!(theFunction.length() > 2)){
                    //dialog box to tell the user that he has wrong input.
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(RungeKuttaTutor.this);
                    noSelectionMade.setTitle("Problem With Input.");
                    noSelectionMade.setMessage("Input your Function in terms of the variables such as\n 'X' and 'Y'\n your arithmetic operators and any of \nthe Trignometric Function.");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(RungeKuttaTutor.this, EulerTutor.class);
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

                }else if((parseTheFunction == true && parseInitialX == true && parseInitialY == true && parseStepSize ==true && parseStepSize == true)) {

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


                    DecimalFormat formatX = new DecimalFormat("0.00");
                    formatX.setMaximumFractionDigits(2);
                    DecimalFormat formatY = new DecimalFormat("0.000");
                    formatY.setMaximumFractionDigits(3);
                    DecimalFormat format = new DecimalFormat("0.0000");
                    format.setMaximumFractionDigits(4);

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
                        AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(RungeKuttaTutor.this);
                        noSelectionMade.setTitle("Problem With Input.");
                        noSelectionMade.setMessage("Check your Function for wrong input!\nYou can only use the variables such as\n 'X' and 'Y'\n your arithmetic operators and any of \nthe Trignometric Function.");

                        noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Intent intent = new Intent(RungeKuttaTutor.this, RungeKuttaTutor.class);
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
                        StringBuilder buildSolution = new StringBuilder();


                        buildSolution.append("STEP 1: \nWe will start with n = 0  to determine our y" +steps + ". \nTo do this, we have to calculate our k1, k2, k3, and k4 using the Runge-Kutta's approach. \n Therefore, calculating our k1 : \nOur Function is: \n y' = " +  theFunction + "  ; \n the initial value of x is: \n x = " + initialX + " ; \n the initial value of y is: \n y = " + initialY + "  . \n");

                        while(steps <= numberOfStepsN ){

                            buildSolution.append("But we know that our k1 is given by \n k1 = f(x,y) ; which implies that k1 = " + theFunction + " , at f(" + initialX + " , " + initialY + "). \n");

                            //for k1:
                            myRK4Content = rkUseEuler.MakeCopy(theRK4Content, myRK4Content);
                            rkUseEuler.SubstituteValues(myRK4Content, initialX, initialY);
                            String substitutedFunction = "";
                            substitutedFunction = substFunction(myRK4Content);

                            String result = "";
                            k1 = rkUseEuler.MyEvaluation(myRK4Content);
                            result = formatY.format(k1);
                            rk1[steps] = result;


                            buildSolution.append("Substituting the value of x and that of y into the function we have: \n k1 = " + substitutedFunction + " ; which evaluates to k1 = " + result +" . \n" );
                            // for k2:
                            myRK4Content = rkUseEuler.MakeCopy(theRK4Content, myRK4Content);

                            double curX = initialX + (stepSizeH/2), curY = initialY + (stepSizeH * (k1/2));
                            rkUseEuler.SubstituteValues(myRK4Content, curX, curY);

                            substitutedFunction = "";
                            substitutedFunction = substFunction(myRK4Content);

                            k2 = rkUseEuler.MyEvaluation(myRK4Content);
                            result = formatY.format(k2);
                            rk2[steps] = result;

                            buildSolution.append("Again, we calculate our k2. \n and We know that our k2 is given by \n k2 = f((x + h/2),(y + h(k1/2)) ; \n where our\n x +(h/2) = " + initialX + "+ (" + stepSizeH +"/2) = " + curX +"; \n and our y + (h(k1/2) = " + initialY + " + (" + stepSizeH +"(" + result + "/2)) = " + curY + " .\n");
                            buildSolution.append("Which implies that \n k2 = " + theFunction + " ,\n at f(" + initialX + " , " + initialY + "). \n Substituting these values into this function that defines k2 we have \n k2 = " + substitutedFunction + " ; \n which evaluates to \n k2 = " + result + " .\n");


                            //for k3
                            myRK4Content = rkUseEuler.MakeCopy(theRK4Content, myRK4Content);
                            curX = initialX + (stepSizeH/2);
                            curY = initialY + (stepSizeH * (k2/2));
                            rkUseEuler.SubstituteValues(myRK4Content, curX, curY);

                            substitutedFunction = "";
                            substitutedFunction = substFunction(myRK4Content);

                            k3 = rkUseEuler.MyEvaluation(myRK4Content);
                            result = formatY.format(k3);
                            rk3[steps] = result;

                            buildSolution.append("Again, we calculate our k3. \n and We know that our k23 is given by \n k3 = f((x + h/2) , (y + h(k2/2)) ; \n where our\n x +(h/2) = " + initialX + "+ (" + stepSizeH +"/2) = " + curX +"; \n and our y + (h(k2/2) = " + initialY + " + (" + stepSizeH +"(" + result + "/2)) = " + curY + " .\n");
                            buildSolution.append("Which implies that \n k3 = " + theFunction + " ,\n at f(" + curX + " , " + curY + "). \n Substituting these values into this function that defines k3 we have \n k3 = " + substitutedFunction + " ; \n which evaluates to \n k3 = " + result + " .\n");

                            //for k4:
                            myRK4Content = rkUseEuler.MakeCopy(theRK4Content, myRK4Content);
                            curX  = initialX + stepSizeH;
                            curY = initialY + (stepSizeH * k3);
                            rkUseEuler.SubstituteValues(myRK4Content, curX, curY);

                            substitutedFunction = "";
                            substitutedFunction = substFunction(myRK4Content);

                            k4 = rkUseEuler.MyEvaluation(myRK4Content);
                            result = formatY.format(k4);
                            rk4[steps] = result;

                            buildSolution.append("And then we calculate our k4.\n And we know that our k4 from the introduction is given by \n k4 = f((x + h) , (y + (h * k3))) ;\n where \n x + h = " + curX + " , \n and y + (h * k3) = " + curY + " . \n ");
                            buildSolution.append("Which implies that \n k4 = " + theFunction + " at f(" + curX + " , " + curY + ") . \n Substituting these values into the function that defines k4 we have \n k4 = " + substitutedFunction + " ; which evaluates to \n k4 = " + result + " .\n");

                            y = initialY + ((stepSizeH/6) * (k1 + (2 * k2) + (2 * k3) + k4));

                            initialX += stepSizeH;
                            rk4TheX[steps] = Double.toString(initialX);
                            rk4TheY[steps] = Double.toString(initialY);
                            rk4TheDerivative[steps] = Double.toString(y);

                            //re-initialize y

                            initialY = y;
                            steps += 1;
                            buildSolution.append("\n STEP 2 : \n Now, let our n = " + (steps -1) + " and we determine y" + steps + " .\n For this, we have to calculate our k1, k2, k3, and k4 using the Runge-Kutta's approach . \n");
                        }

                        buildSolution.append("\n\n Therefore, the tabulated solution is given below: \n\n" );

                        text = buildSolution.toString();

                        //iterate through the arrays using a FOR_NEXT loop.
                        StringBuilder buildResult = new StringBuilder(); //to help build result.



                        double valueX = 0, valueY = 0, value = 0;
                        buildResult.append("   " + rk4TheX[0] + "    |    " + "   " + rk4TheY[0] + "       |" + "     " + rk4TheDerivative[0] + "     " + "\n");

                        for(int i = 1; i < rk4TheX.length; i++){
                            valueX = Double.parseDouble(rk4TheX[i]);
                            valueY = Double.parseDouble(rk4TheY[i]);
                            value = Double.parseDouble(rk4TheDerivative[i]);
                            buildResult.append(formatX.format(valueX) + "   |    " + formatY.format(valueY) + "   |    " + format.format(value) + "\n");
                        }
                        String finalSolution = buildSolution.toString() + buildResult.toString();



                        endButton.setText("End");
                        endButton.setLayoutParams(params);
                        layout.addView(endButton, params);

                        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                        setContentView(writer);
                        writer.setScrollBy(20);
                        writer.setCharacterDelay(-3);
                        writer.animateText(finalSolution);
                        writer.scrollBy(0, 1);



                        //\tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                    }

                }

            }

        });

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);

        startActivityForResult(checkIntent,MY_DATA_CHECK_CODE);





    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == MY_DATA_CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                tts = new TextToSpeech(this,this);
            }else {
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);

            }
        }
    }

    public void onInit(int status){
        if(status == TextToSpeech.SUCCESS){
            Toast.makeText(RungeKuttaTutor.this, "Text-To-Speech Engine Initialized", Toast.LENGTH_LONG).show();

        }else if(status == TextToSpeech.ERROR){
            Toast.makeText(RungeKuttaTutor.this, "Error occurred while Text-To-Speech Engine is Initializing", Toast.LENGTH_LONG).show();
        }
    }


    public String substFunction(String[] function){
        String substitutedFunction = "";

        for(int i = 0; i < myRK4Content.length; i++){
            if(!(myRK4Content[i].equalsIgnoreCase("$"))){
                substitutedFunction = substitutedFunction + myRK4Content[i];
            }
        }

        return substitutedFunction;
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
        MenuItem mnu4 = menu.add(0, 3, 3, "Euler Method");
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
                switchView = new Intent(this,EulerMethod.class);
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
