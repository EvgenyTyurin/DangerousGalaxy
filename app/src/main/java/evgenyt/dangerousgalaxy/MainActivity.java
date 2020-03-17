package evgenyt.dangerousgalaxy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GalaxyView galaxyView;

    private double oldY;
    private double oldX;
    private boolean zooming;
    private boolean pastZoom;
    private float oldDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    galaxyView.setRatio(galaxyView.getRatio() * distance / oldDistance);
                    oldDistance = distance;
                    if (galaxyView.getRatio() < 0.1 ) {
                        galaxyView.setRatio(0.1f);
                    }
                    pastZoom = true;
                } else {
                    if (pastZoom || oneUp) {
                        pastZoom = false;
                    } else {
                        long deltaY = (long) ((oldY - event.getY()) / galaxyView.getRatio());
                        long deltaX = (long) ((oldX - event.getX()) / galaxyView.getRatio());
                        galaxyView.setCenterY(galaxyView.getCenterY() - deltaY);
                        galaxyView.setCenterX(galaxyView.getCenterX() - deltaX);
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
