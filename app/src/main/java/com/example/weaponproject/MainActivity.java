package com.example.weaponproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

    private LineView mLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.answer);
        editText = findViewById(R.id.unit);
        selectImage = findViewById(R.id.button2);
        calc = findViewById(R.id.calc);
        done = findViewById(R.id.done);

        mLineView = (LineView) findViewById(R.id.lineView);
        mLineView.setVisibility(View.GONE);


        //imageView = findViewById(R.id.imageView1);
        final ImageView iv = new ImageView(MainActivity.this);
        coordinates = new ArrayList();
        calculation = new ArrayList();
        reset = findViewById(R.id.reset);

        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        //imageView.setVisibility(View.INVISIBLE);

        done.setVisibility(View.GONE);


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
                mLineView.setVisibility(View.GONE);
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
                    params.topMargin = (int) (y + 85);


                    coordinates.add((int) (x));
                    coordinates.add((int) (y));

                    if (coordinates.size() == 4) {
                        mLineView.setVisibility(View.VISIBLE);
                        mLineView.setPointA(new PointF(coordinates.get(0), coordinates.get(1)));
                        mLineView.setPointB(new PointF(coordinates.get(2), coordinates.get(3)));
                        mLineView.draw();

                    } else if (coordinates.size() == 8) {
                        //mLineView.setVisibility(View.VISIBLE);
                        mLineView.setPointC(new PointF(coordinates.get(4), coordinates.get(5)));
                        mLineView.setPointD(new PointF(coordinates.get(6), coordinates.get(7)));
                        mLineView.draw();

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
                reset.setVisibility(View.VISIBLE);
                calc.setVisibility(View.VISIBLE);
                done.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.INVISIBLE);
                coordinates.clear();
                mLineView.setVisibility(View.GONE);
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
                double answer4 = ((answer3 / answer1) * answer2) * 10;
                calculation.clear();
                textView.setVisibility(View.VISIBLE);

                double p1 = -5.995;
                double p2 = 829.5;
                double p3 = -5554;
                double q1 = -1.69;
                double q2 = -40.59;
                reset.setVisibility(View.GONE);
                calc.setVisibility(View.GONE);

                double answer5 = (p1 * Math.pow(answer4, 2) + p2 * answer4 + p3) / (Math.pow(answer4, 2) - q1 * answer4 + q2);
                textView.setText("Length: " + String.format("%.2f", answer4) + " mm" + "\n" + "Angle: " + String.format("%.2f", answer5) + "°");
                done.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            Toast.makeText(this, "Wrong Input", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 330, 330, true);
                photoView.setImageBitmap(resized);
                photoView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Bitmap resized = Bitmap.createScaledBitmap(photo, 330, 330, true);
            photoView.setImageBitmap(resized);
            photoView.setVisibility(View.VISIBLE);

        }


        super.onActivityResult(requestCode, resultCode, data);
    }


}