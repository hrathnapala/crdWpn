package com.example.weaponproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    int x,y;
    ArrayList<Integer> coordinates;
    double i = 0;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        imageView = findViewById(R.id.imageView) ;
        final ImageView iv = new ImageView(MainActivity.this);
        coordinates = new ArrayList();
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
//        final PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
//        photoView.setImageResource(R.drawable.ic_launcher_background);
//        photoView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                x = (int) motionEvent.getX();
//                y = (int) motionEvent.getY();
//                int[] viewCoords = new int[2];
//                int touchX = (int) motionEvent.getX();
//                int touchY = (int) motionEvent.getY();
//
//                int imageX = touchX - viewCoords[0]; // viewCoords[0] is the X coordinate
//                int imageY = touchY - viewCoords[1]; // viewCoords[1] is the y coordinate
//                photoView.getLocationOnScreen(viewCoords);
//
//                coordinates.add(x);
//                coordinates.add(y);
//                System.out.println(x + " " + y);
//                return false;
//            }
//        });
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                x = (int) motionEvent.getX();
                y = (int) motionEvent.getY();
                int[] viewCoords = new int[2];
                int touchX = (int) motionEvent.getX();
                int touchY = (int) motionEvent.getY();

                int imageX = touchX - viewCoords[0]; // viewCoords[0] is the X coordinate
                int imageY = touchY - viewCoords[1]; // viewCoords[1] is the y coordinate
                imageView.getLocationOnScreen(viewCoords);
                coordinates.add(x);
                coordinates.add(y);
                System.out.println(x + " " + y);
                RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);


                Bitmap bm = BitmapFactory.decodeResource(getResources(),
                        R.drawable.name);
                iv.setImageBitmap(bm.createScaledBitmap(bm,135,80,false));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                params.leftMargin = x;
                params.topMargin = y;
                if(iv.getParent() != null) {
                    ((ViewGroup)iv.getParent()).removeView(iv); // <- fix
                }

                rl.addView(iv, params);
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               double b = getScreenDistance(coordinates.get(0),coordinates.get(1),coordinates.get(2),coordinates.get(3));
                System.out.println(b*2.54);
                coordinates.clear();
                //iv.setVisibility(View.GONE);

            }
        });

    }
    public double getScreenDistance(double x1, double y1, double x2, double y2) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double xDist = Math.pow(Math.abs(x1 - x2) / dm.xdpi, 2);
        double yDist = Math.pow(Math.abs(y1 - y2) / dm.ydpi, 2);
        return Math.sqrt(xDist + yDist);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }

        return false;
    }
}