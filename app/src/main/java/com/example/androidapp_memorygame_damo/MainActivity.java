package com.example.androidapp_memorygame_damo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Cards
    private ImageButton item1_1, item1_2, item1_3, item1_4, item1_5, item1_6;
    private ImageButton item2_1, item2_2, item2_3, item2_4, item2_5, item2_6;
    private ImageButton item3_1, item3_2, item3_3, item3_4, item3_5, item3_6;
    private ImageButton item4_1, item4_2, item4_3, item4_4, item4_5, item4_6;
    private ImageButton item5_1, item5_2, item5_3, item5_4, item5_5, item5_6;
    private ImageButton item6_1, item6_2, item6_3, item6_4, item6_5, item6_6;

    private Button resetButton;

    //Game Manager
    private ImageButton lastCard = null;
    private boolean isTurnActive = false;
    private int score = 0;
    int MAX_SIZE = 36;

    //game board handler
    ArrayList<ImageButton> gameBoard = new ArrayList<ImageButton>();
    ArrayList<Integer> cardIdentifier = new ArrayList<Integer>();
    ArrayList<Integer> cardDrawable = new ArrayList<Integer>();

    //multimedia
    private SoundPool mp, mp2;
    private int idbip, idtouch, idmusic, idwon;
    private float currentVolume = 1.0f;

    //Listeners
    private View.OnClickListener flipElementHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ImageButton b= (ImageButton) view;

            //if is the first turn click (all cards hidden)
            if(!isTurnActive && lastCard == null){
                int currentTag = (Integer)b.getTag();
                b.setImageResource(cardDrawable.get(currentTag));
                lastCard = b;
                isTurnActive = true;
            }
            //if is the second turn click (first card shown) and not the same card
            else if(isTurnActive && (b.getId() != lastCard.getId())){
                int currentTag = (Integer)b.getTag();
                int lastTag = (Integer)lastCard.getTag();
                b.setImageResource(cardDrawable.get(currentTag));
                //if the tag matches, disable the cards and reset the turn
                if(currentTag == lastTag){
                    score++;
                    mp.play(idbip, 1,1,0,0,1);
                    Toast.makeText(b.getContext(), "Very well manuel!", Toast.LENGTH_SHORT).show();
                    lastCard.setEnabled(false);
                    b.setEnabled(false);
                    lastCard = null;
                    checkEndGame(b);

                //if the tag doesnt match, wait 0.2 seconds, reset turn and cover the cards
                }else if(currentTag != lastTag){

                    mp.play(idtouch, 1,1,0,0,1);
                    Toast.makeText(b.getContext(), "Try again!", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            b.setImageResource(R.drawable.monster_portrait_prize);
                            lastCard.setImageResource(R.drawable.monster_portrait_prize);
                            lastCard = null;
                        }
                    }, 2000);
                }
                isTurnActive = false;
            }
        }
    };

    private View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            resetGame();
        }
    };

    //Game manager handlers
    private void resetGame(){
        isTurnActive = false;
        lastCard = null;
        score = 0;
        Collections.shuffle(cardIdentifier);
        assignCards(true);
        coverCards();

    }

    private void checkEndGame(ImageButton b){
        if(score == MAX_SIZE/2){
            mp.play(idwon, 1,1,0,0,1);
            Toast.makeText(b.getContext(), "CONGRATULATIONS! YOU WON!", Toast.LENGTH_SHORT).show();
            resetGame();
        }
    }

    private void coverCards(){
        //cover all cards back
        for(ImageButton item : gameBoard){
            item.setImageResource(R.drawable.monster_portrait_prize);
        }
    }
    private void assignCards(boolean isReset){
        //if is the first time, shuffle and re-assign the cards
        if(!isReset){
            for(Integer i = 0; i < MAX_SIZE/2; i++){
                cardIdentifier.add(i);
                cardIdentifier.add(i);
            }
            Collections.shuffle(cardIdentifier);
        }

        //Set listeners and card tags
        int tagAssign = 0;
        for(ImageButton item : gameBoard){
            if(!isReset) item.setOnClickListener(flipElementHandler);
            item.setEnabled(true);
            item.setTag(Integer.valueOf(cardIdentifier.get(tagAssign)));
            tagAssign++;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //soundpool initalization
        AudioAttributes a = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

        AudioAttributes a2 = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();

        mp = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(a).build();
        mp2 = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(a2).build();

        idbip = mp.load(getBaseContext(), R.raw.good, 1);
        idtouch = mp.load(getBaseContext(), R.raw.bad, 1);
        idwon = mp.load(getBaseContext(), R.raw.won, 1);
        idmusic = mp2.load(getBaseContext(), R.raw.loopin, 1);


        mp2.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sample, int status) {
                soundPool.play(sample, currentVolume, currentVolume, 0, -1, 1);
            }
        });

        //initialize card drawables
        List<Integer> drawableList = Arrays.asList(
                R.drawable.monster_portrait_crystal, R.drawable.monster_portrait_air,
                R.drawable.monster_portrait_earth, R.drawable.monster_portrait_electricity,
                R.drawable.monster_portrait_celestial, R.drawable.monster_portrait_cold,
                R.drawable.monster_portrait_fire, R.drawable.monster_portrait_gold,
                R.drawable.monster_portrait_legendary, R.drawable.monster_portrait_mech,
                R.drawable.monster_portrait_plant, R.drawable.monster_portrait_plasma,
                R.drawable.monster_portrait_poison, R.drawable.monster_portrait_square_a,
                R.drawable.monster_portrait_square_ab, R.drawable.monster_portrait_square_abc,
                R.drawable.monster_portrait_square_abcd, R.drawable.monster_portrait_square_abce
        );
        cardDrawable.addAll(drawableList);

        //get the button views
        gameBoard.add(item1_1 = (ImageButton) findViewById(R.id.image_btn1_1));
        gameBoard.add(item1_2 = (ImageButton) findViewById(R.id.image_btn1_2));
        gameBoard.add(item1_3 = (ImageButton) findViewById(R.id.image_btn1_3));
        gameBoard.add(item1_4 = (ImageButton) findViewById(R.id.image_btn1_4));
        gameBoard.add(item1_5 = (ImageButton) findViewById(R.id.image_btn1_5));
        gameBoard.add(item1_6 = (ImageButton) findViewById(R.id.image_btn1_6));

        gameBoard.add(item2_1 = (ImageButton) findViewById(R.id.image_btn2_1));
        gameBoard.add(item2_2 = (ImageButton) findViewById(R.id.image_btn2_2));
        gameBoard.add(item2_3 = (ImageButton) findViewById(R.id.image_btn2_3));
        gameBoard.add(item2_4 = (ImageButton) findViewById(R.id.image_btn2_4));
        gameBoard.add(item2_5 = (ImageButton) findViewById(R.id.image_btn2_5));
        gameBoard.add(item2_6 = (ImageButton) findViewById(R.id.image_btn2_6));

        gameBoard.add(item3_1 = (ImageButton) findViewById(R.id.image_btn3_1));
        gameBoard.add(item3_2 = (ImageButton) findViewById(R.id.image_btn3_2));
        gameBoard.add(item3_3 = (ImageButton) findViewById(R.id.image_btn3_3));
        gameBoard.add(item3_4 = (ImageButton) findViewById(R.id.image_btn3_4));
        gameBoard.add(item3_5 = (ImageButton) findViewById(R.id.image_btn3_5));
        gameBoard.add(item3_6 = (ImageButton) findViewById(R.id.image_btn3_6));

        gameBoard.add(item4_1 = (ImageButton) findViewById(R.id.image_btn4_1));
        gameBoard.add(item4_2 = (ImageButton) findViewById(R.id.image_btn4_2));
        gameBoard.add(item4_3 = (ImageButton) findViewById(R.id.image_btn4_3));
        gameBoard.add(item4_4 = (ImageButton) findViewById(R.id.image_btn4_4));
        gameBoard.add(item4_5 = (ImageButton) findViewById(R.id.image_btn4_5));
        gameBoard.add(item4_6 = (ImageButton) findViewById(R.id.image_btn4_6));

        gameBoard.add(item5_1 = (ImageButton) findViewById(R.id.image_btn5_1));
        gameBoard.add(item5_2 = (ImageButton) findViewById(R.id.image_btn5_2));
        gameBoard.add(item5_3 = (ImageButton) findViewById(R.id.image_btn5_3));
        gameBoard.add(item5_4 = (ImageButton) findViewById(R.id.image_btn5_4));
        gameBoard.add(item5_5 = (ImageButton) findViewById(R.id.image_btn5_5));
        gameBoard.add(item5_6 = (ImageButton) findViewById(R.id.image_btn5_6));

        gameBoard.add(item6_1 = (ImageButton) findViewById(R.id.image_btn6_1));
        gameBoard.add(item6_2 = (ImageButton) findViewById(R.id.image_btn6_2));
        gameBoard.add(item6_3 = (ImageButton) findViewById(R.id.image_btn6_3));
        gameBoard.add(item6_4 = (ImageButton) findViewById(R.id.image_btn6_4));
        gameBoard.add(item6_5 = (ImageButton) findViewById(R.id.image_btn6_5));
        gameBoard.add(item6_6 = (ImageButton) findViewById(R.id.image_btn6_6));

        resetButton = (Button) findViewById(R.id.reset_btn);
        resetButton.setOnClickListener(resetListener);

        //assign and shuffle the cards
        assignCards(false);

    }
}