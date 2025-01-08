package numsim.justintimetech.com.numsim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

//import android.widget.EditText;

public class SearchActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button BackButton = (Button) findViewById(R.id.btnBackButton);
        BackButton.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();

            }
        });



        //listen to selected option so as to change its color.
        RadioGroup selectedOption = (RadioGroup)findViewById(R.id.method);
        selectedOption.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                v.setBackgroundColor(Color.RED);
            }
        });
        selectedOption.setOnHoverListener(new View.OnHoverListener() {

            public boolean onHover(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                v.setBackgroundColor(Color.RED);
                return true;
            }
        });
        Button MethodSelectionButton =(Button) this.findViewById(R.id.btn_MethodSelectionButton);
        MethodSelectionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String theSelectedText = "";
                try{

                    RadioGroup method = (RadioGroup) findViewById(R.id.method);
                    int selectedId = method.getCheckedRadioButtonId();

                    RadioButton theSelected = (RadioButton) findViewById(selectedId);
                    theSelectedText = theSelected.getText().toString();

                }catch(Exception e){
                    AlertDialog.Builder noSelectionMade = new AlertDialog.Builder(SearchActivity.this);
                    noSelectionMade.setTitle("No Method Selected.");
                    noSelectionMade.setMessage("Please, Select a Numerical Method.");

                    noSelectionMade.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                    noSelectionMade.show();

                }

                if( (theSelectedText.equalsIgnoreCase( "Euler Method"))){

                    Intent startMethod = new Intent(SearchActivity.this, EulerMethod.class);

                    //setContentView(R.layout.eulermethod);

                    startActivity(startMethod);

                }else if(theSelectedText.equalsIgnoreCase( "Euler-Cauchy Method")){
                    Intent startMethod = new Intent(SearchActivity.this, EulerModifiedMethod.class);

                    //setContentView(R.layout.eulermethod);

                    startActivity(startMethod);


                }else if(theSelectedText.equalsIgnoreCase("Euler-Cauchy Tutor")){
                    Intent startMethod = new Intent(SearchActivity.this, EulerCauchyTutor.class);

                    //setContentView(R.layout.eulermethod);

                    startActivity(startMethod);


                }else if(theSelectedText.equalsIgnoreCase( "4th-Order Runge-Kutta Tutor")){

                    Intent startMethod = new Intent(SearchActivity.this, RungeKuttaTutor.class);

                    //setContentView(R.layout.eulermethod);

                    startActivity(startMethod);

                }else if(theSelectedText.equalsIgnoreCase( "4th-Order Runge-Kutta Method")){

                    Intent startMethod = new Intent(SearchActivity.this, FourthRungeKuttaMethod.class);

                    //setContentView(R.layout.eulermethod);

                    startActivity(startMethod);




                }else if(theSelectedText.equalsIgnoreCase("Euler Method Tutor")){

                    Intent startMethod = new Intent(SearchActivity.this, EulerTutor.class);

                    //setContentView(R.layout.eulermethod);

                    startActivity(startMethod);
                }


            }
        });
    }



    //create menu options
    private void CreateMenu(Menu menu)
    {
        MenuItem mnu1 = menu.add(0, 0, 0, "Euler Method");
        {
            mnu1.setAlphabeticShortcut('E');
            mnu1.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Euler Tutor");
        {
            mnu2.setAlphabeticShortcut('T');
            mnu2.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "Euler-Cauchy Method");
        {
            mnu3.setAlphabeticShortcut('M');
            mnu3.setIcon(R.drawable.ic_launcher);
        }
        MenuItem mnu4 = menu.add(0, 3, 3, "Euler-Cauchy Tutor");
        {
            mnu4.setAlphabeticShortcut('C');
        }
        MenuItem mnu5 = menu.add(0, 4, 4, "4th Order Runge-Kutta Method");
        {
            mnu5.setAlphabeticShortcut('R');
        }
        MenuItem mnu6 = menu.add(0, 5, 5, "4th Order Runge-Kutta Tutor");
        {
            mnu6.setAlphabeticShortcut('K');
        }
        MenuItem mnu7 = menu.add(0, 6, 6, "SMS Center");
        {
            mnu7.setAlphabeticShortcut('S');
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
                switchView = new Intent(this,EulerTutor.class);
                startActivity(switchView);
                break;
            case 2:
                switchView = new Intent(this,EulerModifiedMethod.class);
                startActivity(switchView);
                break;
            case 3:
                switchView = new Intent(this,EulerCauchyTutor.class);
                startActivity(switchView);
                break;
            case 4:
                switchView = new Intent(this,FourthRungeKuttaMethod.class);
                startActivity(switchView);
                break;
            case 5:
                switchView = new Intent(this,RungeKuttaTutor.class);
                startActivity(switchView);
                break;

            case 6:
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