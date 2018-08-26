package com.morsemycode.jonathan.morsecodetranslator;

import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity  {
    private DrawerLayout mDrawerLayout;
    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Ad setup
        MobileAds.initialize(this,"ca-app-pub-8737453587464603/2690791840");

        //Fragment SetUp
        MainPageFragment mainPageFragment=new MainPageFragment();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();


        if(findViewById(R.id.frag_container)!=null){
            if(savedInstanceState!=null) {
                return;
            }
            fragmentTransaction.add(R.id.frag_container,mainPageFragment).commit();
        }

        TextView textMorse=findViewById(R.id.morse_text_title);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(textMorse,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);



        //ActionBar setup
        Toolbar toolbar=findViewById(R.id.ToolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Morse My Code</font>"));
        actionBar.setHomeAsUpIndicator(R.drawable.ic_actionbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        if(menuItem.getItemId()==R.id.Morse_Chart){
                            Chart_Fragment chartFrag= new Chart_Fragment();
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frag_container,chartFrag,"MorseChart").commit();
                        }
                        else if(menuItem.getItemId()==R.id.Text_to_Morse){
                            TextToMorseFragment textToMorseFragment = new TextToMorseFragment();
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frag_container,textToMorseFragment,"TextToMorseFragment").commit();

                        }
                        else if(menuItem.getItemId()==R.id.Morse_to_Text){
                            MorseToTextFragment morseToTextFragment = new MorseToTextFragment();
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frag_container,morseToTextFragment,"MorseToText").commit();
                        }
                        return true;
                    }
                });

    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something to Convert to Morse Code");

    // Start the activity, the intent will be populated with the speech text
        try{
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),"Sorry, but your device doesn't support voice recognition",Toast.LENGTH_SHORT).show();
        }
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
            Conversion conversion=new Conversion(spokenText);
            EditText editText = findViewById(R.id.edit_text_of_text_to_morse);
            editText.setText(spokenText);
            TextView textView=findViewById(R.id.result_of_text_to_morse);
            textView.setText(conversion.TextToMorseConversion());
        }
        else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
            Toast.makeText(this,"Audio Error",Toast.LENGTH_SHORT).show();
        }
        else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
            Toast.makeText(this,"Client Error",Toast.LENGTH_SHORT).show();
        }
        else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
            Toast.makeText(this,"Network Error",Toast.LENGTH_SHORT).show();
        }
        else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
            Toast.makeText(this,"No Match",Toast.LENGTH_SHORT).show();
        }
        else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
            Toast.makeText(this,"Server Error",Toast.LENGTH_SHORT).show();
        }
    }



    public void onClick(View v){
        if(v.getId()==R.id.to_button_text_to_morse) {
            //Animation Part
            Button dotButton=findViewById(R.id.to_button_text_to_morse);
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
            MyBounceInterpolator myBounceInterpolator = new MyBounceInterpolator(0.2,20);
            myAnim.setInterpolator(myBounceInterpolator);
            dotButton.startAnimation(myAnim);
            //Text Convert Part
            EditText editText = findViewById(R.id.edit_text_of_text_to_morse);
            String text = editText.getText().toString().trim();
            TextView result_text_view = findViewById(R.id.result_of_text_to_morse);
            result_text_view.setText("");
            Conversion conversion = new Conversion(text);

            result_text_view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,50);
            String converted=conversion.TextToMorseConversion();
            result_text_view.setText(converted);
            /*if(text.length()!=0){
                setTextOneAtATime(converted,result_text_view);
            }*/
            //result_text_view.setText(conversion.TextToMorseConversion());
        }
        else if(v.getId()==R.id.arrow_anim_main_page1||v.getId()==R.id.arrow_anim_main_page2||v.getId()==R.id.arrow_anim_main_page3){
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        else if(v.getId()==R.id.voice_button){
            displaySpeechRecognizer();

        }
        else if(v.getId()==R.id.backspace_imagebutton){
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.translation);
            MyBounceInterpolator myBounceInterpolator = new MyBounceInterpolator(1,20);
            myAnim.setInterpolator(myBounceInterpolator);
            v.startAnimation(myAnim);
            EditText textView=findViewById(R.id.morse_of_morse_to_text);
            String text= textView.getText().toString();
            if(text.length()>1) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,150);
                textView.setText(text.substring(0, text.length() - 1));
            }
            else{
                textView.setText("");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,50);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
        }
        else if(v.getId()==R.id.dot_button){
            //Animation Part
            ImageButton dotButton=findViewById(R.id.dot_button);
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
            MyBounceInterpolator myBounceInterpolator = new MyBounceInterpolator(0.2,20);
            myAnim.setInterpolator(myBounceInterpolator);
            dotButton.startAnimation(myAnim);

            //Text Convert Part
            EditText textView=findViewById(R.id.morse_of_morse_to_text);
            String text=textView.getText().toString()+".";
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,150);
            textView.setText(text);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        else if(v.getId()==R.id.space_button){
            //Animation Part
            ImageButton dotButton=findViewById(R.id.space_button);
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
            MyBounceInterpolator myBounceInterpolator = new MyBounceInterpolator(0.2,20);
            myAnim.setInterpolator(myBounceInterpolator);
            dotButton.startAnimation(myAnim);

            //Text Convert Part
            EditText textView=findViewById(R.id.morse_of_morse_to_text);
            String text=textView.getText().toString()+" ";
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,150);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setText(text);
        }
        else if(v.getId()==R.id.dash_button){
            //Animation Part
            ImageButton dotButton=findViewById(R.id.dash_button);
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
            MyBounceInterpolator myBounceInterpolator = new MyBounceInterpolator(0.2,20);
            myAnim.setInterpolator(myBounceInterpolator);
            dotButton.startAnimation(myAnim);



            //Text Convert Part
            EditText textView=findViewById(R.id.morse_of_morse_to_text);
            String text=textView.getText().toString()+"-";
            textView.setText(text);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,150);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        else if(v.getId()==R.id.To_button_morse_to_text){
            //Animation Part
            Button dotButton=findViewById(R.id.To_button_morse_to_text);
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
            MyBounceInterpolator myBounceInterpolator = new MyBounceInterpolator(0.2,20);
            myAnim.setInterpolator(myBounceInterpolator);
            dotButton.startAnimation(myAnim);




            //Text Convert Part
            TextView resultTextView=findViewById(R.id.result_of_morse_to_text);
            EditText prevTextView=findViewById(R.id.morse_of_morse_to_text);
            String text=prevTextView.getText().toString();
            Conversion conversion=new Conversion(text);
            resultTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,110);
            String converted=conversion.MorseToTextConversion();


            //result
            resultTextView.setText(converted);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*public void setTextOneAtATime(final String s, final TextView result_text_view){
        Log.e("Show me the string",s);
        final int[] ind=new int[1];
        ind[0]=0;
        final int length=s.length();
        result_text_view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        result_text_view.setText("");
        final Timer timer=new Timer();

        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                char c= s.charAt(ind[0]);
                result_text_view.append(String.valueOf(c));
                ind[0]++;
            }
        };

        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
                if (ind[0] == length - 1) {
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask,1,400);
    }*/


}
