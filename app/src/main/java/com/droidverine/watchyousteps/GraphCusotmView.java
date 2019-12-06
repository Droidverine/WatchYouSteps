package com.droidverine.watchyousteps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class GraphCusotmView extends View {
    public static boolean LINE = true;

    private Paint paint;
    private float[] values;
    private String[] str;
    private String[] verlabels;
    private String title;
    private boolean type;
    Context context;

    public GraphCusotmView(Context context) {
        super(context);
    }

    public GraphCusotmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphCusotmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GraphCusotmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int cellWidth = 100 /100 ;

        paint.setColor(Color.rgb(235, 52, 143));
        paint.setStrokeWidth(10);

        canvas.drawRect(getWidth()/8.1f,0, getWidth()/8.1f, getHeight(),paint);
        //vertical
        canvas.drawLine(getWidth()/8.1f,0, getWidth()/8.1f,  getHeight()/2,paint);
        // horizantal
        canvas.drawLine(getWidth()/8.1f,getHeight()/2, getWidth(), getHeight()/2,paint);
        //canvas.drawOval(0,0,1000,1000,paint);
        Paint myPaint = new Paint();

        myPaint.setStrokeWidth(50);
        myPaint.setColor(Color.BLACK);
      //  canvas.drawLine(getWidth()/8.1f,getHeight()/2, 221, 222,paint);
        /*
        Path path = new Path();
        path.moveTo(getWidth()/8.1f,getHeight()/2);
       // canvas.drawPath(path, paint);
        path.lineTo(111, 333);


        paint.setStrokeWidth(50);
        paint.setColor(Color.BLACK);
        canvas.drawPath(path,paint);
        path.close();
        */
        Path path = new Path();
       /// path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(getWidth()/8.1f, getHeight()/2);          //<----- keep only this call to moveTo()
       // path.lineTo(getWidth()/8.1f , getHeight());
          //<----- remove this call
       // path.close();
        canvas.drawPath(path,paint);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        textPaint.setColor(0xFF000000);
        for(int i=1;i<100;i++)
        {
            Log.d("widthghe",""+i);
        canvas.drawCircle((getWidth()/8.1f)*i,(getHeight()/2), 16, paint);

       //     canvas.drawText(""+i,(getWidth()/8.1f)*i,(getHeight()/2)+60, textPaint);

            canvas.drawText(""+i,((getWidth()/8.1f)*i)+(i + 1) * cellWidth,(getHeight()/2)+60, textPaint);

        }
        for(int i=10;i>0;i--)
        {
           // canvas.drawCircle((getWidth()/8.1f),(getHeight()/2)/i, 25, paint);
        }
        canvas.translate(getWidth()/8.1f, getHeight()/2);
        for(int j=0;j<10;j++)
        {
          //  canvas.drawLine((getWidth()/8.1f)*k,(getHeight()/2), 25, paint);
             //canvas.drawLine((*j,(getHeight()/2)-50, (getWidth()/8.1f)*j+50,(getWidth()/8.1f)*j+50, paint);

        }

    }}
