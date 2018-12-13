package com.example.sammrabatool.solutions5d.DashboardOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Polar;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.PolarSeriesType;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.ScaleTypes;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.scales.Linear;
import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;

import java.util.ArrayList;
import java.util.List;

public class DashProperty extends AppCompatActivity {
private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_options_property);
initToolbar();
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Polar polar = AnyChart.polar();

        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomDataEntry("Nail polish", 12814, 4376, 4229));
        data.add(new CustomDataEntry("Eyebrow pencil", 13012, 3987, 3932));
        data.add(new CustomDataEntry("Rouge", 11624, 3574, 5221));
        data.add(new CustomDataEntry("Pomade", 8814, 4376, 9256));
        data.add(new CustomDataEntry("Eyeshadows", 12998, 4572, 3308));
        data.add(new CustomDataEntry("Eyeliner", 12321, 3417, 5432));
        data.add(new CustomDataEntry("Foundation", 10342, 5231, 13701));
        data.add(new CustomDataEntry("Lip gloss", 22998, 4572, 4008));
        data.add(new CustomDataEntry("Mascara", 11261, 6134, 18712));
        data.add(new CustomDataEntry("Powder", 10261, 5134, 25712));

        Set set = Set.instantiate();
        set.data(data);
        Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Data = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Data = set.mapAs("{ x: 'x', value: 'value3' }");

        polar.column(series1Data);

        polar.column(series2Data);

        polar.column(series3Data);

        polar.title("Company Profit Dynamic in Regions by Year");

        polar.sortPointsByX(true)
                .defaultSeriesType(PolarSeriesType.COLUMN)
                .yAxis(false)
                .xScale(ScaleTypes.ORDINAL);

        polar.title().margin().bottom(20d);

        ((Linear) polar.yScale(Linear.class)).stackMode(ScaleStackMode.VALUE);

        polar.tooltip()
                .valuePrefix("$")
                .displayMode(TooltipDisplayMode.UNION);

        anyChartView.setChart(polar);
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_menu);
//        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.indigo_500), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Tools.setSystemBarColor(this, android.R.color.white);
//        Tools.setSystemBarLight(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.logout) {
            Toast.makeText(this, "logout is clicked", Toast.LENGTH_LONG).show();
            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(DashProperty.this, LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
