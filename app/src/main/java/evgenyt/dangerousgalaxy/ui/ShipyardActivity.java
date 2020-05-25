package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.SpaceShip;

public class ShipyardActivity extends AppCompatActivity {

    public final static String EXTRA_SHIP_BUY = "evgenyt.dangerousgalaxy.ship_buy";
    final List<String> shipList = new ArrayList<>();
    List<SpaceShip.Type> shipTypes =Arrays.asList(SpaceShip.Type.values());
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipyard);
        listAdapter = new ArrayAdapter<>(this, R.layout.list_item, shipList);
        for (SpaceShip.Type shipType : shipTypes) {
            shipList.add(shipType + ":" + shipType.price);
        }
        final ListView listView = findViewById(R.id.listview_shipyard);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] itemStrs = String.valueOf(listAdapter.getItem(position)).split(":");
                Intent intent = new Intent(getApplicationContext(), BuyShipActivity.class);
                intent.putExtra(EXTRA_SHIP_BUY, itemStrs[0]);
                startActivity(intent);
            }
        });
    }
}
