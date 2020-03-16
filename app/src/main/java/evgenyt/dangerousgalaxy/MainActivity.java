package evgenyt.dangerousgalaxy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    GalaxyView galaxyView;

    private static final double WINDOW_WIDTH = 1000;
    private static final double WINDOW_HEIGHT = 1500;

    private double oldY;
    private double oldX;
    private boolean zooming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SpaceMath.Point.centerX = (long) (WINDOW_WIDTH / 2);
        SpaceMath.Point.centerY = (long) (WINDOW_HEIGHT / 2);

        galaxyView = new GalaxyView(this);
        galaxyView.setBackgroundColor(Color.WHITE);
        galaxyView.setOnTouchListener(this);
        setContentView(galaxyView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                zooming = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                zooming = false;
                break;
        }

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = event.getY();
                oldX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (zooming) {
                    SpaceMath.Point.ratio += (oldY - event.getY()) / 10;
                    if (SpaceMath.Point.ratio < 1 ) {
                        SpaceMath.Point.ratio = 1;
                    }
                } else {
                    SpaceMath.Point.centerY -= oldY - event.getY();
                    SpaceMath.Point.centerX -= oldX - event.getX();
                }
                oldY = event.getY();
                oldX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        v.invalidate();
        return true;
    }
}
