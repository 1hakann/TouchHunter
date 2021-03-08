package com.badlogic.androidgames.touchhunter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.Random;

public class MainActivity extends Activity {

    // 1- Let's extend activity class
    // 2- Let's add our previously determined variables.
    int horPixels;
    int verPixels;
    int block;
    int gridW;
    int gridH;
    float touchV = -100;
    float touchH = -100;
    int shotsTaken;
    boolean isHit = false;
    boolean isDebug = false;
    int monsterVerPos;
    int monsterHorPos;
    int farFromMonster;

    // 3- Let's add the required classes for drawing
    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;

    // 4- We will now override onCreate. and we will link our classes and objects that allow us to display images on the screen.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // 5- Let's initialize our variables. we will use one class. That's why I didn't need to create a constructor.
        horPixels = size.x;
        verPixels = size.y;
        gridW = horPixels / block;
        block = verPixels / gridH;

        bitmap = Bitmap.createBitmap(horPixels,verPixels,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        imageView = new ImageView(this);

        setContentView(imageView);

        Log.d("Debugging","In onCreate");
        NewGame();
        Draw();
    }

    // 6- Let's code newGame methods
    void NewGame()
    {
        Random random = new Random();
        monsterHorPos = random.nextInt(gridH);
        monsterVerPos = random.nextInt(gridW);
        shotsTaken = 0;

        Log.d("Debugging","In NewGame");
    }

    // 7- Let's code Draw methods
    void Draw()
    {
        imageView.setImageBitmap(bitmap);
        canvas.drawColor(Color.argb(232,167,184,65));
        paint.setColor(Color.argb(255,0,0,0));

        // 7.1- Draw the vertical lines of the grid
        for (int i=0; i<gridW; i++)
        {
            canvas.drawLine(block*i,0, block*i, verPixels, paint);
        }

        // 7.2- Draw the horizontal lines of the grid
        for (int i=0; i<gridH; i++)
        {
            canvas.drawLine(0, block*i, horPixels, block*i, paint);
        }

        // 7.3- Draw the player's shot
        canvas.drawRect(touchH*block, touchV*block,(touchH*block)+block, (touchV*block)+block, paint);

        // 7.4- Re-size the text appropriate for the score and distance text
        paint.setTextSize(block*3);
        paint.setColor(Color.argb(255,0,0,255));
        canvas.drawText("Vuruş Sayısı: "+shotsTaken + " Mesafe: "+farFromMonster, block, block * 2f, paint);

        Log.d("Debugging", "In Draw");

        if (isDebug)
        {
            DebuggingPrintText();
        }
    }
    // 8- onTouchEvent i override edelim
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Debugging", "In onTouchEvent");
        // Has the player removed their finger from the screen?
        if((event.getAction() & event.ACTION_MASK) == event.ACTION_UP)
        {
            Shoot(event.getX(), event.getY());
        }
        return true;
    }

    // 9- Shoot metodunu kodlayalım
    void Shoot(float touchX, float touchY)
    {
        Log.d("Debugging", "In Shoot");

        shotsTaken++;
        touchH = (int)block/touchX;
        touchV = (int)block/touchY;

        isHit = touchH == monsterHorPos && touchV == monsterVerPos;

        // Let's calculate the stroke, we will apply Pythagoras
        int horGap = (int)(touchH - touchX);
        int verGap = (int)(touchV - touchY);

        farFromMonster = (int) Math.sqrt((horGap * horGap) + (verGap * verGap));

        if(isHit) {
            Boom();
        } else {
            Draw();
        }
    }

    // 10- Let's Boom method coding now. This code says "BOOM!"

}
