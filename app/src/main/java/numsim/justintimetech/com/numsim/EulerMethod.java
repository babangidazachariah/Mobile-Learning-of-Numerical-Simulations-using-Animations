package numsim.justintimetech.com.numsim;

//import java.lang.reflect.Array;
//import java.text.NumberFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.StringTokenizer;

public class EulerMethod extends Activity{

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
        setContentView(R.layout.eulermethod);


        Button btnBack = (Button) findViewById(R.id.btn_Back);

        btnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });


        Button btnCompute = (Button) findViewById(R.id.btn_Compute);

        btnCompute.setOnClickListener(new OnClickListener(){

            public void onClick(View view){

                EditText function = (EditText) findViewById(R.id.function);
                EditText initialx = (EditText) findViewById(R.id.initialX);
                EditText initialy = (EditText) findViewById(R.id.initialY);
                EditText stepsizeh = (EditText) findViewById(R.id.stepSize);
                EditText numberofstepsn = (EditText) findViewById(R.id.steps);


                try{
                    theFunction = function.getText().toString();
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of x. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    initialX = Double.parseDouble(initialx.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your function. \n It should be in the form: (f(x,y)) \n That is, (x-y).");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    initialY = Double.parseDouble(initialy.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Initial Value of y. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    stepSizeH = Double.parseDouble(stepsizeh.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Step Size Value. \n It should be a Real Value in the form: 0.00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerMethod.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();
                }
                try{
                    numberOfStepsN = Integer.parseInt(numberofstepsn.getText().toString());
                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerMethod.this);
                    noSelectionMade.setTitle("Input Required.");
                    noSelectionMade.setMessage("Check your Number of Iterations. \n It should be an Integer in the form: 00");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerMethod.this, SearchActivity.class);
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
                steps = 0;
                theX = new String[numberOfStepsN + 1];
                theY = new String[numberOfStepsN + 1];
                theDerivative = new String[numberOfStepsN + 1];
                //initialize the three arrays theX[] theY[] theDerivative[].
                theX[steps] = "x";
                theY[steps] = "y";
                theDerivative[steps] = "y'";

                //Call and pass theFunction to the PostFix to create the post-fix expression.

                theContent = CreateArrayExpression(theFunction, theContent);
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
                            && !(theContent[i].equalsIgnoreCase("cosech")) && !(CheckForNumeric(theContent[i]))
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
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(EulerMethod.this);
                    noSelectionMade.setTitle("Problem With Input.");
                    noSelectionMade.setMessage("Check your Function for wrong input!\nYou can only use the variables such as\n 'X' and 'Y'\n your arithmetic operators and any of \nthe Trignometric Function.");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(EulerMethod.this, EulerMethod.class);
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

                    while(steps <= numberOfStepsN){


                        //Create Copy of the user expression so as to enable repeated evaluation until
                        //the number of steps are satisfied. it is copied into myContent[] array.
                        myContent = new String[theContent.length];
                        myContent = MakeCopy(theContent, myContent);


                        //Substitute the values of x and y into the user expression to
                        // calculate the value of derivative, y'

                        SubstituteValues(myContent, initialX, initialY);


                        //determine the derivative, y' using the MyEvaluation().
                        theDiff = MyEvaluation(myContent);

                        //assign the values of initialX, initialY and theDiff to a three arrays.
                        theX[steps] = Double.toString(initialX);

                        theY[steps] = Double.toString(initialY);

                        theDerivative[steps] = Double.toString(theDiff);

                        //evaluate the next value of initialY
                        initialY = TheEuler(theDiff,initialY,stepSizeH);

                        //increment the value of initialX since the last step is completed.
                        initialX += stepSizeH;

                        //clear the content of myPostFix array so as to re-copy the content of postFix array into it.

                        //increment the number of steps completed so as to help in testing for completion.
                        steps += 1;

                    }
                    Intent sendResult = new Intent(EulerMethod.this,Result.class);

                    Bundle send = new Bundle();

                    //pass the three arrays to the activity Result.

                    send.putStringArray("theX", theX);
                    send.putStringArray("theY", theY);
                    send.putStringArray("theDerivative", theDerivative);

                    sendResult.putExtras(send);

                    //start the Result Activity.
                    startActivity(sendResult);

                }
            }
        });


    }

    public String[] MakeCopy(String[] postfix, String[] mypostfix){  //the procedure to duplicate the postfix expression.



        int j;
        j = postfix.length;//get the length of the created post-fix expression so as to declare the myPostFix[] array.


        //start copying
        for(int i = 0; i< j; i++ ){

            //if((postfix[i] != null) && (i >= 0) && (!(postfix[i]).equalsIgnoreCase("$"))){ //check for NULL values since 5 was added in the declaration of poxtFix[] in line 185.

            mypostfix[i] = postfix[i];
            //}
        }


        return mypostfix;

    }


    public double TheEuler(double diffY, double y, double h){	// this method calculate the value of Yn.
        //pass the differential of y, diffY (y'or f'(x) to calculate nth y i.e Yn.
        //where n = {1,2,3,......}; the step size is h, while y is the previous value 0f y

        double Yn = 0;

        Yn = y + h * diffY;

        return Yn;
    }

    public void SubstituteValues(String[] post, double x, double y){
        //method to substitute all the values of x and y in the created post-fix expression before it
        // it is evaluated.


        for(int i = 0; i < post.length - 1; i++){

            if(( post[i] != null) || !(post[i]).equalsIgnoreCase("$")){

                if((post[i]).equalsIgnoreCase("x")){ //x or X in case when the user enters either of the two.

                    post[i] = Double.toString(x);	//convert the double value to a string.

                }else if((post[i]).equalsIgnoreCase("y")){

                    post[i] = Double.toString(y);	//convert the double value to a string.
                }
            }
        }
    }





    public boolean CheckForNumeric(String x){
        //to check if the string x is numeric or not
        boolean isTrue = false;
        //int val = 0;
        //double val1 = 0;
		 	/*for(int i = 0; i < x.length(); i++){
		 		if(!Character.isDigit(i)){
		 			isTrue = true;
		 		}
		 	}*/
        try{
            Integer.parseInt(x);
            Double.parseDouble(x);
            isTrue = true;

        } catch (Exception e){
            isTrue = false;
        }


        return isTrue;
    }

    public String[] SubstituteTrignometry(String[] trig, int j, int n, String theTrig){
        //to substitute for cosec, sec and cot

        int clLoc = 0, op = 0, cl = 0, m = j + 1;
        while(m < trig.length){ //Search for sentinel and where to insert "(" and ")"
            if(trig[m].equalsIgnoreCase("(")){
                op += 1;

            }else if(trig[m].equalsIgnoreCase(")")){
                cl += 1;
                clLoc = m;
            }
            if(cl == op){
                //move the element so as to insert the ") )" to close the bracket of ( 1 / ( theTrig...
                for(int l = trig.length - 3; l > clLoc;l--){
                    trig[l + 2] = trig[l];
                }
                trig[clLoc + 1] = ")";
                trig[clLoc + 2] = ")";
                m = trig.length;	// so as to exit the loop.
            }

            m += 1;
        }
        n += 2;
        for( int k = trig.length - 1; k > j ; k--){
            trig[k] = trig[k - 4] ;
        }
        trig[j] = "(";
        trig[j+1] = "1";
        trig[j+2] = "/";
        trig[j+3] = "(";
        trig[j+4] = theTrig;



        return trig;
    }

    public String[] CreateArrayExpression(String function, String[] theFunction){
        StringTokenizer token = new StringTokenizer(function,"+-*/()^XYxy",true);


        int index = token.countTokens();

        theFunction = new String[index * 2 +((int) index/2)]; //to accommodate the worst possible function.

        int i = 0;
        while(token.hasMoreTokens()){ //transform the function in token form into an array.

            theFunction[i] =  token.nextToken();

            i += 1;
        }
        //place sentinel, $ to the remaining locations so as to avoid nulls
        //keep tract of location where first sentinel, $ occurs.
        int n = i;
        while(i < theFunction.length){
            theFunction[i] = "$";
            i += 1;
        }


        int j = 0;
	        /*sec x = 1/ cos x
	         * cot x = 1/ tan x
	         * cosec x = 1/sin x*/
        while( j < theFunction.length - 1){

            //keep tract of location of
            if(theFunction[j].equalsIgnoreCase("sec")){

                theFunction = SubstituteTrignometry(theFunction,j,n, "cos");
                j += 1;

            }else if(theFunction[j].equalsIgnoreCase("cot")){

                theFunction = SubstituteTrignometry(theFunction,j,n, "tan");
                j += 1;

            }else if(theFunction[j].equalsIgnoreCase("cosec")){

                theFunction = SubstituteTrignometry(theFunction,j,n, "sin");
                j += 1;

            }else if((!(theFunction[j].equalsIgnoreCase("$"))) && (!(theFunction[j + 1].equalsIgnoreCase("$")) ) && (!(theFunction[j].equalsIgnoreCase("-"))) && (!(theFunction[j].equalsIgnoreCase("+"))) && (!(theFunction[j].equalsIgnoreCase("*"))) && (!(theFunction[j].equalsIgnoreCase("/"))) && (!(theFunction[j].equalsIgnoreCase("^")))  && (!(theFunction[j + 1].equalsIgnoreCase("+")) ) && (!(theFunction[j + 1].equalsIgnoreCase("-")) ) ) {

                if( (!(theFunction[j].equalsIgnoreCase("(")) ) && (!(theFunction[j].equalsIgnoreCase(")")) ) && (!(theFunction[j + 1].equalsIgnoreCase(")")) ) ){


                    if((((theFunction[j].equalsIgnoreCase("x")) || theFunction[j].equalsIgnoreCase("y")) || CheckForNumeric(theFunction[j])) && (((theFunction[j+1].equalsIgnoreCase("y")) || (theFunction[j+1].equalsIgnoreCase("x"))) || CheckForNumeric(theFunction[j+1]) || (theFunction[j + 1].equalsIgnoreCase("(")))){

                        n = theFunction.length - 1;;

                        while(n > (j + 1) ){

                            theFunction[n] = theFunction[n - 1];
                            n -= 1;

                        }
                        theFunction[j + 1] = "*";
                        j += 2;
                        //test if the locations after two elements are the sentinel, $ the increment j so as to exit the loop.
                        if((theFunction[j + 2].equalsIgnoreCase("$")) && (theFunction[j + 3].equalsIgnoreCase("$") )){
                            j = theFunction.length;
                        }

                    }else{
                        j += 1;	//check here you just added this.
                    }
                } else {
                    j += 1;
                }


            }else {
                j += 1;
            }

        }



        return theFunction;
    }

    public double MyEvaluation(String[] x) {
        //run through the array expression to validate number of opened amd closed brackets
        int open = 0, close = 0;
        double operand = 0, result =0;
        for(int i = 0; i < x.length; i++){
            if(x[i].equalsIgnoreCase("(")){
                open += 1;
            }else if(x[i].equalsIgnoreCase(")")){
                close += 1;
            }else if(x[i].equalsIgnoreCase("e")){
                x[i] = Double.toString(Math.E); //replace the e (exponential) in the expression with its equivalent.
            }else if(x[i].equalsIgnoreCase("-")){
                //CHECH IF THE ELEMENT LEFT TO THIS LOCATION IS AN OPEN PARENTHESIS, WHICH IMPLIES THAT THE USER MEANS A NEGATIVE VALUE.
                if((!(x[i + 1].equalsIgnoreCase("("))) && (!(x[i + 1].equalsIgnoreCase("cos"))) && (!(x[i + 1].equalsIgnoreCase("sin"))) && (!(x[i + 1].equalsIgnoreCase("cosh"))) && (!(x[i + 1].equalsIgnoreCase("sinh"))) && (!(x[i + 1].equalsIgnoreCase("tan"))) && (!(x[i + 1].equalsIgnoreCase("tanh")))){
                    //merge the negative to its value, right to it.
                    x[i + 1] =  x[i] + x[i + 1];
                    x[i] = "+";
                }
            }
        }
        //move elements in case of any sentinel in between the expression
        x = MoveElement(x,x);



        if(open == close){
            int openLoc = 0, closeLoc = 0;

            boolean thereIsShift = true; //even though there is no shift initiallly, there may be subsequently.
            while(thereIsShift) {
                int j = x.length- 1;
                while( j >= 0) {

                    if(x[j].equalsIgnoreCase(")")){
                        closeLoc = j;
                    }else if(x[j].equalsIgnoreCase("(")){


                        openLoc = j;
                        TheEvaluation(x,openLoc,closeLoc);
                        //insert sentinel at the locations of open an close parenthesis.
                        x[openLoc] = "$";
                        x[closeLoc] = "$";

                        x = MoveElement(x,x);

                        //check the next element left to this location of the opened bracket
                        //to ensure its not a trignometric function.
                        if(j > 1) {
                            if((x[j - 1].equalsIgnoreCase("sin"))) {
                                operand = Double.parseDouble(x[j]);
                                x[j] = "$";

                                result = Math.sin(Math.toRadians(operand));
                                x[j - 1] = Double.toString(result);

                            }
                            if((x[j - 1].equalsIgnoreCase("sinh"))){
                                operand = Double.parseDouble(x[j]);
                                x[j] = "$";

                                result = Math.sinh(Math.toRadians(operand));
                                x[j - 1] = Double.toString(result);

                            }
                            if((x[j - 1].equalsIgnoreCase("cos"))){
                                operand = Double.parseDouble(x[j]);
                                x[j] = "$";

                                result = Math.cos(Math.toRadians(operand));
                                x[j - 1] = Double.toString(result);

                            }
                            if((x[j - 1].equalsIgnoreCase("cosh"))){
                                operand = Double.parseDouble(x[j]);
                                x[j] = "$";

                                result = Math.cosh(Math.toRadians(operand));
                                x[j - 1] = Double.toString(result);

                            }
                            if((x[j - 1].equalsIgnoreCase("tan"))){
                                operand = Double.parseDouble(x[j]);
                                x[j] = "$";

                                result = Math.tan(Math.toRadians(operand));
                                x[j - 1] = Double.toString(result);

                            }
                            if((x[j - 1].equalsIgnoreCase("tanh"))) {
                                operand = Double.parseDouble(x[j]);
                                x[j] = "$";

                                result = Math.tanh(Math.toRadians(operand));
                                x[j - 1] = Double.toString(result);

                            }
                            if((x[j - 1].equalsIgnoreCase("log"))) {
                                operand = Double.parseDouble(x[j]);
                                x[j] = "$";

                                result = Math.log10(operand);
                                x[j - 1] = Double.toString(result);

                            }

                        }

                        j = -1;     //so as to exit the inner loop, start scanning all over again.

                        thereIsShift = true;
                    }
                    j -= 1;
                    thereIsShift = false;



                }
                if(x[1].equalsIgnoreCase("$")){
                    result = Double.parseDouble(x[0]);

                }else {
                    thereIsShift = true;
                }
            }
        }else {
            //System.out.println("check expression for brackets");
        }
        return result;

    }



    public static void TheEvaluation(String[] x, int op, int cl){


        double result =0, opera1 = 0, opera2 = 0;
        int n = 0, numOfElements = cl - op;
        if(numOfElements > 2) {   //in cases where the parenthesis contains one element such as (23). if numOfElements > 1 it means an there is an expression, so we evalute the expression.
            int loc = 0;
            boolean operator = true;
            while(operator)  { //checks if an operation is performed. if performed the value is false.
                n = cl;

                while(n > op){


                    if(x[n].equalsIgnoreCase("^")) {
                        loc = n;
                        int j = loc - 1;
                        while(j > op){    //SEARCH THROUGH, IN BETWEEN THE BRACKET FOR THE LOCACTION OF AN OPERAND.

                            if(!(x[j].equalsIgnoreCase("$"))){
                                opera1 = Double.parseDouble(x[j]);
                                x[j] = "$";
                                j = op;
                            }else{
                                j -= 1;
                            }

                        }
                        j = loc + 1;
                        while(j < cl){    //SEARCH THROUGH, IN BETWEEN THE BRACKET FOR THE LOCACTION OF AN OPERAND.
                            if(!(x[j].equalsIgnoreCase("$"))){
                                opera2 = Double.parseDouble(x[j]);
                                x[j] = "$";
                                j = cl;
                            }else {
                                j += 1;
                            }
                        }
                        //INSERT THE A SENTINEL AT  LOCATION OF THE OPERATOR AND THE SECOND OPERAND.
                        x[loc] = "$";
                        x[loc + 1] = "$";
                        result = Math.pow(opera1, opera2);

                        x[loc - 1] = Double.toString(result);
                        ConcatNegative(x,op,cl); //check if there is a negative value such as 'x - y' so as to make it 'x + (-y)' before evaluating.
                        n = op;     //if an operation is performed, we set the value of n = cl to exit the loop
                        operator = true;
                    }else {
                        operator = false;
                    }

                    n -= 1;
                }
            }

            operator = true;
            while(operator)  { //checks if an operation is performed. if performed the value is false.
                n = cl;
                while(n > op){
                    if(x[n].equalsIgnoreCase("*")) {
                        loc = n;
                        int j = loc - 1;
                        while(j > op){    //SEARCH THROUGH, IN BETWEEN THE BRACKET FOR THE LOCACTION OF AN OPERAND.

                            if(!(x[j].equalsIgnoreCase("$"))){
                                opera1 = Double.parseDouble(x[j]);
                                x[j] = "$";
                                j = op;
                            }else {
                                j -= 1;
                            }

                        }
                        j = loc + 1;
                        while(j < cl){    //SEARCH THROUGH, IN BETWEEN THE BRACKET FOR THE LOCACTION OF AN OPERAND.

                            if(!(x[j].equalsIgnoreCase("$"))){

                                opera2 = Double.parseDouble(x[j]);
                                x[j] = "$";
                                j = cl;
                            }else {
                                j += 1;
                            }

                        }
                        //INSERT THE A SENTINEL AT  LOCATION OF THE OPERATOR AND THE SECOND OPERAND.
                        x[loc] = "$";
                        x[loc + 1] = "$";
                        result = opera1 * opera2;

                        x[loc - 1] = Double.toString(result);
                        ConcatNegative(x,op,cl); //check if there is a negative value such as 'x - y' so as to make it 'x + (-y)' before evaluating.
                        n = op;     //if an operation is performed, we set the value of n = cl to exit the loop
                        operator = true;
                    }else {
                        operator = false;
                    }

                    n -= 1;
                }
            }
            operator = true;
            while(operator)  { //checks if an operation is performed. if performed the value is false.
                n = cl;
                while(n > op){

                    if(x[n].equalsIgnoreCase("/")) {
                        loc = n;
                        int j = loc - 1;
                        while(j > op){    //SEARCH THROUGH, IN BETWEEN THE BRACKET FOR THE LOCACTION OF AN OPERAND.

                            if(!(x[j].equalsIgnoreCase("$"))){
                                opera1 = Double.parseDouble(x[j]);
                                x[j] = "$";
                                j = op;
                            }else {
                                j -= 1;
                            }
                        }

                        j = loc + 1;
                        while(j < cl){    //SEARCH THROUGH, IN BETWEEN THE BRACKET FOR THE LOCACTION OF AN OPERAND.


                            if(!(x[j].equalsIgnoreCase("$"))){
                                opera2 = Double.parseDouble(x[j]);
                                x[j] = "$";
                                j = cl;
                            }else {
                                j += 1;
                            }

                        }
                        //INSERT THE A SENTINEL AT  LOCATION OF THE OPERATOR AND THE SECOND OPERAND.
                        x[loc] = "$";
                        x[loc + 1] = "$";
                        result = opera1 / opera2;

                        x[loc - 1] = Double.toString(result);
                        ConcatNegative(x,op,cl); //check if there is a negative value such as 'x - y' so as to make it 'x + (-y)' before evaluating.
                        n = op;     //if an operation is performed, we set the value of n = cl to exit the loop
                        operator = true;

                    }else {
                        operator = false;
                    }

                    n -= 1;
                }
            }

            operator = true;
            while(operator)  { //checks if an operation is performed. if performed the value is false.
                n = cl;
                while(n > op){
                    if(x[n].equalsIgnoreCase("-")) {
                        loc = n;
                        int j = loc - 1;
                        while(j > op){    //SEARCH THROUGH, IN BETWEEN THE BRACKET FOR THE LOCACTION OF AN OPERAND.
                            if(!(x[j].equalsIgnoreCase("$"))){
                                opera1 = Double.parseDouble(x[j]);
                                x[j] = "$";
                                j = op;
                            }else {
                                j -= 1;
                            }
                        }
                        j = loc + 1;
                        while(j < cl){    //SEARCH THROUGH, IN BETWEEN THE BRACKET FOR THE LOCACTION OF AN OPERAND.
                            if(!(x[j].equalsIgnoreCase("$"))){
                                opera2 = Double.parseDouble(x[j]);
                                x[j] = "$";
                                j = cl;
                            }else {
                                j += 1;
                            }
                        }
                        //INSERT THE A SENTINEL AT  LOCATION OF THE OPERATOR AND THE SECOND OPERAND.
                        x[loc] = "$";
                        x[loc + 1] = "$";
                        result = opera1 - opera2;

                        x[loc - 1] = Double.toString(result);
                        ConcatNegative(x,op,cl); //check if there is a negative value such as 'x - y' so as to make it 'x + (-y)' before evaluating.

                        n = cl;     //if an operation is performed, we set the value of n = cl to exit the loop
                        operator = true;
                    }else {
                        operator = false;
                    }

                    n -= 1;
                }
            }


            operator = true;
            while(operator)  { //checks if an operation is performed. if performed the value is false.
                n = cl;
                while(n > op){

                    if(x[n].equalsIgnoreCase("+")) {
                        loc = n;
                        int j = loc - 1;
                        while(j > op){    //SEARCH THROUGH, IN BETWEEN THE BRACKET FOR THE LOCACTION OF AN OPERAND.
                            if(!(x[j].equalsIgnoreCase("$"))){
                                opera1 = Double.parseDouble(x[j]);
                                x[j] = "$";
                                j = op;
                            }else {
                                j -= 1;
                            }
                        }

                        j = loc + 1;
                        while(j < cl){    //SEARCH THROUGH, IN BETWEEN THE BRACKET FOR THE LOCACTION OF AN OPERAND.
                            if(!(x[j].equalsIgnoreCase("$"))){
                                opera2 = Double.parseDouble(x[j]);
                                x[j] = "$";
                                j = cl;
                            }else {
                                j += 1;
                            }
                        }
                        //INSERT THE A SENTINEL AT  LOCATION OF THE OPERATOR AND THE SECOND OPERAND.
                        x[loc] = "$";
                        x[loc + 1] = "$";
                        result = opera1 + opera2;

                        x[loc - 1] = Double.toString(result);
                        ConcatNegative(x,op,cl); //check if there is a negative value such as 'x - y' so as to make it 'x + (-y)' before evaluating.
                        n = op;     //if an operation is performed, we set the value of n = cl to exit the loop
                        operator = true;


                    }else {
                        operator = false;
                    }

                    n -= 1;
                }
            }


        }


    }

    public static String[] MoveElement(String[] x,String[] y){
        int j = 0,  i = 0;

        while(i < x.length){
            if(!(x[i].equalsIgnoreCase("$"))){
                y[j]  = x[i];
                j += 1;
            }
            i += 1;
        }
        for(int k = j; k < y.length; k++){
            y[k] = "$";
        }


        return y;

    }
    public static void ConcatNegative(String[]x, int open, int close){
        double operand1 = 0, operand2 = 0;
        for(int i = open + 1; i < close; i++){

            if(((x[i].equalsIgnoreCase("-"))) && (!(x[i + 1].equalsIgnoreCase("("))) && (!(x[i + 1].equalsIgnoreCase("sin"))) && (!(x[i + 1].equalsIgnoreCase("sinh"))) && (!(x[i+1].equalsIgnoreCase("cos"))) && (!(x[i+1].equalsIgnoreCase("cosh"))) && (!(x[i+1].equalsIgnoreCase("tan"))) && (!(x[i+1].equalsIgnoreCase("tanh")))){
                //x[i] =  x[i] + "1";
                operand1 = Double.parseDouble(x[i] + "1");  //concatenate negative- Minus with a one.
                operand2 = Double.parseDouble(x[i + 1]);
                x[i + 1] = Double.toString(operand1 * operand2);    //multiply -1 and the value after it.
                x[i] = "+";    //insert an addition symbol to replace the minus.


            }
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
