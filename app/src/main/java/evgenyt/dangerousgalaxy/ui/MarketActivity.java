package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Commodity;
import evgenyt.dangerousgalaxy.universe.Economy;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.Player;
import evgenyt.dangerousgalaxy.universe.SpaceShip;

public class MarketActivity extends AppCompatActivity {

    private Galaxy galaxy = Galaxy.getInstance();
    private SpaceShip playerShip = galaxy.getPlayerShip();
    private Economy economy = playerShip.getCurrentPlanet().getPlanetEconomy();
    private Player player = galaxy.getPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        final List<String> marketList = new ArrayList<>();
        Map<Commodity, Integer> prices = economy.getCommoditiesPrices();
        for (Commodity commodity : prices.keySet()) {
            int price = prices.get(commodity);
            if (price > 0)
            marketList.add(commodity.toString() + "~" + price + "cr/t");
        }
        final ListAdapter listAdapter = new ArrayAdapter<>(this,
                R.layout.list_item, marketList);
        final ListView marketListView = findViewById(R.id.listview_market);
        marketListView.setAdapter(listAdapter);
        marketListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] strs = String.valueOf(listAdapter.getItem(position)).split("~");
                final Commodity commodity = new Commodity(strs[0]);
                AlertDialog.Builder builder = new AlertDialog.Builder(MarketActivity.this);
                TextView textCargo = new TextView(MarketActivity.this);
                builder.setTitle("All cargo: " + playerShip.getCurrentCargoTonnage() + "t/" +
                        playerShip.getMaxCargoTonnage() + "t. Balance: " + player.getBalance() + "cr.");
                builder.setMessage(commodity.toString() + " (in cargo: " +
                        playerShip.getCargoList().get(commodity) + "t)");
                final EditText editQuantity = new EditText(MarketActivity.this);
                builder.setView(editQuantity);
                builder.setPositiveButton("Sell", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int tonnage = Integer.valueOf(editQuantity.getText().toString());
                        int price = tonnage * economy.getCommoditiesPrices().get(commodity);
                        if (playerShip.moveFromCargo(commodity, tonnage)) {
                            player.credBalance(price);
                        }
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setNegativeButton("Buy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int tonnage = Integer.valueOf(editQuantity.getText().toString());
                        int price = tonnage * economy.getCommoditiesPrices().get(commodity);
                        if (price <= player.getBalance() && playerShip.moveToCargo(commodity, tonnage)) {
                            player.debBalance(price);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
