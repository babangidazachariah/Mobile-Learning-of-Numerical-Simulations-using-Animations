package numsim.justintimetech.com.numsim;

import android.content.Context;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

public class TypeWriter extends TextView {

    private CharSequence mText;
    private int mIndex,scrollStep,count = 0;
    private long mDelay = 250;//500
    boolean isTrue = false;
    //int scroll = 0;
    public TypeWriter(Context object){

        super(object);

    }

    /*
     * Introduction about project: use an activity and display progress dialog.
     * present a list of methods.
     * present a dialog to request for either run or tutorials
     * use an activity to request for initial-value problem from user.
     * present the results
     */
    public TypeWriter(Context context, AttributeSet attrs){
        super(context,attrs);

    }

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable(){
        public void run(){
            setTextSize(20);
            setSelected(true);

            setMovementMethod(new ScrollingMovementMethod());
            setText(mText.subSequence(0,mIndex++));
            if(mIndex <= mText.length()){
                mHandler.postDelayed(characterAdder, mDelay);
                count += 1;
                if(count == 30){

                    scrollBy(0, scrollStep);
                    count = 0;
                }
            }



        }
    };
    public void animateText(CharSequence text){
        mText = text;
        mIndex = 0;
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);

        count += 1;

    }
    public void setCharacterDelay(long millis){
        mDelay = millis;
    }

    public void setScrollBy(int step){
        scrollStep = step;
    }



}
