package com.ex.view.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.ex.view.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineActivity extends Activity {

    private LineChart mChart;
    private LineData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        /*
        mChart = (LineChart) findViewById(R.id.line_chart);
        mChart.setDrawGridBackground(false);
        // 无描述文本
        mChart.getDescription().setEnabled(false);

        // 使能点击
        mChart.setTouchEnabled(true);

        // 使能拖动和缩放
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // 如果为false，则x，y两个方向可分别缩放
        mChart.setPinchZoom(true);

        LimitLine ll1 = new LimitLine(90f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(80f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        //设置x轴位置
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //去除右边的y轴
        YAxis yAxisRight = mChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = mChart.getAxisLeft();
        yAxisLeft.addLimitLine(ll1);
        yAxisLeft.addLimitLine(ll2);
        //init();

         */
    }
/*
    private void init(){
        //初始化数据
        String xl[] ={"1","2","3","4","5","6","7","8","9","10"}; //横轴数据
        String yl[] ={"80","85","80","90","95","88","90","91","92","93"}; //竖轴数据
        data = getData(xl,yl);
        mChart.setData(data);
        //mChart.animateX(2000);//动画时间
    }

    private LineData getData(String[] xx, String[] yy){
        List<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < yy.length; i++) {
            // 要显示的数据
            yVals.add(new Entry(i+1,Float.parseFloat(yy[i])));
        }
        ArrayList<String> xvalue=new ArrayList<>();
        for (int i = 0; i < yy.length; i++) {
            //x轴时间
            xvalue.add(xx[i]);
        }
        //一条曲线对应一个LineDataSet
        LineDataSet set1 = new LineDataSet(yVals, "前五次的评分");
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置曲线为圆滑的线
        //et1.setCubicIntensity(0.2f);
        set1.setDrawCircles(true);  //设置有圆点
        set1.setLineWidth(2f);    //设置线的宽度
        set1.setCircleSize(5f);   //设置小圆的大小
        set1.setDrawFilled(true);//设置包括的范围区域填充颜色
        set1.setCircleColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.rgb(244, 117, 117));
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);
        return data;
    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

 */
}
