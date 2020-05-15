package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Commodity;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.PlayerInfo;
import evgenyt.dangerousgalaxy.universe.SpaceShip;
import evgenyt.dangerousgalaxy.universe.Star;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Galaxy galaxy = Galaxy.getInstance();
        PlayerInfo player = galaxy.getPlayer();
        SpaceShip playerShip = galaxy.getPlayerShip();
        TextView balanceText = findViewById(R.id.textBalance);
        balanceText.setText("Balance:" + player.getBalance() + "cr.");
        TextView shipText = findViewById(R.id.text_ship);
        shipText.setText("Ship: " + playerShip.getType() +
                ", attack:" + playerShip.getType().attack +
                ", speed:" + playerShip.getType().speed +
                ", cargo:" + playerShip.getCurrentCargoTonnage() + "/" + playerShip.getType().maxCargo + " tons."
                );
        TextView cargoText = findViewById(R.id.text_cargo);
        StringBuilder stringBuilder = new StringBuilder();
        for (Commodity commodity : playerShip.getCargoList().keySet()) {
            stringBuilder.append(commodity.toString() + " - " + playerShip.getCargoList().get(commodity) + "t. ");
        }
        cargoText.setText("Cargo: " + stringBuilder.toString());
        TextView navText = findViewById(R.id.text_nav);
        Star currentStar = playerShip.getCurrentStar();
        navText.setText("Coordinates: " + currentStar.getName() + "(" + currentStar.getCoords().toString() + ")" );
    }
}
