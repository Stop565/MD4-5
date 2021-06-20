package ua.kpi.comsys.iv8221.SecondFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import ua.kpi.comsys.iv8221.MainActivity;
import ua.kpi.comsys.iv8221.R;

public class SecondFragment extends Fragment {

    private LinearLayout graphContainer;
    private GraphView graph;
    private PieChart chart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_second, container, false);
        graphContainer = view.findViewById(R.id.graph_container);
        final SwitchCompat switchCompat = view.findViewById(R.id.plot_switch);

        graph = view.findViewById(R.id.first_elem);
        chart = view.findViewById(R.id.second_elem);

        populateLineGraph(graph);
        populatePieChart(chart);

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphContainer.removeAllViews();
                if (switchCompat.isChecked()){
                    graphContainer.addView(chart);
                } else {
                    graphContainer.addView(graph);
                }
            }
        });

        graphContainer.removeAllViews();
        graphContainer.addView(graph);
        return view;
    }
    
    private void populateLineGraph(GraphView graph){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();


        int N = 100;
        double x = -Math.PI;
        double step = 2 * Math.PI / N;

        for (int i = 0; i < 100; i++) {
            series.appendData(new DataPoint(x, Math.cos(x)), true, N);
            x += step;
        }
        graph.addSeries(series);
    }


    private void populatePieChart(PieChart chart){
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(45, "Blue"));
        pieEntries.add(new PieEntry(5, "Violet"));
        pieEntries.add(new PieEntry(25, "Yellow"));
        pieEntries.add(new PieEntry(25, "Gray"));

        PieDataSet ds = new PieDataSet(pieEntries, "");
        int[] colors = {
                Color.rgb(127, 223, 255),
                Color.rgb(200, 0, 180),
                Color.rgb(255, 204, 0),
                Color.rgb(120, 120, 120),
        };
        ds.setColors(colors);

        PieData d = new PieData(ds);
        chart.setData(d);
        chart.invalidate();
        chart.getDescription().setText("Colorful chart");
    }
}
