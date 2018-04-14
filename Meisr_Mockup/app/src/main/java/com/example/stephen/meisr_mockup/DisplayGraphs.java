package com.example.stephen.meisr_mockup;

import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by chasefeaster on 4/14/18.
 */

public class DisplayGraphs extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_graphs);


        GraphView graph = findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1,85),
                new DataPoint(2,75)
        });
        series.setColor(Color.BLUE);
        series.setTitle("Up to Age");
        BarGraphSeries<DataPoint> s2 = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1,83.6),
                new DataPoint(2,70)
        });
        s2.setColor(Color.RED);
        s2.setTitle("Total Survey");
        series.setSpacing(25);
        s2.setSpacing(25);

        graph.addSeries(series);
        graph.addSeries(s2);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(11);
        graph.getViewport().setMinX(0);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(100);
        graph.getViewport().setMinY(0);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Percentage of 3's");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.setTitle("Routine");
        graph.setTitleTextSize(150);
    }

}
