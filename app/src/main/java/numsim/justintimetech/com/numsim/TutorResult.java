package numsim.justintimetech.com.numsim;


import android.app.Activity;
import android.os.Bundle;

import numsim.justintimetech.com.numsim.TypeWriter;

//import android.content.Intent;
//import android.speech.tts.TextToSpeech;
//import android.speech.tts.TextToSpeech.OnInitListener;



public abstract class TutorResult extends Activity {//implements OnInitListener{
    //	private int MY_DATA_CHECK_CODE = 0;
    //	private TextToSpeech tts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypeWriter writer = new TypeWriter(this);

        Bundle receiveResult = getIntent().getExtras();
        String theSolution = receiveResult.getString("theBuildSolution");
        String theResult = receiveResult.getString("theBuildResult");

        String finalSolution = theSolution + theResult;
	        /*/
	        tts.speak(theSolution, TextToSpeech.QUEUE_ADD, null);
	        Intent checkIntent = new Intent();
	        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	        
	        startActivityForResult(checkIntent,MY_DATA_CHECK_CODE);
	       
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
	      /*  @Override
	        public void onInit(int status){
	        	if(status == TextToSpeech.SUCCESS)
	        }
	        */
        setContentView(writer);
        writer.setCharacterDelay(100);
        writer.animateText(finalSolution);
        writer.scrollBy(0, 5);

    }
}