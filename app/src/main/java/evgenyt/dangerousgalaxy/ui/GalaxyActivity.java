package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class GalaxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GalaxyView galaxyView = new GalaxyView(this);
        galaxyView.setBackgroundColor(Color.WHITE);
        setContentView(galaxyView);
    }

}
