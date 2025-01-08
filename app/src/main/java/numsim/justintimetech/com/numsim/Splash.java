package numsim.justintimetech.com.numsim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Splash extends Activity{

    private void RunAnimations(){
        Animation a = AnimationUtils.loadAnimation(this, R.anim.alpha);
        a.reset();
        TextView tv = (TextView)findViewById(R.id.firstTextView);
        tv.clearAnimation();
        tv.startAnimation(a);

        a = AnimationUtils.loadAnimation(this, R.anim.translate);
        a.reset();
        tv = (TextView)findViewById(R.id.secondTextView);
        tv.clearAnimation();
        tv.startAnimation(a);

        a = AnimationUtils.loadAnimation(this, R.anim.rotate);
        a.reset();
        tv = (TextView)findViewById(R.id.thirdTextView);
        tv.clearAnimation();
        tv.startAnimation(a);

        a = AnimationUtils.loadAnimation(this, R.anim.translate);
        a.reset();
        tv = (TextView)findViewById(R.id.fourthTextView);
        tv.clearAnimation();
        tv.startAnimation(a);


        a = AnimationUtils.loadAnimation(this, R.anim.scalerotate);
        a.reset();
        tv = (TextView)findViewById(R.id.fourthTextView);
        tv.clearAnimation();
        tv.startAnimation(a);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        RunAnimations();
        Button cont = (Button)findViewById(R.id.cont);
        cont.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                Intent start = new Intent(Splash.this,SearchActivity.class);
                finish();
                startActivity(start);

                return false;
            }
        });

    }
}
