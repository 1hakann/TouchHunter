package com.badlogic.androidgames.touchhunter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import androidx.annotation.Nullable;

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

    }
}
