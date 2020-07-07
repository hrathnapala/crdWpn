package com.example.weaponproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class LineView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private PointF pointA, pointB, pointC, pointD;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, paint);
        if (pointC != null && pointD != null) {
            canvas.drawLine(pointC.x, pointC.y, pointD.x, pointD.y, paint);
            pointC=null;
            pointD=null;
        }

        super.onDraw(canvas);
    }

    public void setPointA(PointF point) {
        pointA = point;
    }

    public void setPointB(PointF point) {
        pointB = point;
    }

    public void setPointC(PointF point) {
        pointC = point;
    }

    public void setPointD(PointF point) {
        pointD = point;
    }


    public void draw() {
        invalidate();
        requestLayout();
    }
}
