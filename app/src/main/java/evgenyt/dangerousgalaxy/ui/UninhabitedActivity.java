package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Commodity;
import evgenyt.dangerousgalaxy.universe.Economy;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.Planet;
import evgenyt.dangerousgalaxy.universe.SpaceShip;

public class UninhabitedActivity extends AppCompatActivity {

    Galaxy galaxy = Galaxy.getInstance();
    SpaceShip playerShip = galaxy.getPlayerShip();
    Planet currentPlanet = playerShip.getCurrentPlanet();
    Economy economy = currentPlanet.getPlanetEconomy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uninhabitated);
        final TextView statusText = findViewById(R.id.textSearchStatus);
        statusText.setText("PLANET UNDISCOVERED");
        Button searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commodity commodity = new Commodity("Artifacts");
                playerShip.moveToCargo(commodity , 1);
                statusText.setText("PLANET DISCOVERED");
            }
        });
    }
}
