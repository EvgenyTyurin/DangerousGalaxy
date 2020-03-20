package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import evgenyt.dangerousgalaxy.R;

public class GalaxyActivity extends AppCompatActivity {

    private GalaxyView galaxyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                galaxyView.getGalaxy().getPlayerShip().setCurrentStar(galaxyView.getTargetStar());
                galaxyView.invalidate();
                return true;
            case R.id.menu_galaxy_system:
                Intent intent = new Intent(this, SystemActivity.class);
                startActivity(intent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

}
