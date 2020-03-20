package evgenyt.dangerousgalaxy.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SystemActivity extends AppCompatActivity {

    private SystemView systemView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemView = new SystemView(this);
        setContentView(systemView);
        setTitle(GalaxyView.getTargetStar().getName());
    }
}
