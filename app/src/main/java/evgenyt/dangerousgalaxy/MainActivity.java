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
    private boolean pastZoom;
    private float oldDistance;

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
        boolean oneUp = false;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                zooming = event.getPointerCount() == 2;
                oldDistance = SpaceMath.distance(event.getX(0), event.getX(1),
                        event.getY(0), event.getY(1));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                zooming = event.getPointerCount() != 2;
                oneUp = true;
                break;
        }
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!zooming) {
                    oldY = event.getY();
                    oldX = event.getX();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (zooming && oldDistance > 0 && event.getPointerCount() == 2) {
                    float distance = SpaceMath.distance(event.getX(0), event.getX(1),
                            event.getY(0), event.getY(1));
                    SpaceMath.Point.ratio *= distance / oldDistance;
                    oldDistance = distance;
                    if (SpaceMath.Point.ratio < 1 ) {
                        SpaceMath.Point.ratio = 1;
                    }
                    pastZoom = true;
                } else {
                    if (pastZoom || oneUp) {
                        pastZoom = false;
                    } else {
                        SpaceMath.Point.centerY -= (oldY - event.getY()) / SpaceMath.Point.ratio;
                        SpaceMath.Point.centerX -= (oldX - event.getX()) / SpaceMath.Point.ratio;
                        oldY = event.getY();
                        oldX = event.getX();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        v.invalidate();
        return true;
    }
}
