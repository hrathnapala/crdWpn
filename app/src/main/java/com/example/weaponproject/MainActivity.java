package com.example.weaponproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.OnScaleChangedListener;
import com.github.chrisbanes.photoview.OnViewTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import com.viven.imagezoom.ImageZoomHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//import uk.co.senab.photoview.PhotoView;
//import uk.co.senab.photoview.PhotoViewAttacher;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    ImageView imageView;
    Button reset, calc, selectImage, done;
    private final int IMG_REQUEST = 1;
    EditText editText;
    TextView textView;
    float scale = 0, scalex = 0, scaley = 0;
    int x, y;
    ArrayList<Integer> coordinates;
    ArrayList<Double> calculation;
    double i = 0;
    Bitmap bitmap;
    PhotoView photoView;
    PointF pointA = new PointF(100, 600);
    PointF pointB = new PointF(500, 400);


    private String currentPhotoPath;

    private static final int PERMISSION_CODE = 1001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.answer);
        editText = findViewById(R.id.unit);
        selectImage = findViewById(R.id.button2);
        //calc = findViewById(R.id.calc);
        done = findViewById(R.id.done);







        //imageView = findViewById(R.id.imageView1);
        final ImageView iv = new ImageView(MainActivity.this);
        coordinates = new ArrayList();
        calculation = new ArrayList();
       // reset = findViewById(R.id.reset);

        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        //imageView.setVisibility(View.INVISIBLE);

        done.setVisibility(View.GONE);


        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
//        reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //imageView.setVisibility(View.INVISIBLE);
//                mLineView.setVisibility(View.GONE);
//                editText.setText("");
//                calculation.clear();
//                coordinates.clear();
//                textView.setVisibility(View.GONE);
//                iv.setVisibility(View.GONE);
//                Toast.makeText(MainActivity.this, "Coordinated deleted successfully", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//        calc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculate();
//            }
//        });
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                            requestPermissions(permissions,PERMISSION_CODE);
                    }else {
                        String fileName = "dgh";
                        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        try {
                            File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                            currentPhotoPath = imageFile.getAbsolutePath();
                            Uri imageUri =  FileProvider.getUriForFile(MainActivity.this,"com.example.weaponproject.fileprovider",imageFile);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent,2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    String fileName = "dgh";
                    File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    try {
                        File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                        currentPhotoPath = imageFile.getAbsolutePath();
                        Uri imageUri =  FileProvider.getUriForFile(MainActivity.this,"com.example.weaponproject.fileprovider",imageFile);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent,2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



            }
        });

        photoView = (PhotoView) findViewById(R.id.photo_view);

//        photoView.setVisibility(View.INVISIBLE);
//        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
//
//            @Override
//            public void onPhotoTap(ImageView view, float x, float y) {
//                photoView.setOnViewTapListener(new OnViewTapListener() {
//                    @Override
//                    public void onViewTap(View view, float x, float y) {
//                        if (calculation.size() == 8) {
//                            Toast.makeText(MainActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Bitmap bm = BitmapFactory.decodeResource(getResources(),
//                                    R.drawable.name);
//                            iv.setImageBitmap(bm.createScaledBitmap(bm, 135, 135, true));
//                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                            params.leftMargin = (int) (x - 65);
//                            params.topMargin = (int) (y + 85);
//
//
//                            if (iv.getParent() != null) {
//                                ((ViewGroup) iv.getParent()).removeView(iv); // <- fix
//                            }
//
//                            rl.addView(iv, params);
//                        }
//                    }
//                });
//                System.out.println(x);
//                if (calculation.size() == 8) {
//                    Toast.makeText(MainActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
//                } else {
//                    calculation.add((double) x);
//                    calculation.add((double) y);
//                }
//            }
//        });


        photoView.setOnViewTapListener(new OnViewTapListener() {

            @Override
            public void onViewTap(View view, float x, float y) {
                iv.setVisibility(View.VISIBLE);

                photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(ImageView view, float x, float y) {
                        if (calculation.size() == 8) {
                            Toast.makeText(MainActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
                        } else {
                            calculation.add((double) x);
                            calculation.add((double) y);
                        }
                    }
                });
                if (calculation.size() == 8) {
                    Toast.makeText(MainActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
                } else {
                    Bitmap bm = BitmapFactory.decodeResource(getResources(),
                            R.drawable.name);
                    iv.setImageBitmap(bm.createScaledBitmap(bm, 135, 135, true));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    params.leftMargin = (int) (x - 65);
                    params.topMargin = (int) (y - 65); // + 45


                    coordinates.add((int) (x));
                    coordinates.add((int) (y));


                    switch (coordinates.size()){
                        case 2:
                            Toast.makeText(MainActivity.this, "1st point selected", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toast.makeText(MainActivity.this, "2nd point selected", Toast.LENGTH_SHORT).show();
                            break;
                        case 6:
                            Toast.makeText(MainActivity.this, "3rd point selected", Toast.LENGTH_SHORT).show();
                            break;
                        case 8:
                            Toast.makeText(MainActivity.this, "4th point selected", Toast.LENGTH_SHORT).show();
                            break;

                    }


                    if (coordinates.size() == 4) {
//                        mLineView.setVisibility(View.VISIBLE);
//                        mLineView.setPointA(new PointF(coordinates.get(0), coordinates.get(1)));
//                        mLineView.setPointB(new PointF(coordinates.get(2), coordinates.get(3)));
//                        mLineView.draw();


                    } else if (coordinates.size() == 8) {
//                        mLineView.setVisibility(View.VISIBLE);
//                        mLineView.setPointC(new PointF(coordinates.get(4), coordinates.get(5)));
//                        mLineView.setPointD(new PointF(coordinates.get(6), coordinates.get(7)));
//                        mLineView.draw();

                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Points saved successfully,Do you want to calculate the Distance and Angle");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        calculate();
                                        iv.setVisibility(View.GONE);
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        editText.setText("");
                                        calculation.clear();
                                        coordinates.clear();
                                        textView.setVisibility(View.GONE);
                                        iv.setVisibility(View.GONE);
                                        Toast.makeText(MainActivity.this, "Coordinated deleted successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        alertDialog.show();

                    }

                    if (iv.getParent() != null) {
                        ((ViewGroup) iv.getParent()).removeView(iv); // <- fix
                    }

                    rl.addView(iv, params);
                }

            }

        });


//        final PhotoViewAttacher photoView = new PhotoViewAttacher(imageView);
//        photoView.update();
//        photoView.setOnScaleChangeListener(new PhotoViewAttacher.OnScaleChangeListener() {
//            @Override
//            public void onScaleChange(float scaleFactor, float focusX, float focusY) {
//                scale = scaleFactor;
//                scalex = focusX;
//                scaley = focusY;
//               System.out.println(scaleFactor);
//                System.out.println(focusX + " and" + focusY);
//
//            }
//        });

//        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//
//            @Override
//            public void onPhotoTap(View view, float x, float y) {
//
//                iv.setVisibility(View.VISIBLE);
//                textView.setVisibility(View.GONE);
//                if (calculation.size() == 8) {
//                    Toast.makeText(MainActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
//                } else {
//
////                    System.out.println("X:" + x);
////                    System.out.println("Y:" + y);
//                    if ((int) scale == 0 || (int) scalex == 0 || (int) scaley == 0) {
//                        scale = 1;
//                        scalex = 1;
//                        scaley = 1;
//                    }
//
//
//                    calculation.add((double) x);
//                    calculation.add((double) y);
//
//
//                    Bitmap bm = BitmapFactory.decodeResource(getResources(),
//                            R.drawable.name);
//                    iv.setImageBitmap(bm.createScaledBitmap(bm, 135, 135, true));
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                    params.leftMargin = (int) (x * imageView.getWidth() / scale - 60);
//                    params.topMargin = (int) (y * imageView.getHeight() / scale + 80);
//
//                    if (iv.getParent() != null) {
//                        ((ViewGroup) iv.getParent()).removeView(iv); // <- fix
//                    }
//
//                    rl.addView(iv, params);
//                }
//            }
//
//            @Override
//            public void onOutsidePhotoTap() {
//
//            }
//
//
//        });


//        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
//            @Override
//            public void onViewTap(View view, float x, float y) {
//
//                iv.setVisibility(View.VISIBLE);
//                textView.setVisibility(View.GONE);
//                if (calculation.size() == 8) {
//                    Toast.makeText(MainActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
//                } else {
//                    if ((int) scale == 0 || (int) scalex == 0 || (int) scaley == 0) {
//                        scale = 1;
//                        scalex = 1;
//                        scaley = 1;
//                    }
//                    calculation.add((double) x / scale);
//                    calculation.add((double) y / scale);
////                    System.out.println("X:" + (int) (x - 65));
////                    System.out.println("Y:" + (int) (y + 60));
////
////                    System.out.println(x + " " + y);
////                    System.out.println(x + " and " + y);
//
//
//                    Bitmap bm = BitmapFactory.decodeResource(getResources(),
//                            R.drawable.name);
//                    iv.setImageBitmap(bm.createScaledBitmap(bm, 135, 135, true));
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                    params.leftMargin = (int) (x - 65);
//                    params.topMargin = (int) (y + 85);
//
//
//                    if (iv.getParent() != null) {
//                        ((ViewGroup) iv.getParent()).removeView(iv); // <- fix
//                    }
//
//                    rl.addView(iv, params);
//                }
//            }
//        });
//
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //reset.setVisibility(View.VISIBLE);
                //calc.setVisibility(View.VISIBLE);
                editText.setText("");
                photoView.setImageResource(R.drawable.ic_launcher_background);
                done.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.INVISIBLE);
                coordinates.clear();
                //mLineView.setVisibility(View.GONE);
            }
        });

    }


    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    public void calculate() {
        try {
            if (editText.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Calibration value is empty", Toast.LENGTH_SHORT).show();
                calculation.clear();
                coordinates.clear();
            } else if (calculation.size() < 8) {
                Toast.makeText(this, "Missing Coordinates", Toast.LENGTH_SHORT).show();
            } else if (Double.parseDouble(editText.getText().toString()) < 1) {
                Toast.makeText(this, "Wrong Calibration input", Toast.LENGTH_SHORT).show();
            } else {

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
                double fullImpactLength = ((answer3 / answer1) * answer2);
                calculation.clear();
                textView.setVisibility(View.VISIBLE);

                double p1 = -5.995;
                double p2 = 829.5;
                double p3 = -5554;
                double q1 = -1.69;
                double q2 = -40.59;
//                reset.setVisibility(View.GONE);
//                calc.setVisibility(View.GONE);
                double Incidence_Angle = (-5.995 * Math.pow(fullImpactLength, 2) + 829.5 * fullImpactLength - 5554) / (Math.pow(fullImpactLength, 2) - 1.69 * fullImpactLength - 40.59);
                //double answer5 = (p1 * Math.pow(answer4, 2) + p2 * answer4 + p3) / (Math.pow(answer4, 2) - q1 * answer4 + q2);
                textView.setText("Length: " + String.format("%.2f", fullImpactLength) + " mm" + "\n" + "Angle: " + String.format("%.2f", Incidence_Angle) + "°");

                done.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            Toast.makeText(this, "Wrong Input", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    String fileName = "dgh";
                    File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    try {
                        File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                        currentPhotoPath = imageFile.getAbsolutePath();
                        Uri imageUri =  FileProvider.getUriForFile(MainActivity.this,"com.example.weaponproject.fileprovider",imageFile);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent,2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(this, "Permission Denied....", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            PhotoView photoView = findViewById(R.id.photo_view);
            photoView.setVisibility(View.VISIBLE);
            photoView.setImageBitmap(bitmap);
        } else if(requestCode == 1 && resultCode == RESULT_OK) {
            photoView.setVisibility(View.VISIBLE);
            photoView.setImageURI(data.getData());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



}