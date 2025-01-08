package numsim.justintimetech.com.numsim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

//import android.content.Intent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;

public class SearchResult extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach_result);
        Bundle b = getIntent().getExtras();
        int selectedID = b.getInt("selectedId");
        String theSelected = b.getString("theSelected");

        TextView vw3 = (TextView) findViewById(R.id.txtSelected);

        vw3.setText(theSelected + "  " + selectedID);


        if( (theSelected.equalsIgnoreCase( "Euler Method"))){

            Intent startMethod = new Intent(SearchResult.this, EulerMethod.class);

            //setContentView(R.layout.eulermethod);

            startActivity(startMethod);

        }else if(theSelected.equalsIgnoreCase( "Euler-Cauchy Method")){
            Intent startMethod = new Intent(SearchResult.this, EulerModifiedMethod.class);

            //setContentView(R.layout.eulermethod);

            startActivity(startMethod);


        }else if(theSelected.equalsIgnoreCase("Euler-Cauchy Method Tutor")){
            Intent startMethod = new Intent(SearchResult.this, EulerCauchyTutor.class);

            //setContentView(R.layout.eulermethod);

            startActivity(startMethod);


        }else if(theSelected.equalsIgnoreCase( "Runge-Kutta Method")){

            Intent startMethod = new Intent(SearchResult.this, RungeKuttaMethod.class);

            //setContentView(R.layout.eulermethod);

            startActivity(startMethod);

        }else if(theSelected.equalsIgnoreCase( "4th Runge-Kutta Method")){

            Intent startMethod = new Intent(SearchResult.this, FourthRungeKuttaMethod.class);

            //setContentView(R.layout.eulermethod);

            startActivity(startMethod);




        }else if(theSelected.equalsIgnoreCase("Euler Method Tutor")){

            Intent startMethod = new Intent(SearchResult.this, EulerTutor.class);

            //setContentView(R.layout.eulermethod);

            startActivity(startMethod);
        }

    }



}
