package evgenyt.dangerousgalaxy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.data.Galaxy;
import evgenyt.dangerousgalaxy.data.SpaceShip;
import evgenyt.dangerousgalaxy.data.Star;

public class SystemActivity extends AppCompatActivity {

    private SystemView systemView;
    SpaceShip playerShip = Galaxy.getInstance().getPlayerShip();
    Star systemStar = GalaxyView.getTargetStar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemView = new SystemView(this);
        setContentView(systemView);
        setTitle(systemStar.getName());
        playerShip.setCurrentPlanet(null);
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
                if (systemView.getTargetPlanet() != null) {
                    playerShip.setCurrentPlanet(systemView.getTargetPlanet());
                }
                systemView.invalidate();
                return true;
            case R.id.menu_system_land:
                if (playerShip.getCurrentStar() == systemStar && playerShip.getCurrentPlanet() != null) {
                    Intent intent = new Intent(this, SpacePortActivity.class);
                    startActivity(intent);
                }
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }



}
