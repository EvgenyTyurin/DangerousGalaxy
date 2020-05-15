package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.SpaceShip;

public class GalaxyActivity extends AppCompatActivity {

    private GalaxyView galaxyView;
    private SpaceShip playerShip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefsWork.init(this);
        playerShip = Galaxy.getInstance().getPlayerShip();
        super.onCreate(savedInstanceState);
        galaxyView = new GalaxyView(this);
        setContentView(galaxyView);
    }

    /** Add menu to window */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_galaxy, menu);
        return true;
    }

    /** Menu click handler */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_galaxy_travel:
                playerShip.setCurrentStar(galaxyView.getTargetStar());
                playerShip.setCurrentPlanet(null);
                galaxyView.invalidate();
                return true;
            case R.id.menu_galaxy_system:
                Intent intent = new Intent(this, SystemActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_galaxy_stat:
                Intent statusIntent = new Intent(this, StatusActivity.class);
                startActivity(statusIntent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

}
