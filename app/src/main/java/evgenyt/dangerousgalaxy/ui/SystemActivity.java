package evgenyt.dangerousgalaxy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ThreadLocalRandom;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Economy;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.Planet;
import evgenyt.dangerousgalaxy.universe.SpaceMath;
import evgenyt.dangerousgalaxy.universe.SpaceShip;
import evgenyt.dangerousgalaxy.universe.Star;

public class SystemActivity extends AppCompatActivity {

    public static GalaxyActivity galaxyActivity;
    static SystemView systemView;
    SpaceShip playerShip = Galaxy.getInstance().getPlayerShip();
    Star systemStar = GalaxyView.getTargetStar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemView = new SystemView(this);
        setContentView(systemView);
        updateTitle();
    }

    public void updateTitle() {
        setTitle("Fuel:" + playerShip.getFuel() + "t.");
    }

    /** Add menu to window */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_system, menu);
        return true;
    }

    /** Menu click handler */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_system_travel:
                if (playerShip.getCurrentStar() != systemStar) {
                    Star currentStar = playerShip.getCurrentStar();
                    int distance = (int) SpaceMath.distanceLY(currentStar, systemStar);
                    if (playerShip.debFuel(distance)) {
                        playerShip.setCurrentStar(systemStar);
                        playerShip.setCurrentPlanet(null);
                        systemView.setTargetPlanet(null);
                        systemView.invalidate();
                        updateTitle();
                    }
                    // Toast.makeText(this, "You're not at this system!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (playerShip.debFuel(1)) {
                    float random = ThreadLocalRandom.current().nextFloat();
                    boolean intercepted = random < playerShip.getCurrentStar().getSecurity().pirateChance;
                    if (intercepted) {
                        Intent intent = new Intent(this, BattleActivity.class);
                        startActivity(intent);
                    } else {
                        playerShip.setCurrentPlanet(systemView.getTargetPlanet());
                    }
                } else {
                    Toast.makeText(this, "No fuel", Toast.LENGTH_SHORT).show();
                }
                systemView.invalidate();
                galaxyActivity.updateTitle();
                updateTitle();
                return true;
            case R.id.menu_system_land:
                if (playerShip.getCurrentPlanet() == null) {
                    Toast.makeText(this, "You can't land on star!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (playerShip.getCurrentPlanet().getPlanetType() != Planet.PlanetType.GAS_GIANT) {
                    if (playerShip.getCurrentPlanet().getPlanetEconomy().getEconomyType() !=
                            Economy.EconomyType.UNINHABITED) {
                        Intent intent = new Intent(this, SpacePortActivity.class);
                        SpacePortActivity.galaxyActivity = galaxyActivity;
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(this, UninhabitedActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "You can't land on gas giant!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menu_system_fuel_scoop:
                if (playerShip.getCurrentPlanet() != null) {
                    Toast.makeText(this, "You must travel to star to fuel scooping!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (playerShip.getDamage(10)) {
                    playerShip.setFuel(playerShip.getType().maxFuel);
                    updateTitle();
                    Toast.makeText(this, "Fuel scooped, integrity " + playerShip.getHealth() + "%",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Ship integrity to low to fuel scoop!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menu_system_player:
                Intent intent = new Intent(this, StatusActivity.class);
                startActivity(intent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }



}
