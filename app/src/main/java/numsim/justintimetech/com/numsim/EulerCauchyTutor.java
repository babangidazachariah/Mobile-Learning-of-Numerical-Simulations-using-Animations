package numsim.justintimetech.com.numsim;

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
//import numsim.justintimetech.com.numsim.TypeWriter;

import java.text.DecimalFormat;

public class EulerCauchyTutor extends Activity implements OnInitListener{

    public int MY_DATA_CHECK_CODE = 0;
    public TextToSpeech tts;
    public String text;
    public TypeWriter writer;
    int sPointer,pPointer, index, steps = 0;
    double initialX = 0, theDiff = 0;
    String[] theEulerCauchyContent,myEulerCauchyContent,eulerCauchyMyPostFix, eulerCauchyStack,eulerCauchyTheX,eulerCauchyTheY, eulerCauchyTheDerivative; //theContent[] will be containing the function supplied by the user,
    String theFunction = "";
    int numberOfStepsN = 0;
    double  initialY = 0, stepSizeH = 0;				//postFix[] will be containing the post-Fix expression of the supplied function.
    //stack[] will be containing the operators.
    //myPostFix[] will be containing the the copy of the postFix[] after it has been created.

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

        setContentView(R.layout.eulercauchytutor);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        final Button endButton = new Button(this);
        final android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        //---create a layout---
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        writer = new TypeWriter(this);

        Button startEulerCachyTutor = (Button) findViewById(R.id.btn_EulerCauchyComputeTutor);
        startEulerCachyTutor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                boolean parseFunction= false, parseInitialX= false, parseInitialY= false,parseStepSize= false,parseSteps = false; //variable to determnif all input were correctly input

                // TODO Auto-generated method stub
                EditText tutorFunction = (EditText) findViewById(R.id.EulerCauchyfunction);
                EditText initialx = (EditText) findViewById(R.id.EulerCauchyinitialX);
                EditText initialy = (EditText) findViewById(R.id.EulerCauchyinitialY);
                EditText stepsizeh = (EditText) findViewById(R.id.EulerCauchystepSize);
                EditText numberofstepsn = (EditText) findViewById(R.id.EulerCauchysteps);

                try{
                    theFunction = tutorFunction.getText().toString();
                    parseFunction = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerCauchyTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of x. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerCauchyTutor.this, EulerCauchyTutor.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    initialX = Double.parseDouble(initialx.getText().toString());
                    parseInitialX = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerCauchyTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your function. \n It should be in the form: (f(x,y)) \n That is, (x-y).");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerCauchyTutor.this, EulerCauchyTutor.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    initialY = Double.parseDouble(initialy.getText().toString());
                    parseInitialY= true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerCauchyTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of y. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerCauchyTutor.this, EulerCauchyTutor.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    stepSizeH = Double.parseDouble(stepsizeh.getText().toString());
                    parseStepSize = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerCauchyTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Step Size Value. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerCauchyTutor.this, EulerCauchyTutor.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    numberOfStepsN = Integer.parseInt(numberofstepsn.getText().toString());
                    parseSteps = true;
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerCauchyTutor.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Number of Iterations. \n It should be an Integer in the form: 00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerCauchyTutor.this, EulerCauchyTutor.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }

                if(!(theFunction.length() > 2)){
                    //dialog box to tell the user that he has wrong input.
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerCauchyTutor.this);
                    noSelectionMade.setTitle("Problem With Input.");
                    noSelectionMade.setMessage("Input your Function in terms of the variables such as\n 'X' and 'Y'\n your arithmetic operators and any of \nthe Trignometric Function.");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerCauchyTutor.this, EulerCauchyTutor.class);
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

                }else if((parseFunction == true && parseInitialX == true && parseInitialY == true && parseStepSize ==true && parseSteps == true)) {
                    //test the numberOfStepsN variable to see if it is not given, we assign five to it.
                    if(!(numberOfStepsN > 0 )){
                        numberOfStepsN = 3;
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
                    EulerModifiedMethod useEulerCachy = new EulerModifiedMethod();

                    String substitutedFunction = "";



                    DecimalFormat formatX = new DecimalFormat("0.00");
                    formatX.setMaximumFractionDigits(2);
                    DecimalFormat formatY = new DecimalFormat("0.000");
                    formatY.setMaximumFractionDigits(3);
                    DecimalFormat format = new DecimalFormat("0.0000");
                    format.setMaximumFractionDigits(4);


                    StringBuilder buildSolution = new StringBuilder(); //to help build solution.
                    //start Solution

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
                        AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerCauchyTutor.this);
                        noSelectionMade.setTitle("Problem With Input.");
                        noSelectionMade.setMessage("Check your Function for wrong input!\nYou can only use the variables such as\n 'X' and 'Y'\n your arithmetic operators and any of \nthe Trignometric Function.");

                        noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Intent intent = new Intent(EulerCauchyTutor.this, EulerCauchyTutor.class);
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
                        for(int i = 0; i<myEulerCauchyContent.length; i++){
                            if(!(myEulerCauchyContent[i].equalsIgnoreCase("$"))){
                                substitutedFunction += myEulerCauchyContent[i];
                            }
                        }
                        //evaluate the user expression
                        theDiff = useEuler.MyEvaluation(myEulerCauchyContent);
                        theDiff = Double.parseDouble(format.format(theDiff));

                        buildSolution.append("Our function is: \n y' = " + theFunction + " ; \nThe Initial value of x is: \n x = " + initialX + " ;\nInitial value of y is: \n y = "+ initialY + ".\n");

                        //assign the values of initialX, initialY and theDiff to a three arrays.
                        steps += 1;
                        eulerCauchyTheX[steps] = Double.toString(initialX);

                        eulerCauchyTheY[steps] = Double.toString(initialY);

                        eulerCauchyTheDerivative[steps] = Double.toString(theDiff);

                        steps += 1;
                        while(steps <= numberOfStepsN + 1){
                            double prevX = initialX;
                            initialX += stepSizeH;
                            buildSolution.append("Therefore, substituting the value of x and that of y into the function, we have: \n y' = " + substitutedFunction + "; \nWhich evaluates to: \n y' = " + theDiff + ". \n");
                            buildSolution.append("To calculate our Predicted Value, we increment the value of x by the step size h as in: \n x = x + h. \n Thus, we have x = " + prevX + " + " + stepSizeH + "; \n Which evaluates to: \n x = "+ initialX + ". \n");

                            double yP1 = useEuler.TheEuler(theDiff, initialY, stepSizeH);
                            yP1 = Double.parseDouble(formatY.format(yP1));

                            useEuler.MakeCopy(theEulerCauchyContent, myEulerCauchyContent);

                            useEuler.SubstituteValues(myEulerCauchyContent, initialX, yP1);


                            double resultingFunction = useEuler.MyEvaluation(myEulerCauchyContent);
                            resultingFunction = Double.parseDouble(formatY.format(resultingFunction));

                            substitutedFunction = "";	//initialize
                            for(int i = 0; i<myEulerCauchyContent.length; i++){
                                if(!(myEulerCauchyContent[i].equalsIgnoreCase("$"))){
                                    substitutedFunction += myEulerCauchyContent[i];
                                }
                            }
                            buildSolution.append("Our Corrector value,yc is given as \n yc = y + 1/2[h(y' + f(x,yp))]; \n Where our f(x,yp) is the given function with  y = yp as in: \n f(x,y) = " + theFunction + ". \n Substituting the value of x = " + initialX + " and the value of yp = y = " + yP1 + "; We have: \n");
                            buildSolution.append("yc = " +initialY + " + 1/2[" + stepSizeH + "(" + theDiff + " + " +substitutedFunction + ")]; \n Which evaluates to:\n yp = " + resultingFunction + ". \n" );

                            double yC1 = useEulerCachy.theEulerModified(initialY,stepSizeH,theDiff,resultingFunction);
                            yC1 = Double.parseDouble(formatY.format(yC1));
                            buildSolution.append("Our Predictor value yp is given by \n yp = y + h(y'); \n Where y = " + initialY + " and y' = " + theDiff + "; \n therefore, we have \n yp = " + initialY + " + " + stepSizeH + "(" + theDiff + "); \n which evaluate to \n yp = " + yP1 + "    .\n");
                            initialY = yC1;


                            theDiff = yC1 - initialX;
                            theDiff = Double.parseDouble(format.format(theDiff));
                            buildSolution.append("To obtain the value of the derivative y', we subtract the value of x from the corrector value, yc \n that is, \n y' = " + yC1 + " - " + initialX + " = " + theDiff + "   .\n");
                            //assign the values of initialX, initialY and theDiff to a three arrays.
                            eulerCauchyTheX[steps] = Double.toString(initialX);


                            eulerCauchyTheY[steps] = Double.toString(initialY);

                            eulerCauchyTheDerivative[steps] = Double.toString(theDiff);
                            buildSolution.append("Therefore, our initial values of x, y and y' are: \n x = " + formatX.format(initialX) + "; \n y = " + formatY.format(initialY) + "; and \n y' = " + theDiff + "   .\n");

                            steps += 1;

                        }

                        buildSolution.append("\n\n Therefore, the tabulated solution is given below: \n\n" );

                        text = buildSolution.toString();

                        //iterate through the arrays using a FOR_NEXT loop.
                        StringBuilder buildResult = new StringBuilder(); //to help build result.



                        double valueX = 0, valueY = 0, value = 0;
                        buildResult.append("   " + eulerCauchyTheX[0] + "    |    " + "   " + eulerCauchyTheY[0] + "       |" + "     " + eulerCauchyTheDerivative[0] + "     " + "\n");

                        for(int i = 1; i < eulerCauchyTheX.length; i++){
                            valueX = Double.parseDouble(eulerCauchyTheX[i]);
                            valueY = Double.parseDouble(eulerCauchyTheY[i]);
                            value = Double.parseDouble(eulerCauchyTheDerivative[i]);
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



                        //tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

                    }

                }

            }//end of if( parseFunction....){


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
            Toast.makeText(EulerCauchyTutor.this, "Text-To-Speech Engine Initialized", Toast.LENGTH_LONG).show();

        }else if(status == TextToSpeech.ERROR){
            Toast.makeText(EulerCauchyTutor.this, "Error occurred while Text-To-Speech Engine is Initializing", Toast.LENGTH_LONG).show();
        }
    }

    //create menu options
    private void CreateMenu(Menu menu)
    {
        MenuItem mnu1 = menu.add(0, 0, 0, "Euler Method");
        {
            mnu1.setAlphabeticShortcut('E');
            mnu1.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Euler Method Tutor");
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
        MenuItem mnu5 = menu.add(0, 4, 4, "Euler-Cauchy Tutor");
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
