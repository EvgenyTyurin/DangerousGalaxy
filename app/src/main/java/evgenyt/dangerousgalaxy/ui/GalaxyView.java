package evgenyt.dangerousgalaxy.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import evgenyt.dangerousgalaxy.data.Galaxy;
import evgenyt.dangerousgalaxy.data.Star;
import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class GalaxyView extends View  implements View.OnTouchListener {

    private Paint paintStar = new Paint();
    private Paint paintShip = new Paint();
    private Paint paintTarget = new Paint();
    private Galaxy galaxy = Galaxy.getInstance();
    private Star targetStar = galaxy.getStars().get(102);
    private List<Star> screenStars = new ArrayList<>();

    // User camera
    private long centerX = 0;
    private long centerY = 0;
    private float ratio = 1;

    // Touch listener
    private double oldY;
    private double oldX;
    private boolean zooming;
    private boolean pastZoom;
    private float oldDistance;
    private String debugStr = "";

    private void init() {
        paintStar.setColor(Color.WHITE);
        paintShip.setColor(Color.CYAN);
        paintTarget.setColor(Color.RED);
        setOnTouchListener(this);
    }

    public GalaxyView(Context context) {
        super(context);
        init();
    }

    public GalaxyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalaxyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        int r = ratio < 1 ? 1 : (int) ratio;
        paintStar.setTextSize(r * 2);
        screenStars.clear();
        for (Star star: galaxy.getStars()) {
            long x = getScrX(star.getCoords().getX());
            long y = getScrY(star.getCoords().getY());
            if (x > -100 && x < getWidth() + 100 && y > -100 && y < getHeight()) {
                canvas.drawCircle(x, y, r, paintStar);
                if (ratio > 5) {
                    canvas.drawText(star.getName(), x + r, y, paintStar);
                }
                screenStars.add(star);
            }
        }
        long x = getScrX(galaxy.getPlayerShip().getCurrentStar().getCoords().getX());
        long y = getScrY(galaxy.getPlayerShip().getCurrentStar().getCoords().getY()) - r;
        int l = r < 10 ? 10 : r;
        canvas.drawLine(x, y, x - l, y - l * 2, paintShip);
        canvas.drawLine(x, y, x + l, y - l * 2, paintShip);
        long x1 = getScrX(targetStar.getCoords().getX());
        long y1 = getScrY(targetStar.getCoords().getY()) - r;
        canvas.drawLine(x1, y1, x1 - l, y1 - l * 2, paintTarget);
        canvas.drawLine(x1, y1, x1 + l, y1 - l * 2, paintTarget);
        canvas.drawLine(x, y, x1, y1, paintStar);

        canvas.drawText(debugStr, 10, 100, paintStar);
    }

    public long getScrX(long x) {
        return (long) ((x + centerX) * ratio + getWidth() / 2);
    }

    public long getScrY(long y) {
        return (long) ((centerY + y) * ratio + getHeight() / 2);
    }

    public long getCenterX() {
        return centerX;
    }

    public void setCenterX(long centerX) {
        this.centerX = centerX;
    }

    public long getCenterY() {
        return centerY;
    }

    public void setCenterY(long centerY) {
        this.centerY = centerY;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public List<Star> getScreenStars() {
        return screenStars;
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
                oldY = event.getY();
                oldX = event.getX();
                break;
        }
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!zooming) {
                    oldY = event.getY();
                    oldX = event.getX();
                    debugStr = "On screen: " + getScreenStars().size() +
                            " " + event.getX() + ":" + event.getY();
                    for (Star star : getScreenStars()) {
                        if (SpaceMath.distance(getScrX(star.getCoords().getX()),
                                getScrY(star.getCoords().getY()),
                                event.getX(),
                                event.getY()) <= ratio) {
                            targetStar = star;
                            break;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (zooming && oldDistance > 0 && event.getPointerCount() == 2) {
                    float distance = SpaceMath.distance(event.getX(0), event.getX(1),
                            event.getY(0), event.getY(1));
                    setRatio(getRatio() * distance / oldDistance);
                    oldDistance = distance;
                    if (getRatio() < 0.1 ) {
                        setRatio(0.1f);
                    }
                    pastZoom = true;
                } else {
                    if (pastZoom || oneUp) {
                        pastZoom = false;
                    } else {
                        long deltaY = (long) ((oldY - event.getY()) / getRatio());
                        long deltaX = (long) ((oldX - event.getX()) / getRatio());
                        setCenterY(getCenterY() - deltaY);
                        setCenterX(getCenterX() - deltaX);
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