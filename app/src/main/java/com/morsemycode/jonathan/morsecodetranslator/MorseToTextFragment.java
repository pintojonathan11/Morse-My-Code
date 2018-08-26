package com.morsemycode.jonathan.morsecodetranslator;

import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MorseToTextFragment extends Fragment {
    View v;
    ImageButton imageButton;
    EditText textView;
    public MorseToTextFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        v= inflater.inflate(R.layout.morse_to_text, container, false);
        imageButton=v.findViewById(R.id.backspace_imagebutton);
        textView=v.findViewById(R.id.morse_of_morse_to_text);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        if(savedInstanceState==null) {
            imageButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Animation slideLeft=new TranslateAnimation(0,-30,0,0);
                    slideLeft.setDuration(200);
                    final Animation slideRight=new TranslateAnimation(-30,10,0,0);
                    slideLeft.setDuration(200);
                    final View view=v;
                    slideLeft.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            view.startAnimation(slideRight);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    v.setAnimation(slideLeft);
                    textView.setText("");
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,50);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    return false;
                }
            });
        }
        super.onActivityCreated(savedInstanceState);
    }

}
