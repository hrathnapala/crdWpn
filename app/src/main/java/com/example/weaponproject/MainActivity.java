package com.example.weaponproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    ImageView imageView;
    Button button,reset,calc;
    EditText editText;
    TextView textView;
    int x,y;
    ArrayList<Integer> coordinates;
    ArrayList<Double> calculation;
    double i = 0;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.answer);
        editText = findViewById(R.id.unit);
        calc = findViewById(R.id.calc);
        button = findViewById(R.id.btn);
        imageView = findViewById(R.id.imageView1) ;
        final ImageView iv = new ImageView(MainActivity.this);
        coordinates = new ArrayList();
        calculation = new ArrayList();
        reset = findViewById(R.id.reset);
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        //final RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
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
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.INVISIBLE);
                calculation.clear();
                coordinates.clear();
                textView.setVisibility(View.GONE);
                iv.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Coordinated deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
            }
        });
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                iv.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                if(calculation.size() == 8){
                    Toast.makeText(MainActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
                }else {
                    System.out.println(coordinates.size());
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

                    calculation.add((double) x);
                    calculation.add((double) y);

                    System.out.println(x + " " + y);



                    Bitmap bm = BitmapFactory.decodeResource(getResources(),
                            R.drawable.name);
                    iv.setImageBitmap(bm.createScaledBitmap(bm, 135, 135, true));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    params.leftMargin = x - 65;
                    params.topMargin = y + 58;


                    if (iv.getParent() != null) {
                        ((ViewGroup) iv.getParent()).removeView(iv); // <- fix
                    }

                    rl.addView(iv, params);
                }

                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double b = getScreenDistance(coordinates.get(0),coordinates.get(1),coordinates.get(2),coordinates.get(3));
                    calculation.add(b);
                    System.out.println(b*2.54);
                    textView.setText("" + b*2.54);
                    textView.setVisibility(View.VISIBLE);
                    coordinates.clear();
                    iv.setVisibility(View.GONE);
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, ",", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    public void calculate(){
        try {
            double a = calculation.get(0);
            double b = calculation.get(1);
            double c = calculation.get(2);
            double d = calculation.get(3);
            double e = calculation.get(4);
            double f = calculation.get(5);
            double g = calculation.get(6);
            double h = calculation.get(7);

            double answer1 = Math.sqrt(Math.pow((a-c),2) + Math.pow((b-d),2) );
            double answer2 = Math.sqrt(Math.pow((e-g),2) + Math.pow((f-h),2) );
            double answer3 = Double.parseDouble(editText.getText().toString());
            double answer4 = ((answer3/answer1)*answer2);
            calculation.clear();
            editText.setText("");
            textView.setText("" + answer4);
            textView.setVisibility(View.VISIBLE);

        }catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

        super.onActivityResult(requestCode, resultCode, data);
    }
}