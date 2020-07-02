package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Commodity;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.PlayerInfo;
import evgenyt.dangerousgalaxy.universe.SpaceMath;
import evgenyt.dangerousgalaxy.universe.SpaceShip;
import evgenyt.dangerousgalaxy.universe.Star;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Galaxy galaxy = Galaxy.getInstance();
        final PlayerInfo player = galaxy.getPlayer();
        final SpaceShip playerShip = galaxy.getPlayerShip();
        TextView balanceText = findViewById(R.id.textBalance);
        balanceText.setText("Balance: " + player.getBalance() + " cr." +
                " Insurance: " + playerShip.getType().price / 10 + " cr.");
        TextView shipText = findViewById(R.id.text_ship);
        shipText.setText("Ship: " + playerShip.getType() +
                ", integrity: " + playerShip.getHealth() + "%" +
                ", laser:" + playerShip.getType().attack + "MW" +
                ", speed:" + playerShip.getType().speed + "ls/h" +
                ", cargo:" + playerShip.getCurrentCargoTonnage() + "/" + playerShip.getType().maxCargo + " t."
                );
        TextView fuelText = findViewById(R.id.text_fuel);
        fuelText.setText("Fuel: " + playerShip.getFuel() + "/" + playerShip.getType().maxFuel);
        TextView cargoText = findViewById(R.id.text_cargo);
        StringBuilder stringBuilder = new StringBuilder();
        for (Commodity commodity : playerShip.getCargoList().keySet()) {
            int tonnage = playerShip.getCargoList().get(commodity);
            if (tonnage > 0)
                stringBuilder.append(commodity.toString() + " - " + tonnage + "t. ");
        }
        cargoText.setText("Cargo: " + stringBuilder.toString());
        TextView navText = findViewById(R.id.text_nav);
        Star currentStar = playerShip.getCurrentStar();
        navText.setText("Coordinates: " + currentStar.getName() + "(" + currentStar.getCoords().toString() + ")" );
        final Button destructButton = findViewById(R.id.button_destruct);
        destructButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpaceMath.shipDestruct();
            }
        });
    }
}
