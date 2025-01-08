package numsim.justintimetech.com.numsim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SimsonOne extends Activity{

    int sPointer,pPointer, index;

    String[] simsonContent,mySimsonContent,simsonArray,simsonTheX,simsonTheY; //theContent[] will be containing the function supplied by the user,

    double theUpperBound,theLowerBound;
    int numberOfStepsN = 0,intervals;

    String theFunction = "",xCoordinates,yCoordinates;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	        /*
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
			}*/
        setContentView(R.layout.simsonone);
        RadioButton coordinate = (RadioButton)findViewById(R.id.coordinates);
        RadioButton function = (RadioButton)findViewById(R.id.functions);
	        /*  
	        coordinate.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					setVisiblityForCoordinate();
				}
			});
	        function.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					setVisiblityForFunction();
				}
			});*/

        Button btnEulerModifiedCompute = (Button) findViewById(R.id.btn_EulerModifiedCompute);

        btnEulerModifiedCompute.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                double valueIs = 0.0,sumEven = 0 ,sumOdd  = 0,first = 0,last = 0;


                RadioButton coordinate = (RadioButton)findViewById(R.id.coordinates);
                RadioButton function = (RadioButton)findViewById(R.id.functions);
                EditText xCoordinate = (EditText)findViewById(R.id.xcoordinate);
                EditText yCoordinate = (EditText)findViewById(R.id.ycoordinate);
                EditText thefunction = (EditText)findViewById(R.id.function);
                EditText upperBound = (EditText)findViewById(R.id.upperBound);
                EditText lowerBound = (EditText)findViewById(R.id.lowerBound);
                EditText inte = (EditText)findViewById(R.id.interv);

                if (coordinate.getText().toString().equalsIgnoreCase("Coordinates")) {

                    try{
                        xCoordinates = xCoordinate.getText().toString();
                    }catch(Exception e){
                        AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(SimsonOne.this);
                        noSelectionMade.setTitle("Input Required.");
                        noSelectionMade.setMessage("Check your Initial Value of x. \n It should be a Real Value in the form: 0.00");

                        noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Intent intent = new Intent(SimsonOne.this, SearchActivity.class);
                                startActivity(intent);
                            }
                        });
                        noSelectionMade.show();

                    }
                    try{
                        yCoordinates = yCoordinate.getText().toString();
                    }catch(Exception e){
                        AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(SimsonOne.this);
                        noSelectionMade.setTitle("Input Required.");
                        noSelectionMade.setMessage("Check your Initial Value of y. \n It should be a Real Value in the form: 0.00");

                        noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Intent intent = new Intent(SimsonOne.this, SearchActivity.class);
                                startActivity(intent);
                            }
                        });
                        noSelectionMade.show();

                    }


                }else if(function.getText().toString().equalsIgnoreCase("Function")){



                    try{
                        theFunction = thefunction.getText().toString();
                    }catch(Exception e){
                        AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(SimsonOne.this);
                        noSelectionMade.setTitle("Input Required.");
                        noSelectionMade.setMessage("Check your function. \n It should be in the form: (f(x,y)) \n That is, (x-y).");

                        noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Intent intent = new Intent(SimsonOne.this, SearchActivity.class);
                                startActivity(intent);
                            }
                        });
                        noSelectionMade.show();

                    }
                    try{
                        theUpperBound = Double.parseDouble(upperBound.getText().toString());
                    }catch(Exception e){
                        AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(SimsonOne.this);
                        noSelectionMade.setTitle("Input Required.");
                        noSelectionMade.setMessage("Check your Step Size Value. \n It should be a Real Value in the form: 0.00");

                        noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Intent intent = new Intent(SimsonOne.this, SearchActivity.class);
                                startActivity(intent);
                            }
                        });
                        noSelectionMade.show();

                    }
                    try{
                        theLowerBound = Double.parseDouble(lowerBound.getText().toString());
                    }catch(Exception e){
                        AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(SimsonOne.this);
                        noSelectionMade.setTitle("Input Required.");
                        noSelectionMade.setMessage("Check your Number of Iterations. \n It should be an Integer in the form: 00");

                        noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Intent intent = new Intent(SimsonOne.this, SearchActivity.class);
                                startActivity(intent);
                            }
                        });
                        noSelectionMade.show();

                    }
                    try{
                        intervals = Integer.parseInt(inte.getText().toString());
                    }catch(Exception e){
                        AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(SimsonOne.this);
                        noSelectionMade.setTitle("Input Required.");
                        noSelectionMade.setMessage("Check your Number of Iterations. \n It should be an Integer in the form: 00");

                        noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Intent intent = new Intent(SimsonOne.this, SearchActivity.class);
                                startActivity(intent);
                            }
                        });
                        noSelectionMade.show();

                    }
                    //CREATE EulerMethod object.
                    EulerMethod useEuler = new EulerMethod();

                    useEuler.CreateArrayExpression(theFunction, simsonContent);
                    //ValidateInput(theContent);
                    boolean error = false;

                    for(int i = 0; i < simsonContent.length-1; i++){
                        if(!(simsonContent[i].equalsIgnoreCase("x")) && !(simsonContent[i].equalsIgnoreCase("y"))
                                &&  !(simsonContent[i].equalsIgnoreCase("-"))  && !(simsonContent[i].equalsIgnoreCase("+"))
                                && !(simsonContent[i].equalsIgnoreCase("*")) && !(simsonContent[i].equalsIgnoreCase("^"))
                                &&  !(simsonContent[i].equalsIgnoreCase("/")) &&  !(simsonContent[i].equalsIgnoreCase("sin"))
                                &&  !(simsonContent[i].equalsIgnoreCase("cos")) &&  !(simsonContent[i].equalsIgnoreCase("sinh"))
                                &&  !(simsonContent[i].equalsIgnoreCase("cosh"))  && !(simsonContent[i].equalsIgnoreCase("tan"))
                                &&  !(simsonContent[i].equalsIgnoreCase("tanh")) &&  !(simsonContent[i].equalsIgnoreCase("sec"))
                                &&  !(simsonContent[i].equalsIgnoreCase("sech")) && !(simsonContent[i].equalsIgnoreCase("cosec"))
                                && !(simsonContent[i].equalsIgnoreCase("cosech")) && !(useEuler.CheckForNumeric(simsonContent[i]))
                                && !(simsonContent[i].equalsIgnoreCase("(")) && !(simsonContent[i].equalsIgnoreCase(")")) && !(simsonContent[i].equalsIgnoreCase("$"))){

                            error = true;

                            break;
                        }else if(simsonContent[i].equalsIgnoreCase("$")){
                            error = false;

                            break;
                        }

                    }

                    if(error){
                        //dialog box to tell the user that he has wrong input.
                        AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(SimsonOne.this);
                        noSelectionMade.setTitle("Problem With Input.");
                        noSelectionMade.setMessage("Check your Function for wrong input!\nYou can only use the variables such as\n 'X' and 'Y'\n your arithmetic operators and any of \nthe Trignometric Function.");

                        noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Intent intent = new Intent(SimsonOne.this, SimsonOne.class);
                                Bundle send = new Bundle();


                                intent.putExtras(send);

                                //starActivity(getIntent());
                                startActivity(intent);
                                finish();
                            }
                        });

                        noSelectionMade.show();


                    }else{

                        int  i = 0, stepInterval = (int) (theLowerBound + theUpperBound)/intervals;
                        double j = theLowerBound, value = 0.0;
                        while(j <= theUpperBound){
                            useEuler.MakeCopy(simsonContent, mySimsonContent);
                            useEuler.SubstituteValues(mySimsonContent, j, 0);
                            value = useEuler.MyEvaluation(mySimsonContent);
                            simsonTheY[i] = Double.toString(value);
                            simsonTheX[i] = Double.toString(j);
                            i += 1;
                            j += stepInterval;
                        }
                        //sum of even
                        i = 2;
                        while(i < simsonTheY.length){
                            sumEven += Double.parseDouble(simsonTheY[i]);
                            i += 2;
                        }
                        //sum of odd
                        i = 3;
                        while(i < simsonTheY.length){
                            sumOdd += Double.parseDouble(simsonTheY[i]);
                            i += 2;
                        }
                        first = Double.parseDouble(simsonTheY[0]);
                        last = Double.parseDouble(simsonTheY[simsonTheY.length - 1]);
                        valueIs = (1/3*stepInterval*((first + last) + (4 * sumEven) + (2 * sumOdd)));
                    }
                }
                Intent sendResult = new Intent(SimsonOne.this,Result.class);

                Bundle send = new Bundle();

                //pass the three arrays to the activity Result.

                send.putStringArray("simsonThex", simsonTheX);
                send.putStringArray("simsonThey", simsonTheY);
                send.putDouble("first", first);
                send.putDouble("last", last);
                send.putDouble("valueIs", valueIs);
                sendResult.putExtras(send);

                //start the Result Activity.
                startActivity(sendResult);

            }
        });

    }
    public void setVisiblityForCoordinate(){
        EditText xCoordinate = (EditText)findViewById(R.id.xcoordinate);
        EditText yCoordinate = (EditText)findViewById(R.id.ycoordinate);
        EditText thefunction = (EditText)findViewById(R.id.function);
        EditText upperBound = (EditText)findViewById(R.id.upperBound);
        EditText lowerBound = (EditText)findViewById(R.id.lowerBound);
        EditText inte = (EditText)findViewById(R.id.interv);
        thefunction.setVisibility(View.GONE);
        upperBound.setVisibility(View.GONE);
        lowerBound.setVisibility(View.GONE);
        inte.setVisibility(View.GONE);
        xCoordinate.setVisibility(View.VISIBLE);
        yCoordinate.setVisibility(View.VISIBLE);
    }
    public void setVisiblityForFunction(){
        EditText xCoordinate = (EditText)findViewById(R.id.xcoordinate);
        EditText yCoordinate = (EditText)findViewById(R.id.ycoordinate);
        EditText thefunction = (EditText)findViewById(R.id.function);
        EditText upperBound = (EditText)findViewById(R.id.upperBound);
        EditText lowerBound = (EditText)findViewById(R.id.lowerBound);
        EditText inte = (EditText)findViewById(R.id.interv);
        xCoordinate.setVisibility(View.GONE);
        yCoordinate.setVisibility(View.GONE);
        thefunction.setVisibility(View.VISIBLE);
        upperBound.setVisibility(View.VISIBLE);
        lowerBound.setVisibility(View.VISIBLE);
        inte.setVisibility(View.VISIBLE);
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
