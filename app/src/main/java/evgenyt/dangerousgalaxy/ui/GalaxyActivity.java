package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.SpaceMath;
import evgenyt.dangerousgalaxy.universe.SpaceShip;
import evgenyt.dangerousgalaxy.universe.Star;

public class GalaxyActivity extends AppCompatActivity {

    private GalaxyView galaxyView;
    private SpaceShip playerShip;

    public static Bitmap shipBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        shipBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
        PrefsWork.init(this);
        playerShip = Galaxy.getInstance().getPlayerShip();
        super.onCreate(savedInstanceState);
        galaxyView = new GalaxyView(this);
        setContentView(galaxyView);
        updateTitle();
    }

    public void updateTitle() {
        setTitle("Fuel: " + playerShip.getFuel() + " t.");
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
                Star currentStar = playerShip.getCurrentStar();
                Star targetStar = galaxyView.getTargetStar();
                int distance = (int) SpaceMath.distanceLY(currentStar, targetStar);
                if (playerShip.debFuel(distance)) {
                    playerShip.setCurrentStar(targetStar);
                    playerShip.setCurrentPlanet(null);
                    galaxyView.invalidate();
                    updateTitle();
                }
                return true;
            case R.id.menu_galaxy_system:
                Intent intent = new Intent(this, SystemActivity.class);
                SystemActivity.galaxyActivity = this;
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
