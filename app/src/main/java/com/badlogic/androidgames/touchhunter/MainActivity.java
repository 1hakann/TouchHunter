package com.badlogic.androidgames.touchhunter;

import android.accounts.NetworkErrorException;
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
    int gridW = 30;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // 5- Let's initialize our variables. we will use one class. That's why I didn't need to create a constructor.
        horPixels = size.x;
        verPixels = size.y;
        block = horPixels / gridW;
        gridH = verPixels / block;

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
        monsterHorPos = random.nextInt(gridW);
        monsterVerPos = random.nextInt(gridH);
        shotsTaken = 0;

        Log.d("Debugging","In NewGame");
    }

    // 7- Let's code Draw methods
    void Draw()
    {
        imageView.setImageBitmap(bitmap);
        canvas.drawColor(Color.argb(255,167,184,65));
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
        paint.setTextSize(block*2);
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
        touchH = (int)touchX/block;
        touchV = (int)touchY/block;

        isHit = touchH == monsterHorPos && touchV == monsterVerPos;

        // Let's calculate the stroke, we will apply Pythagoras
        int horGap = (int)(touchH - monsterHorPos);
        int verGap = (int)(touchV - monsterVerPos);

        farFromMonster = (int) Math.sqrt((horGap * horGap) + (verGap * verGap));

        if(isHit) {
            Boom();
        } else {
            Draw();
        }
    }

    // 10- Let's Boom method coding now. This code says "BOOM!"
    void Boom()
    {
        // Let's create an image and call
        imageView.setImageBitmap(bitmap);
        canvas.drawColor(Color.argb(255, 145, 21,85));
        paint.setColor(Color.argb(255,255,255,255));
        paint.setTextSize(block*8);
        canvas.drawText("BOOOM",block, block*10, paint);

        // Draw some text to prompt restarting
        paint.setTextSize(block * 2);
        canvas.drawText("Tekrar Başlamak İçin Dokunun", block*2, block*16, paint);

        NewGame();
    }
    
    void DebuggingPrintText()
    {
        paint.setTextSize(block);
        canvas.drawText("horPixels = "
                        + horPixels,
                50, block * 3, paint);
        canvas.drawText("verPixels = "
                        + verPixels,
                50, block * 4, paint);
        canvas.drawText("block = " + block,
                50, block * 5, paint);
        canvas.drawText("gridW = " + gridW,
                50, block * 6, paint);
        canvas.drawText("gridH = " + gridH,
                50, block * 7, paint);
        canvas.drawText("touchH = " +
                        touchH, 50,
                block * 8, paint);
        canvas.drawText("touchV = " +
                        touchV, 50,
                block * 9, paint);
        canvas.drawText("monsterHorPos = " +
                        monsterHorPos, 50,
                block * 10, paint);
        canvas.drawText("subVerticalPosition = " +
                        monsterVerPos, 50,
                block * 11, paint);
        canvas.drawText("hit = " + isHit,
                50, block * 12, paint);
        canvas.drawText("shotsTaken = " +
                        shotsTaken,
                50, block * 13, paint);
        canvas.drawText("debugging = " + isDebug,
                50, block * 14, paint);
    }
}
