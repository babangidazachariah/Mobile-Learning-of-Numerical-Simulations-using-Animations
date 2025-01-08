package numsim.justintimetech.com.numsim;

//importnumsim.justintimetech.com.numsim.EulerMethod;

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

//import numsim.justintimetech.com.numsim.R;
//        importnumsim.justintimetech.com.numsim.TypeWriter;

import java.text.DecimalFormat;

//importnumsim.justintimetech.com.numsim.Result;
//import android.widget.TextView;
//import android.widget.TextView;


public class EulerTutor extends Activity implements OnInitListener{

    public int MY_DATA_CHECK_CODE = 0;
    public TextToSpeech tts;
    public String text;
    public TypeWriter writer;
    int sPointer,pPointer, index, steps = 0;
    double initialX = 0, theDiff = 0;
    String[] theContent,myContent,stack,theX,theY, theDerivative; //theContent[] will be containing the function supplied by the user,
    String theFunction ="";
    double initialY = 0.0,stepSizeH = 0.0;
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

        setContentView(R.layout.eulertutor);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        final Button endButton = new Button(this);
        final android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        //---create a layout---
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);


        writer = new TypeWriter(this);


        Button cancelTutor = (Button) findViewById(R.id.btn_CancelTutor);
        cancelTutor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        Button startTutor = (Button) findViewById(R.id.btn_Start);

        startTutor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                EditText function = (EditText) findViewById(R.id.function);
                EditText initialx = (EditText) findViewById(R.id.initialX);
                EditText initialy = (EditText) findViewById(R.id.initialY);
                EditText stepsizeh = (EditText) findViewById(R.id.stepSize);
                EditText numberofstepsn = (EditText) findViewById(R.id.steps);
                boolean parseTheFunction = false, parseInitialY = false, parseInitialX = false, parseStepSize =false;

                try{
                    theFunction = function.getText().toString();
                    parseTheFunction = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of x. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerTutor.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    initialX = Double.parseDouble(initialx.getText().toString());
                    parseInitialX = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your function. \n It should be in the form: (f(x,y)) \n That is, (x-y).");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerTutor.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    initialY = Double.parseDouble(initialy.getText().toString());
                    parseInitialY = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of y. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerTutor.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    stepSizeH = Double.parseDouble(stepsizeh.getText().toString());
                    parseStepSize = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Step Size Value. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerTutor.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    numberOfStepsN = Integer.parseInt(numberofstepsn.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Number of Iterations. \n It should be an Integer in the form: 00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerTutor.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }


                String substitutedFunction = "";


                if(!(theFunction.length() > 2)){
                    //dialog box to tell the user that he has wrong input.
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerTutor.this);
                    noSelectionMade.setTitle("Problem With Input.");
                    noSelectionMade.setMessage("Input your Function in terms of the variables such as\n 'X' and 'Y'\n your arithmetic operators and any of \nthe Trignometric Function.");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerTutor.this, EulerTutor.class);
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
                    DecimalFormat formatX = new DecimalFormat("0.00000");
                    formatX.setMaximumFractionDigits(2);
                    DecimalFormat formatY = new DecimalFormat("0.00000");
                    formatY.setMaximumFractionDigits(3);
                    DecimalFormat format = new DecimalFormat("0.000000");
                    format.setMaximumFractionDigits(4);


                    StringBuilder buildSolution = new StringBuilder(); //to help build solution.
                    //start Solution
                    //StringBuilder buildSubSolution  = new StringBuilder();

                    //test the numberOfStepsN variable to see if it is not given, we assign five to it.
                    if(!(numberOfStepsN > 0 )){
                        numberOfStepsN = 3;
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


                    EulerMethod tutorUseEuler = new EulerMethod();
                    //Call and pass theFunction to the PostFix to create the post-fix expression.

                    theContent = tutorUseEuler.CreateArrayExpression(theFunction, theContent);
                    //ValidateInput(theContent);
                    boolean error = false;

                    for(int i = 0; i < theContent.length-1; i++){
                        if(!(theContent[i].equalsIgnoreCase("x")) && !(theContent[i].equalsIgnoreCase("y"))
                                &&  !(theContent[i].equalsIgnoreCase("-"))  && !(theContent[i].equalsIgnoreCase("+"))
                                && !(theContent[i].equalsIgnoreCase("*")) && !(theContent[i].equalsIgnoreCase("^"))
                                &&  !(theContent[i].equalsIgnoreCase("/")) &&  !(theContent[i].equalsIgnoreCase("sin"))
                                &&  !(theContent[i].equalsIgnoreCase("cos")) &&  !(theContent[i].equalsIgnoreCase("sinh"))
                                &&  !(theContent[i].equalsIgnoreCase("cosh"))  && !(theContent[i].equalsIgnoreCase("tan"))
                                &&  !(theContent[i].equalsIgnoreCase("tanh")) &&  !(theContent[i].equalsIgnoreCase("sec"))
                                &&  !(theContent[i].equalsIgnoreCase("sech")) && !(theContent[i].equalsIgnoreCase("cosec"))
                                && !(theContent[i].equalsIgnoreCase("cosech")) && !(tutorUseEuler.CheckForNumeric(theContent[i]))
                                && !(theContent[i].equalsIgnoreCase("(")) && !(theContent[i].equalsIgnoreCase(")")) && !(theContent[i].equalsIgnoreCase("$"))){

                            error = true;

                            break;
                        }else if(theContent[i].equalsIgnoreCase("$")){
                            error = false;

                            break;
                        }

                    }

                    if(error){
                        //dialog box to tell the user that he has wrong input.
                        AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerTutor.this);
                        noSelectionMade.setTitle("Problem With Input.");
                        noSelectionMade.setMessage("Check your Function for wrong input!\nYou can only use the variables such as\n 'X' and 'Y'\n your arithmetic operators and any of \nthe Trignometric Function.");

                        noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Intent intent = new Intent(EulerTutor.this, EulerTutor.class);
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




                        steps += 1;
                        buildSolution.append("Step " + steps + ": \n\n Our Function is: \n y' = " + theFunction + "\n The initial x is: \n x = " + initialX + " \n The initial y is: \n y = " + initialY + "\n  \n ");
                        myContent = new String[theContent.length];

                        myContent = tutorUseEuler.MakeCopy(theContent, myContent);

                        tutorUseEuler.SubstituteValues(myContent, initialX, initialY);

                        for(int i = 0; i < myContent.length;i++){
                            if(!(myContent[i].equalsIgnoreCase("$"))){
                                substitutedFunction = substitutedFunction + myContent[i];
                            }
                        }

                        theDiff = tutorUseEuler.MyEvaluation(myContent);

                        buildSolution.append("Therefore, Substituting " + initialX + " for x and " + initialY + " for y into the function, we  have y' = " + substitutedFunction + "\n Which evaluates to: \n y' = " + substitutedFunction + " = "+ theDiff + "\n\n");

                        while(steps <= (numberOfStepsN )){

                            //assign the values of initialX, initialY and theDiff to a three arrays.
                            theX[steps] = Double.toString(initialX);

                            theY[steps] = Double.toString(initialY);

                            theDerivative[steps] = Double.toString(theDiff);

                            //Create Copy of the user expression so as to enable repeated evaluation until
                            //the number of steps are satisfied. it is copied into myContent[] array.
                            //myContent = new String[theContent.length];
                            myContent = new String[theContent.length];
                            myContent = tutorUseEuler.MakeCopy(theContent, myContent);

                            //Substitute the values of x and y into the user expression to
                            // calculate the value of derivative, y'
                            //increment the value of initialX since the last step is completed.
                            double prevX = initialX;
                            initialX += stepSizeH;
                            initialX = Double.parseDouble(formatX.format(initialX));
                            initialY = tutorUseEuler.TheEuler(theDiff,initialY,stepSizeH);
                            initialY = Double.parseDouble(formatY.format(initialY));
                            double prevY = initialY;
                            tutorUseEuler.SubstituteValues(myContent, initialX, initialY);

                            substitutedFunction = "";	//initialize
                            for(int i = 0; i<myContent.length;i++){
                                if(!(myContent[i].equalsIgnoreCase("$"))){
                                    substitutedFunction = substitutedFunction + myContent[i];
                                }
                            }
                            String prevDiff = format.format(theDiff);
                            theDiff = tutorUseEuler.MyEvaluation(myContent);
                            theDiff = Double.parseDouble(format.format(theDiff));

                            buildSolution.append("\nStep " + (steps + 1) + ": \n  We now increment our x by the step size, h. as in \n x = x + h \n x = " + prevX + " + " + stepSizeH + "  .\n  Therefore, our current x is: \n x = " + initialX + "  .\n By Euler Method: \n y1 = y0 + h(y')  .\n Where y0 is the previously given or computed y and y1 is the current y to be computed. \n Therefore, substituting y0, y' and h into Euler Method, We have  \n y1 = " + prevY + " + (" + stepSizeH + " * " + prevDiff + ") .\n Which evaluates to y1 = " + initialY + "  .\n But our function is: \n y' = " + theFunction + " .\n and our current x is:\n x = " + initialX + " .\n and our current y is:\n y = " + initialY + "  .\n ");
                            buildSolution.append("Therefore, we solve y' by substituting the values " + initialX + " for x and " + initialY + " for y into the function; thus we now have: \n y' = " + substitutedFunction + " .\n Solving this Arithmetically, we have: \n");

                            buildSolution.append("y' = " + substitutedFunction +" = "+ theDiff);
                            buildSolution.append("\n So, we have \n x = " + prevX + ", y = " + prevY + " and y' = " + theDiff );

                            //increment the number of steps completed so as to help in testing for completion.
                            steps += 1;
                        }

                        buildSolution.append("\n\n Therefore, the tabulated solution is given below: \n\n" );

                        text = buildSolution.toString();

                        //iterate through the arrays using a FOR_NEXT loop.
                        StringBuilder buildResult = new StringBuilder(); //to help build result.



                        double valueX = 0, valueY = 0, value = 0;
                        buildResult.append("   " + theX[0] + "    |    " + "   " + theY[0] + "       |" + "     " + theDerivative[0] + "     " + "\n");

                        for(int i = 1; i < theX.length; i++){
                            valueX = Double.parseDouble(theX[i]);
                            valueY = Double.parseDouble(theY[i]);
                            value = Double.parseDouble(theDerivative[i]);
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

                        writer.animateText(text + finalSolution);
                        //tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
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
            Toast.makeText(EulerTutor.this, "Text-To-Speech Engine Initialized", Toast.LENGTH_SHORT).show();

        }else if(status == TextToSpeech.ERROR){
            Toast.makeText(EulerTutor.this, "Error occurred while Text-To-Speech Engine is Initializing", Toast.LENGTH_LONG).show();
        }
    }


    //create menu options
    private void CreateMenu(Menu menu)
    {
        MenuItem mnu1 = menu.add(0, 0, 0, "Euler-Cauchy Method");
        {
            mnu1.setAlphabeticShortcut('E');
            mnu1.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Euler-Cauchy Tutor");
        {
            mnu2.setAlphabeticShortcut('R');
            mnu2.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "Runge-Kutta Method");
        {
            mnu3.setAlphabeticShortcut('C');
            mnu3.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu4 = menu.add(0, 3, 3, "Runge-Kutta Tutor");
        {
            mnu4.setAlphabeticShortcut('K');
        }
        MenuItem mnu5 = menu.add(0, 4, 4, "Euler Method");
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
                switchView = new Intent(this,EulerModifiedMethod.class);
                startActivity(switchView);
                break;
            case 1:
                switchView = new Intent(this,EulerCauchyTutor.class);
                startActivity(switchView);
                break;
            case 2:
                switchView = new Intent(this,FourthRungeKuttaMethod.class);
                startActivity(switchView);
                break;
            case 3:
                switchView = new Intent(this,RungeKuttaTutor.class);
                startActivity(switchView);
                break;
            case 4:
                switchView = new Intent(this,EulerMethod.class);
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
