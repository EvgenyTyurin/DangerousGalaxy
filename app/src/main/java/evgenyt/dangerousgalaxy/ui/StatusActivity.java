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

        TextView battleRatingText = findViewById(R.id.text_battle_rating);
        int kills = PrefsWork.readIntSlot("kills");
        String battleRating = "Harmless";
        if (kills >= 5)
            battleRating = "Mostly harmless";
        if (kills >= 10)
            battleRating = "Novice";
        if (kills >= 20)
            battleRating = "Competent";
        if (kills >= 50)
            battleRating = "Expert";
        if (kills >= 100)
            battleRating = "Master";
        if (kills >= 150)
            battleRating = "Dangerous";
        if (kills >= 200)
            battleRating = "Deadly";
        if (kills >= 300)
            battleRating = "Elite";
        if (kills >= 500)
            battleRating = "Super Elite";
        battleRatingText.setText("Battle rating: " + battleRating + "(" + kills + " kills)");

        int artifacts = PrefsWork.readIntSlot("artifacts");
        String explorerRating = "Aimless";
        if (artifacts >= 5)
            explorerRating = "Mostly Aimless";
        if (artifacts >= 10)
            explorerRating = "Scout";
        if (artifacts >= 20)
            explorerRating = "Surveyor";
        if (artifacts >= 50)
            explorerRating = "Trailblazer";
        if (artifacts >= 100)
            explorerRating = "Pathfinder";
        if (artifacts >= 150)
            explorerRating = "Ranger";
        if (artifacts >= 200)
            explorerRating = "Pioneer";
        if (artifacts >= 300)
            explorerRating = "Elite";
        if (artifacts >= 500)
            explorerRating = "Super Elite";
        TextView explorerRatingText = findViewById(R.id.text_explorer_rating);
        explorerRatingText.setText("Explorer rating: " + explorerRating + "(" + artifacts + " artifacts)");

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
