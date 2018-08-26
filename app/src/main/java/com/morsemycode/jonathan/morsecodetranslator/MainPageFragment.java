package com.morsemycode.jonathan.morsecodetranslator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;



public class MainPageFragment extends Fragment implements Animation.AnimationListener{
    View v;
    ImageView imageView1,imageView2,imageView3;
    AlphaAnimation a1 , a2 , a3,fadeOut;
    public MainPageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        v= inflater.inflate(R.layout.mainpage, container, false);
        imageView1= v.findViewById(R.id.arrow_anim_main_page1);
        imageView2= v.findViewById(R.id.arrow_anim_main_page2);
        imageView3= v.findViewById(R.id.arrow_anim_main_page3);

        return v;
    }


   @Override
    public void onActivityCreated(Bundle savedInstanceState){
        if(savedInstanceState==null) {
            a1 = new AlphaAnimation(0.0f,1.0f);
            a2 = new AlphaAnimation(0.0f,1.0f);
            a3 = new AlphaAnimation(0.0f,1.0f);
            fadeOut=new AlphaAnimation(1.0f,0.0f);

            a1.setFillAfter(true);
            a1.setDuration(500);
            a2.setFillAfter(true);
            a2.setDuration(500);
            a3.setFillAfter(true);
            a3.setDuration(500);
            fadeOut.setFillAfter(true);
            fadeOut.setDuration(200);

            a1.setAnimationListener(this);
            a2.setAnimationListener(this);
            a3.setAnimationListener(this);
            fadeOut.setAnimationListener(this);

            imageView2.setVisibility(View.INVISIBLE);
            imageView3.setVisibility(View.INVISIBLE);

            imageView1.startAnimation(a1);

        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAnimationEnd(Animation animation){
        if(animation == a1 ){
            imageView2.setVisibility(View.VISIBLE);
            imageView2.startAnimation(a2);
        }

        else if(animation == a2){
            imageView3.setVisibility(View.VISIBLE);
            imageView3.startAnimation(a3);
        }

        else if(animation==a3){
            imageView1.startAnimation(fadeOut);
            imageView2.startAnimation(fadeOut);
            imageView3.startAnimation(fadeOut);
        }
        else if(animation==fadeOut){
            imageView2.setVisibility(View.INVISIBLE);
            imageView3.setVisibility(View.INVISIBLE);

            imageView1.startAnimation(a1);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation){

    }

    @Override
    public void onAnimationStart(Animation animation){

    }

}
