package evgenyt.dangerousgalaxy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.PlayerInfo;
import evgenyt.dangerousgalaxy.universe.SpaceShip;

public class SpacePortActivity extends AppCompatActivity {

    private static final int FUEL_PRICE = 10;

    Galaxy galaxy = Galaxy.getInstance();
    PlayerInfo playerInfo = galaxy.getPlayer();
    SpaceShip playerShip = galaxy.getPlayerShip();
    public static GalaxyActivity galaxyActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaceport);
        setTitle(Galaxy.getInstance().getPlayerShip().getCurrentPlanet().getName() + " space port");
        final Button marketButton = findViewById(R.id.buttonMarket);
        marketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MarketActivity.class);
                startActivity(intent);
            }
        });
        final Button refuelButton = findViewById(R.id.buttonRefuel);
        final int fuel = playerShip.getFuel();
        final int maxFuel = playerShip.getType().maxFuel;
        refuelButton.setText("REFUEL (" + (maxFuel - fuel) * FUEL_PRICE + "cr.)");
        refuelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerInfo.debBalance((maxFuel - fuel) * FUEL_PRICE)) {
                    playerShip.setFuel(maxFuel);
                    refuelButton.setText(R.string.txt_refueled);
                    refuelButton.setEnabled(false);
                    galaxyActivity.updateTitle();
                } else {
                    refuelButton.setText(R.string.txt_nomoney);
                }
            }
        });
        final Button repairButton = findViewById(R.id.buttonRepair);
        final int repairPrice = (int) ((100 - playerShip.getHealth()) * 0.001 * playerShip.getType().price);
        repairButton.setText("Repair (" + repairPrice + "cr.)");
        repairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerInfo.debBalance(repairPrice)) {
                    playerShip.setHealth(100);
                    repairButton.setEnabled(false);
                    repairButton.setText(R.string.txt_repaired);
                } else {
                    repairButton.setText(R.string.txt_nomoney);
                }
            }
        });
        final Button shipyardButton = findViewById(R.id.buttonShipyard);
        shipyardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShipyardActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_spaceport, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_spaceport_status:
                Intent intent = new Intent(this, StatusActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
