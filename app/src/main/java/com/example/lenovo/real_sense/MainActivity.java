package com.example.lenovo.real_sense;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView xText_Acc,yText_Acc,zText_Acc,xText_Gyr,yText_Gyr,zText_Gyr,xText_Mag,yText_Mag,zText_Mag;
    private GraphView mGraphAcc,mGraphGyr;
    private LineGraphSeries mSeriesAccX,mSeriesAccY,mSeriesAccZ,mSeriesGyrX,mSeriesGyrY,mSeriesGyrZ;
    private double graphLastAccelXValue = 5d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGraphAcc = initGraph(R.id.graphAccel, "Accelerometer");
        mGraphGyr = initGraph(R.id.graphGyro, "Gyrometer");
        mSeriesAccX = initSeries(Color.BLUE, "X");
        mSeriesAccY = initSeries(Color.RED, "Y");
        mSeriesAccZ = initSeries(Color.GREEN, "Z");
        mSeriesGyrX = initSeries(Color.BLUE, "X");
        mSeriesGyrY = initSeries(Color.RED, "Y");
        mSeriesGyrZ = initSeries(Color.GREEN, "Z");
        mGraphAcc.addSeries(mSeriesAccX);
        mGraphAcc.addSeries(mSeriesAccY);
        mGraphAcc.addSeries(mSeriesAccZ);
        mGraphGyr.addSeries(mSeriesGyrX);
        mGraphGyr.addSeries(mSeriesGyrY);
        mGraphGyr.addSeries(mSeriesGyrZ);
        xText_Acc = (TextView)findViewById(R.id.AX);
        yText_Acc = (TextView)findViewById(R.id.AY);
        zText_Acc = (TextView)findViewById(R.id.AZ);
        xText_Gyr = (TextView)findViewById(R.id.GX);
        yText_Gyr = (TextView)findViewById(R.id.GY);
        zText_Gyr = (TextView)findViewById(R.id.GZ);
        startAccel();
        startGyro();
    }

    private void startGyro() {
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void startAccel(){
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public LineGraphSeries<DataPoint> initSeries(int color, String title) {
        LineGraphSeries<DataPoint> series;
        series = new LineGraphSeries<>();
        series.setDrawDataPoints(false);
        series.setDrawBackground(false);
        series.setColor(color);
        series.setTitle(title);
        return series;
    }

    public GraphView initGraph(int id, String title) {
        GraphView graph = (GraphView) findViewById(id);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(5);
        graph.getGridLabelRenderer().setLabelVerticalWidth(100);
        graph.setTitle(title);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        return graph;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            graphLastAccelXValue += 0.15d;
            mSeriesAccX.appendData(new DataPoint(graphLastAccelXValue, event.values[0]), true, 33);
            mSeriesAccY.appendData(new DataPoint(graphLastAccelXValue, event.values[1]), true, 33);
            mSeriesAccZ.appendData(new DataPoint(graphLastAccelXValue, event.values[2]), true, 33);
            xText_Acc.setText("X : " + event.values[0]);
            yText_Acc.setText("Y : " + event.values[1]);
            zText_Acc.setText("Z : " + event.values[2]);

        }


        if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
            graphLastAccelXValue += 0.15d;
            mSeriesGyrX.appendData(new DataPoint(graphLastAccelXValue, event.values[0]), true, 33);
            mSeriesGyrY.appendData(new DataPoint(graphLastAccelXValue, event.values[1]), true, 33);
            mSeriesGyrZ.appendData(new DataPoint(graphLastAccelXValue, event.values[2]), true, 33);
            xText_Gyr.setText("X : " + event.values[0]);
            yText_Gyr.setText("Y : " + event.values[1]);
            zText_Gyr.setText("Z : " + event.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
