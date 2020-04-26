package evgenyt.dangerousgalaxy.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.data.Galaxy;

public class SpacePortActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaceport);
        setTitle(Galaxy.getInstance().getPlayerShip().getCurrentPlanet().getName());
    }
}
