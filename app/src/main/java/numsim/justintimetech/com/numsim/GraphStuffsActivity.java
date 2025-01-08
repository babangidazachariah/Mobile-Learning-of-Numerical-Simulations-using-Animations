package numsim.justintimetech.com.numsim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;


public class GraphStuffsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //get the three arrays to display result
        Bundle receiveResult = getIntent().getExtras();

        String[] theX = receiveResult.getStringArray("theX");
        String[] theY = receiveResult.getStringArray("theY");
        //String[] theDerivative = receiveResult.getStringArray("theDerivative");

        ArrayList<PointOnChart> points1 = new ArrayList<PointOnChart>();
        for(int i= 1; i < theX.length; i++){

            float X = Float.parseFloat(theX[i]);
            float Y = Float.parseFloat(theY[i]);

            points1.add(new PointOnChart(X,Y));
            //System.out.println("( X : "+ X + ", Y : "+ Y+" )");

        }

       /* ArrayList<PointOnChart> points2 = new ArrayList<PointOnChart>();
        for(int i= 0; i < theX.length; i++){

            float X = i;
            float Y = (4*X*X)- 0;

            points2.add(new PointOnChart(X,-Y));
            //System.out.println("( X : "+ X + ", Y : "+ Y+" )");

        }

        ArrayList<PointOnChart> points3 = new ArrayList<PointOnChart>();
        for(int i=30; i>=-30; i--){

            float X = i;
            float Y = (4*X*X) + 1500;

            points3.add(new PointOnChart(X,-Y));
            //System.out.println("( X : "+ X + ", Y : "+ Y+" )");

        }

        ArrayList<PointOnChart> points4 = new ArrayList<PointOnChart>();
        for(int i=30; i>=-30; i--){

            float X = i;
            float Y = (4*X*X) - 1300;

            points4.add(new PointOnChart(X,-Y));
            //System.out.println("( X : "+ X + ", Y : "+ Y+" )");

        }

        ArrayList<PointOnChart> points5 = new ArrayList<PointOnChart>();
        for(int i=30; i>=-30; i--){

            float X = i;
            float Y = (4*X*X) - 200;

            points5.add(new PointOnChart(X,-Y));
            //System.out.println("( X : "+ X + ", Y : "+ Y+" )");

        }

        ArrayList<PointOnChart> points6 = new ArrayList<PointOnChart>();
        for(int i=30; i>=-30; i--){

            float X = i;
            float Y = (4*X*X)+ 1300;

            points6.add(new PointOnChart(X,-Y));
            //System.out.println("( X : "+ X + ", Y : "+ Y+" )");

        }
	*/
        PathAttributes pathAttributes1 = new PathAttributes();
        pathAttributes1.setPointColor("#00AAAAAA");
        pathAttributes1.setPathColor("#FFAF00");
        PathOnChart path1 = new PathOnChart(points1, pathAttributes1);

       /* PathAttributes pathAttributes2 = new PathAttributes();
        pathAttributes2.setPointColor("#00AAAAAA");
        pathAttributes2.setPathColor("#FFFFFF");
        PathOnChart path2 = new PathOnChart(points2, pathAttributes2);

        PathAttributes pathAttributes3 = new PathAttributes();
        pathAttributes3.setPointColor("#00AAAAAA");
        pathAttributes3.setPathColor("#008000");
        PathOnChart path3 = new PathOnChart(points3, pathAttributes3);

        PathAttributes pathAttributes4 = new PathAttributes();
        pathAttributes4.setPointColor("#00AAAAAA");
        pathAttributes4.setPathColor("#FFAF00");
        PathOnChart path4 = new PathOnChart(points4, pathAttributes4);

        PathAttributes pathAttributes5 = new PathAttributes();
        pathAttributes5.setPointColor("#00AAAAAA");
        pathAttributes5.setPathColor("#FFFFFF");
        PathOnChart path5 = new PathOnChart(points5, pathAttributes2);

        PathAttributes pathAttributes6 = new PathAttributes();
        pathAttributes6.setPointColor("#00AAAAAA");
        pathAttributes6.setPathColor("#008000");
        PathOnChart path6 = new PathOnChart(points6, pathAttributes3);
        */

        ArrayList<PathOnChart> paths = new ArrayList<PathOnChart>();
        paths.add(path1);
        /*
         * paths.add(path2);
        paths.add(path3);
        paths.add(path4);
        paths.add(path5);
        paths.add(path6);
        */
        LineChartAttributes lineChartAttributes = new LineChartAttributes();
        lineChartAttributes.setBackgroundColor("#aaabbb");
        //lineChartAttributes.setGridVisible(false);
        setContentView(new LineChartView(this, paths, lineChartAttributes));

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
