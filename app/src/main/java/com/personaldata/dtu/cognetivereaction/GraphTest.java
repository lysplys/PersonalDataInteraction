package com.personaldata.dtu.cognetivereaction;

/**
 * Created by lyspl on 15-02-2017.
 */

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GraphTest extends AppCompatActivity {
    String[] ar;
    int int_av1;
    List<Integer> myList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);

        //get sdcard
        File sdcard = Environment.getExternalStorageDirectory();

        //Get text file
        File file = new File(sdcard,"PDI" +File.separator + "Logs"+ File.separator + "log_data.txt");

        //Read text from file
       // StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));


            String line = br.readLine();
            if(line != null) {
                //for testing file output
                //text.append(line);

                //for graph data
                ar=line.split(",");
                //myList = new ArrayList<Integer>();

                String date = ar[0];

                //for(int i = 1; i<11; i++) {

                    //myList.add(Integer.parseInt(ar[i]));

                //}



            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        //Find the view by its id
       // TextView datatest = (TextView)findViewById(R.id.datatest);

//Set the text
        //datatest.setText(text);


//graph1
       GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setTitle("Trials");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Trial");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Time");


        DataPoint[] dataPoints = new DataPoint[10]; // declare an array of DataPoint objects with the same size as your list
        for (int i = 0; i < 10; i++) {

            // add new DataPoint object to the array for each of your list entries

            dataPoints[i] = new DataPoint(i, Float.parseFloat(ar[i])); // not sure but I think the second argument should be of type double
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);

        Log.i("graphtest", ar[1]);
        graph.addSeries(series);

        // styling series
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        //series.setDataPointsRadius(10);

        // custom paint to make a dotted line
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));

        series.setCustomPaint(paint);
    }


}

