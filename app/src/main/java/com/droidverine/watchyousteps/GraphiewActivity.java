package com.droidverine.watchyousteps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class GraphiewActivity extends AppCompatActivity {
    ArrayList<Integer> imageslist;
    int maxval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphiew);

        Intent intent=getIntent(); imageslist = (ArrayList<Integer>) getIntent().getSerializableExtra("perkm");
        Log.d("Arrayala",""+imageslist.toString());
        Double abc=intent.getDoubleExtra("totdis",0.0);
        Log.d("Max","he act"+ abc);
         maxval=(int) Math.round(abc);
        Log.d("Max","he Int"+ maxval);
        GraphCusotmView graphCusotmView=new GraphCusotmView(getApplicationContext(),imageslist,maxval);
        graphCusotmView.setValues(imageslist,maxval);





    }
    public ArrayList<Integer> getarray()
    {
        return imageslist;
    }
    public Integer getmax()
    {
        return maxval;
    }
}
