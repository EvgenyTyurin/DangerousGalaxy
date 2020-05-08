package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Commodity;
import evgenyt.dangerousgalaxy.universe.Economy;
import evgenyt.dangerousgalaxy.universe.Galaxy;

public class MarketActivity extends AppCompatActivity {

    Economy economy = Galaxy.getInstance().getPlayerShip().getCurrentPlanet().getPlanetEconomy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        List<String> marketList = new ArrayList<>();
        Map<Commodity, Integer> prices = economy.getCommoditiesPrices();
        for (Commodity commodity : prices.keySet()) {
            marketList.add(commodity.toString() + " - " + prices.get(commodity) + "cr");
        }
        final ListAdapter listAdapter = new ArrayAdapter<>(this,
                R.layout.list_item, marketList);
        ListView marketListView = findViewById(R.id.listview_market);
        marketListView.setAdapter(listAdapter);
    }
}
