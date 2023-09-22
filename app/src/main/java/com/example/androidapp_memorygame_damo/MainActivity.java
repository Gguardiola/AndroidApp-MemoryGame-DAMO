package com.example.androidapp_memorygame_damo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageButton item1_1, item1_2, item1_3, item1_4, item1_5, item1_6;
    private ImageButton item2_1, item2_2, item2_3, item2_4, item2_5, item2_6;
    private ImageButton item3_1, item3_2, item3_3, item3_4, item3_5, item3_6;
    private ImageButton item4_1, item4_2, item4_3, item4_4, item4_5, item4_6;
    private ImageButton item5_1, item5_2, item5_3, item5_4, item5_5, item5_6;
    private ImageButton item6_1, item6_2, item6_3, item6_4, item6_5, item6_6;
    Random rand = new Random();


    ArrayList<ImageButton> gameBoard = new ArrayList<ImageButton>();
    int cardIdentifier[];
    int MAX_SIZE = 36;

    //Listeners
    private View.OnClickListener flipElementHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ImageButton b= (ImageButton) view;
            b.setImageResource(R.drawable.monster_portrait_crystal);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        for(ImageButton item : gameBoard){
            item.setOnClickListener(flipElementHandler);
            item.setTag();
        }
 //LISTENERS
    }
}