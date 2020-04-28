package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import evgenyt.dangerousgalaxy.R;

public class MarketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        List<String> marketList = new ArrayList<>();
        marketList.add("Food - 100cr");
        final ListAdapter listAdapter = new ArrayAdapter<>(this,
                R.layout.list_item, marketList);
        ListView marketListView = findViewById(R.id.listview_market);
        marketListView.setAdapter(listAdapter);
    }
}
