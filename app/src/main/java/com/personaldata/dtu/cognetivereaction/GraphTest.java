package com.personaldata.dtu.cognetivereaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class GraphTest extends AppCompatActivity {

    String[] splitLine;
    String getLastLine, getLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);

        //get sdcard
        File sdcard = Environment.getExternalStorageDirectory();

        //Get text file
        File file = new File(sdcard, "PDI" + File.separator + "Logs"+ File.separator + "log_data.txt");

        //Read text from file
        // StringBuilder text = new StringBuilder();

        try {
            BufferedReader getFile = new BufferedReader(new FileReader(file));

            while ((getLine = getFile.readLine()) != null) {
                getLastLine = getLine;
            }

            splitLine = getLastLine.split(",");
            //String getTimeStamp = getLastLine.substring(getLastLine.lastIndexOf(",") + 1);

            getFile.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        /*
        String test = "0.2, 0.4, 0.56, 0.34, 0.356, 123123123";
        String[] testSplit = test.split(",");
        */

        Log.i("GraphTest", "First element: " + splitLine[0] + ", Last element: " + splitLine[splitLine.length - 1] + "");

        double scoreAvg = 0.0;
        double sum = 0;
        for(int i = 0; i < splitLine.length - 1; i++) {
            sum += Float.parseFloat(splitLine[i]);
        }

        scoreAvg = 1.0d * sum / (splitLine.length - 1);

        TextView averageData = (TextView)findViewById(R.id.textView);
        averageData.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.###").format(scoreAvg))) + " s");

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setTitle("Reaction time");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Trials");
        //graph.getGridLabelRenderer().setVerticalAxisTitle("Time");
        graph.getGridLabelRenderer().setNumHorizontalLabels(splitLine.length - 1);

        DataPoint[] dataPoints = new DataPoint[splitLine.length - 1]; // declare an array of DataPoint objects with the same size as your list
        for (int i = 0; i < splitLine.length - 1; i++) {
            // add new DataPoint object to the array for each of your list entries
            dataPoints[i] = new DataPoint(i + 1, Float.parseFloat(splitLine[i])); // not sure but I think the second argument should be of type double
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints); // This one should be obvious right? :)

        /*
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        */
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

    public void restartTrials(View view)
    {
        Intent intent = new Intent(GraphTest.this, MainActivity.class);
        startActivity(intent);
    }

}
