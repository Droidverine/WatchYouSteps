package com.droidverine.watchyousteps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    int StartX,StartY,EndX,EndY;
    ArrayList<Integer> PerkmArraylist=new ArrayList<>();
    static ArrayList<Integer> speedmarks=new ArrayList<>();

    int MAX_KM=1;
   private  int MAX_SPEED=25;

     int BORDER_GAP=5 ;//= 20;

    private static final int GRAPH_POINT_WIDTH = 12;
    static List<Integer> eachkmArraylist= new ArrayList<>();
    ;
    private  int Y_HATCH_CNT;




    public GraphCusotmView(Context context,ArrayList<Integer> PerkmArraylist,int MAX_KM) {
        super(context);
        init();


    }
    public void setValues(ArrayList<Integer> PerkmArraylist, int MAX_KM) {

         this.PerkmArraylist=PerkmArraylist;
        this.MAX_KM=MAX_KM;
        Log.d("MaxKM",""+MAX_KM);

        // invalidate();

    }

    public void setMAX_KM(int MAX_KM) {
        invalidate();
        this.MAX_KM = MAX_KM;
    }

    public int getMAX_KM()
    {
        return MAX_KM;
    }

    public GraphCusotmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public GraphCusotmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public GraphCusotmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setPaddingRelative(100,100,100,100);
       
    }

    public void init()
{
   // this.MAX_KM=getMAX_KM();
    Log.d("Init",""+MAX_KM);

  //  invalidate();

}


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("redraw","ll");
        MainActivity mainActivity=new MainActivity();
        Log.d("perkm",""+mainActivity.getPerkmArraylist());
        //eachkmArraylist.addAll(mainActivity.getPerkmArraylist());

        eachkmArraylist=new ArrayList<Integer>(mainActivity.getPerkmArraylist());
        super.onDraw(canvas);
        if(MAX_KM!=0 && eachkmArraylist!=null){
            MAX_KM=eachkmArraylist.size();

//            MAX_SPEED=Integer.valueOf(Collections.max(eachkmArraylist));
      //  canvas.clipRect(minX, minY, maxX, maxY, Region.Op.REPLACE);

        StartX=30;
        StartY=20;


        GraphiewActivity graphCusotmView=new GraphiewActivity();
        //eachkmArraylist=graphCusotmView.getarray();
        //MAX_SPEED=graphCusotmView.getmax();
        Canvas g2=canvas;
        if(eachkmArraylist!=null) {
           // MAX_KM=eachkmArraylist.size();

            MAX_SPEED = mainActivity.getmaxspeed();
        }else {
            MAX_SPEED=200;
        }

            Log.d("Maxkm",""+MAX_KM);

        /*
        for(int i=0;i<PerkmArraylist.size();i++)
        {
            eachkmArraylist.add((int) Math.round(PerkmArraylist.get(i)));
        }
*/
        /*
        for(int i=0;i<PerkmArraylist.size();i++)
        {
            eachkmArraylist.add((int) Math.round(PerkmArraylist.get(i)));
        }


        eachkmArraylist.add(25);
        eachkmArraylist.add(1);

        eachkmArraylist.add(5);
        eachkmArraylist.add(10);
            eachkmArraylist.add(20);

        eachkmArraylist.add(1);

        eachkmArraylist.add(20);
        eachkmArraylist.add(205);

        eachkmArraylist.add(10);

        eachkmArraylist.add(5);
        eachkmArraylist.add(1);

*/

        Y_HATCH_CNT= eachkmArraylist.size();
            split(MAX_SPEED,Y_HATCH_CNT,canvas);

            TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(44);
        textPaint.setColor(0xFF000000);


        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (MAX_KM - 1);
        double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SPEED - 1);

        List<Point> graphPoints = new ArrayList<Point>();
        for (int i = 0; i < eachkmArraylist.size(); i++) {
            int x1 = (int) (i * xScale + BORDER_GAP);
            int y1 = (int) ((MAX_SPEED - eachkmArraylist.get(i)) * yScale + BORDER_GAP);
            graphPoints.add(new Point(x1, y1));

        }
           // Log.d("points",""+graphPoints.toString());

           int minX = getPaddingLeft();
            int maxX = getWidth() - getPaddingLeft() - getPaddingRight();
            int minY = getPaddingTop();
            int maxY = getHeight() - getPaddingTop() - getPaddingBottom();
            // create x and y axes

        g2.drawLine(BORDER_GAP, (getHeight() - BORDER_GAP), BORDER_GAP, BORDER_GAP,paint);
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP,paint);

        // create hatch marks for y axis.
            if(speedmarks.size()>0)
            {
        for (int i = 0; i < speedmarks.size(); i++) {
            int x0 = BORDER_GAP;
            int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
            int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
            int y1 = y0;
            g2.drawLine(x0, y0, x1, y1,paint);
            //Log.d("valv",""+MAX_SPEED/(5-i));
                g2.drawText(""+speedmarks.get(i),x0+28,y0+28,textPaint);
            Log.d("valv",""+speedmarks.get(0));






        }
                Log.d("speedmarks",""+speedmarks.toString());

            }
        // and for x axis
        for (int i = 0; i < MAX_KM-1; i++) {
            int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (MAX_KM - 1) + BORDER_GAP;
            int x1 = x0;
            int y0 = getHeight() - BORDER_GAP;
            int y1 = y0 - GRAPH_POINT_WIDTH;
            g2.drawLine(x0, y0, x1, y1,paint);
            g2.drawText(""+(i+1),x0-28,y0-28,textPaint);

        }

        //Stroke oldStroke = g2.getStroke();
        //g2.setColor(GRAPH_COLOR);
       // g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2,paint);
        }

       // g2.setStroke(oldStroke);
        //g2.setColor(GRAPH_POINT_COLOR);
          //  Log.d("points",""+graphPoints.toString());

        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
            int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            g2.drawCircle(x, y, ovalW, paint);
        //    g2.drawText(""+i,x,y+100,textPaint);
        }}
    }
    public void split(int x, int n, Canvas canvas)
    {

    int dif=x/n;
       // speedmarks.add(x);
        speedmarks.clear();

        for(int i=x;i>=0;i=i-dif)
    {
            if(i>4)
            {
            speedmarks.add(i);
            }

       // Log.d("points",""+i);
    }
        Log.d("points",speedmarks.toString());
        Collections.reverse(speedmarks);
// If we cannot split the
// number into exactly 'N' parts
        if(x < n)
            System.out.print("-1 ");



            // If x % n == 0 then the minimum
            // difference is 0 and all
            // numbers are x / n
        else if (x % n == 0)
        {
            for(int i=0;i<n;i++)
                Log.d("s",""+x/n );
        }
        else
        {

            // upto n-(x % n) the values
            // will be x / n
            // after that the values
            // will be x / n + 1
            int zp = n - (x % n);
            int pp = x/n;
            for(int i=0;i<n;i++)
            {

                if(i>= zp)
                    Log.d("s",""+(pp + 1) );
                else
                    Log.d("s",""+pp );
            }
        }
        invalidate();


    }
}
