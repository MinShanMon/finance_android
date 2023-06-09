package com.team3.personalfinanceapp.insights;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.team3.personalfinanceapp.R;
import com.team3.personalfinanceapp.model.Transaction;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorySpendFragment extends Fragment {

    private List<Transaction> transactions;


    public CategorySpendFragment() {
        // Required empty public constructor
    }

    public CategorySpendFragment(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_spend, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        createSpendingByCategory(view);
    }

    private void createSpendingByCategory(View view) {

        LocalDate currDate = LocalDate.now().withDayOfMonth(1);

        Map<String, Double> currMonthCategorySpending =
                transactions.stream().filter(t -> t.getDate().withDayOfMonth(1).equals(currDate))
                        .collect(Collectors.groupingBy(Transaction::getCategory,
                                Collectors.summingDouble(Transaction::getAmount)));

        Map<String, Double> prevMonthCategorySpending =
                transactions.stream().filter(t -> t.getDate().withDayOfMonth(1).equals(currDate.minusMonths(1)))
                        .collect(Collectors.groupingBy(Transaction::getCategory,
                                Collectors.summingDouble(Transaction::getAmount)));
        setBarChart(view, currMonthCategorySpending, prevMonthCategorySpending);
    }

    private void setBarChart(View view, Map<String, Double> currMonthSpendingMap, Map<String, Double> prevMonthSpendingMap) {
        BarChart foodBarChart = view.findViewById(R.id.food_barchart);
        BarChart transportBarChart = view.findViewById(R.id.transport_barchart);
        BarChart othersBarChart = view.findViewById(R.id.others_barchart);

        setBarData(foodBarChart, "Food", currMonthSpendingMap, prevMonthSpendingMap);
        setBarData(transportBarChart, "Transport", currMonthSpendingMap, prevMonthSpendingMap);
        setBarData(othersBarChart, "Others", currMonthSpendingMap, prevMonthSpendingMap);

        TextView foodLabel = view.findViewById(R.id.food_barchart_label);
        foodLabel.setText("Food");

        TextView transportLabel = view.findViewById(R.id.transport_barchart_label);
        transportLabel.setText("Transport");

        TextView othersLabel = view.findViewById(R.id.others_barchart_label);
        othersLabel.setText("Others");


    }

    private void setBarData(BarChart barChart, String category, Map<String, Double> currMonthSpendingMap, Map<String, Double> prevMonthSpendingMap) {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, prevMonthSpendingMap.getOrDefault(category, Double.valueOf(0)).floatValue()));
        entries.add(new BarEntry(1f, currMonthSpendingMap.getOrDefault(category, Double.valueOf(0)).floatValue()));
        BarDataSet dataSet = new BarDataSet(entries, category + " Spending");
        dataSet.setColors(Color.LTGRAY, Color.parseColor("#FFA500"));
        BarData data = new BarData(dataSet);

        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.setDescription(null);
        barChart.getLegend().setEnabled(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        String currMonthStr = LocalDate.now().getMonth().toString().substring(0, 3);
        String prevMonthStr = LocalDate.now().getMonth().minus(1).toString().substring(0, 3);
        String[] labels = new String[]{prevMonthStr, currMonthStr};
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return labels[(int) value];
            }
        });
        barChart.invalidate();
    }

}