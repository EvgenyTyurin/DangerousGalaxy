package evgenyt.dangerousgalaxy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaceport);
        setTitle(Galaxy.getInstance().getPlayerShip().getCurrentPlanet().getName());
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
}
