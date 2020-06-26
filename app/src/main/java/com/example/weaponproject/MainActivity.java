package com.example.weaponproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    ImageView imageView;
    Button reset, calc,selectImage;
    private final int   IMG_REQUEST = 1;
    EditText editText;
    TextView textView;
    float scale = 0;
    int x, y;
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
        selectImage = findViewById(R.id.button2);
        calc = findViewById(R.id.calc);
        imageView = findViewById(R.id.imageView1);
        final ImageView iv = new ImageView(MainActivity.this);
        coordinates = new ArrayList();
        calculation = new ArrayList();
        reset = findViewById(R.id.reset);
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //imageView.setVisibility(View.INVISIBLE);
                editText.setText("");
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
        photoButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });

        PhotoViewAttacher photoView = new PhotoViewAttacher(imageView);
        photoView.update();
        photoView.setOnScaleChangeListener(new PhotoViewAttacher.OnScaleChangeListener() {
            @Override
            public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                scale = scaleFactor;
                System.out.println(scaleFactor);
            }
        });

        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                iv.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                if (calculation.size() == 8) {
                    Toast.makeText(MainActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
                } else {
                    if ((int) scale == 0) {
                        scale = 1;
                    }
                    calculation.add((double) x / scale);
                    calculation.add((double) y / scale);

                    System.out.println(x + " " + y);


                    Bitmap bm = BitmapFactory.decodeResource(getResources(),
                            R.drawable.name);
                    iv.setImageBitmap(bm.createScaledBitmap(bm, 135, 135, true));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    params.leftMargin = (int) (x - 65);
                    params.topMargin = (int) (y + 60);


                    if (iv.getParent() != null) {
                        ((ViewGroup) iv.getParent()).removeView(iv); // <- fix
                    }

                    rl.addView(iv, params);
                }
            }
        });

    }

    public void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    public void calculate() {
        try {
            double a = calculation.get(0);
            double b = calculation.get(1);
            double c = calculation.get(2);
            double d = calculation.get(3);
            double e = calculation.get(4);
            double f = calculation.get(5);
            double g = calculation.get(6);
            double h = calculation.get(7);

            double answer1 = Math.sqrt(Math.pow((a - c), 2) + Math.pow((b - d), 2));
            double answer2 = Math.sqrt(Math.pow((e - g), 2) + Math.pow((f - h), 2));
            double answer3 = Double.parseDouble(editText.getText().toString());
            double answer4 = ((answer3 / answer1) * answer2);
            calculation.clear();
            editText.setText("");
            textView.setText("" + answer4);
            textView.setVisibility(View.VISIBLE);

            double p1 = -5.995;
            double p2 = 829.5;
            double p3 = -5554;
            double q1 = -1.69;
            double q2 = -40.59;

            double answer5 = (p1 * Math.pow(answer4,2) + p2 * answer4 + p3) / (Math.pow(answer4,2) - q1 * answer4 + q2);
            editText.setText(""+answer5);

        } catch (Exception e) {
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
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap,330, 330, true);
                imageView.setImageBitmap(resized);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            imageView.setVisibility(View.VISIBLE);
        }



        super.onActivityResult(requestCode, resultCode, data);
    }
}