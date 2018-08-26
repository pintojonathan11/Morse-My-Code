package com.morsemycode.jonathan.morsecodetranslator;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Chart_Fragment extends Fragment {
    View v;
    TextView textTranslation;
    int[] images_resources = {R.drawable.a_translation, R.drawable.b_translation, R.drawable.c_translation,
            R.drawable.d_translation, R.drawable.e_translation, R.drawable.f_translation, R.drawable.g_translation,
            R.drawable.h_translation, R.drawable.i_translation, R.drawable.j_translation, R.drawable.k_translation,
            R.drawable.l_translation, R.drawable.m_translation, R.drawable.n_translation, R.drawable.o_translation,
            R.drawable.p_translation, R.drawable.q_translation, R.drawable.r_translation, R.drawable.s_translation,
            R.drawable.t_translation, R.drawable.u_translation, R.drawable.v_translation, R.drawable.w_translation,
            R.drawable.x_translation, R.drawable.y_translation, R.drawable.z_translation, R.drawable.one_translation,
            R.drawable.two_translation, R.drawable.three_translation, R.drawable.four_translation, R.drawable.five_translation,
            R.drawable.six_translation, R.drawable.seven_translation, R.drawable.eight_translation, R.drawable.nine_translation,
            R.drawable.zero_translation};

    public Chart_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_morse_code_chart, container, false);

        textTranslation = v.findViewById(R.id.Official_Morse_Code_Translation_text);
        return v;
    }

    class IsPlaying{
        View view;
        MediaPlayer mediaPlayer;
        boolean somethingIsPlaying;
        public IsPlaying(MediaPlayer mp,View v){
            view=v;
            mediaPlayer=mp;
            somethingIsPlaying=false;
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            TextViewCompat.setAutoSizeTextTypeWithDefaults(textTranslation, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            GridLayout gridLayout = v.findViewById(R.id.grid_layout);
            final MediaPlayer[] sound_resource = {MediaPlayer.create(v.getContext(), R.raw.a_sound),
                    MediaPlayer.create(v.getContext(), R.raw.b_sound), MediaPlayer.create(v.getContext(), R.raw.c_sound),
                    MediaPlayer.create(v.getContext(), R.raw.d_sound), MediaPlayer.create(v.getContext(), R.raw.e_sound),
                    MediaPlayer.create(v.getContext(), R.raw.f_sound), MediaPlayer.create(v.getContext(), R.raw.g_sound),
                    MediaPlayer.create(v.getContext(), R.raw.h_sound), MediaPlayer.create(v.getContext(), R.raw.i_sound),
                    MediaPlayer.create(v.getContext(), R.raw.j_sound), MediaPlayer.create(v.getContext(), R.raw.k_sound),
                    MediaPlayer.create(v.getContext(), R.raw.l_sound), MediaPlayer.create(v.getContext(), R.raw.m_sound),
                    MediaPlayer.create(v.getContext(), R.raw.n_sound), MediaPlayer.create(v.getContext(), R.raw.o_sound),
                    MediaPlayer.create(v.getContext(), R.raw.p_sound), MediaPlayer.create(v.getContext(), R.raw.q_sound),
                    MediaPlayer.create(v.getContext(), R.raw.r_sound), MediaPlayer.create(v.getContext(), R.raw.s_sound),
                    MediaPlayer.create(v.getContext(), R.raw.t_sound), MediaPlayer.create(v.getContext(), R.raw.u_sound),
                    MediaPlayer.create(v.getContext(), R.raw.v_sound), MediaPlayer.create(v.getContext(), R.raw.w_sound),
                    MediaPlayer.create(v.getContext(), R.raw.x_sound), MediaPlayer.create(v.getContext(), R.raw.y_sound),
                    MediaPlayer.create(v.getContext(), R.raw.z_sound), MediaPlayer.create(v.getContext(), R.raw.one_sound),
                    MediaPlayer.create(v.getContext(), R.raw.two_sound), MediaPlayer.create(v.getContext(), R.raw.three_sound),
                    MediaPlayer.create(v.getContext(), R.raw.four_sound), MediaPlayer.create(v.getContext(), R.raw.five_sound),
                    MediaPlayer.create(v.getContext(), R.raw.six_sound), MediaPlayer.create(v.getContext(), R.raw.seven_sound),
                    MediaPlayer.create(v.getContext(), R.raw.eight_sound), MediaPlayer.create(v.getContext(), R.raw.nine_sound),
                    MediaPlayer.create(v.getContext(), R.raw.zero_sound)};

            String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            List<ImageButton> imageButtonList = new ArrayList<ImageButton>();

            final IsPlaying isPlaying = new IsPlaying(null,null);

            final int[] ind = {0};

            for (int i = 0, c = 0, r = 0; i < 36 * 3; i++, c++) {
                if (c == 3) {
                    c = 0;
                    r++;
                    ind[0] = r;
                }

                GridLayout.Spec row = GridLayout.spec(r, GridLayout.CENTER);
                GridLayout.Spec col = GridLayout.spec(c, GridLayout.CENTER);
                GridLayout.Spec col2 = GridLayout.spec(c, GridLayout.LEFT);

                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row, col);

                if (c == 0) {
                    ImageButton imageButton = new ImageButton(v.getContext());
                    final Animation animation= AnimationUtils.loadAnimation(v.getContext(),R.anim.rotate_anim);
                    imageButton.setId(ind[0]);
                    imageButton.setBackgroundResource(R.drawable.ic_play_sound);
                    imageButtonList.add(imageButton);
                    imageButtonList.get(ind[0]).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MediaPlayer player = sound_resource[v.getId()];
                            if(isPlaying.view!=null){
                                isPlaying.view.clearAnimation();
                            }
                            v.startAnimation(animation);
                            isPlaying.view=v;
                            animation.setDuration(player.getDuration());
                            if (!isPlaying.somethingIsPlaying) {//if nothing is playing
                                player.start();
                                isPlaying.mediaPlayer = player;
                                isPlaying.somethingIsPlaying = true;
                            } else if (player.isPlaying()) {//if something is playing and player is already playing
                                player.seekTo(0);
                                player.start();
                            } else {//if something is playing and player is not playing
                                isPlaying.mediaPlayer.seekTo(0);
                                isPlaying.mediaPlayer.pause();
                                player.start();
                                isPlaying.mediaPlayer = player;
                                isPlaying.somethingIsPlaying = true;
                            }

                            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    isPlaying.somethingIsPlaying = false;
                                    mp.seekTo(0);
                                }
                            });
                        }
                    });
                    gridLayout.addView(imageButton, layoutParams);
                } else if (c == 1) {
                    TextView textView = new TextView(v.getContext());
                    textView.setText(String.valueOf(abc.charAt(r)));
                    textView.setTextSize(27);
                    textView.setTextColor(v.getContext().getResources().getColor(R.color.main_text_color));
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    gridLayout.addView(textView, layoutParams);

                } else if (c == 2) {
                    ImageView imageView = new ImageView(v.getContext());
                    imageView.setImageResource(images_resources[r]);

                    GridLayout.LayoutParams layoutParams1 = new GridLayout.LayoutParams(row, col2);
                    layoutParams1.leftMargin = 13;
                    gridLayout.addView(imageView, layoutParams1);
                }
            }

        }
        super.onActivityCreated(savedInstanceState);

    }
}
