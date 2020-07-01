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

import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.SpaceShip;
import evgenyt.dangerousgalaxy.universe.Star;
import evgenyt.dangerousgalaxy.universe.SpaceMath;

import static evgenyt.dangerousgalaxy.ui.GalaxyActivity.shipBitmap;

/**
 * Galaxy map
 */

public class GalaxyView extends View  implements View.OnTouchListener {

    public static final float MIN_RATIO = 0.5f;
    public static final float INIT_RATIO = 10f;

    // Draw brushes
    private Paint paintStar = new Paint();
    private Paint paintShip = new Paint();
    private Paint paintTarget = new Paint();
    private Paint paintDebug = new Paint();

    // Init galaxy objects
    private static Galaxy galaxy = Galaxy.getInstance();
    private static SpaceShip playerShip = galaxy.getPlayerShip();
    private static Star targetStar = playerShip.getCurrentStar();
    private static Star currentStar = playerShip.getCurrentStar();

    // Stars displaying on screen
    private List<Star> screenStars = new ArrayList<>();

    // User camera
    private long centerX = -1 * currentStar.getCoords().getX();
    private long centerY = -1 * currentStar.getCoords().getY();
    private float ratio = INIT_RATIO;

    // Touch listener vars
    private double oldY;
    private double oldX;
    private boolean zooming;
    private boolean pastZoom;
    private float oldDistance;
    private String debugStr = "";
    private boolean moved = true;

    // Animation vars
    private boolean isAnimating = false;
    private int shipX, shipY, targetX, targetY;
    private float deltaX, deltaY;

    private void init() {
        paintStar.setColor(Color.WHITE);
        paintShip.setColor(Color.CYAN);
        paintTarget.setColor(Color.RED);
        paintTarget.setStyle(Paint.Style.STROKE);
        paintTarget.setStrokeWidth(4);
        paintDebug.setColor(Color.WHITE);
        paintDebug.setTextSize(40);
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

    // Draw galaxy map
    @Override
    public void onDraw(Canvas canvas) {
        // draw stars
        canvas.drawColor(Color.BLACK);
        int r = ratio < 1 ? 1 : (int) ratio;
        paintStar.setTextSize(r * 2);
        if (moved) {
            screenStars.clear();
        }
        for (Star star: galaxy.getStars()) {
            long x = getScrX(star.getCoords().getX());
            long y = getScrY(star.getCoords().getY());
            if (x > -100 && x < getWidth() + 100 && y > -100 && y < getHeight()) {
                canvas.drawCircle(x, y, r, paintStar);
                if (ratio > 5) {
                    canvas.drawText(star.getName(), x + r, y, paintStar);
                }
                if (moved) {
                    screenStars.add(star);
                }
            }
        }
        int l = r < 10 ? 10 : r;
        long x1 = getScrX(targetStar.getCoords().getX());
        long y1 = getScrY(targetStar.getCoords().getY());

        // draw destination
        if (targetStar != playerShip.getCurrentStar()) {
            l += 2;
            canvas.drawCircle(x1, y1, l, paintTarget);
            canvas.drawLine(x1 - l, y1, x1 + l, y1, paintTarget);
            canvas.drawLine(x1, y1 - l, x1, y1 + l, paintTarget);
        }

        // draw ship
        if (isAnimating) {
            if (Math.pow(shipX - targetX, 2) + Math.pow(shipY - targetY, 2) < 144) {
                isAnimating = false;
                invalidate();
            } else {
                canvas.drawBitmap(shipBitmap, shipX, shipY, paintShip);
                canvas.drawLine(shipX, shipY, targetX, targetY, paintStar);
            }
        } else {
            long x = getScrX(galaxy.getPlayerShip().getCurrentStar().getCoords().getX());
            long y = getScrY(galaxy.getPlayerShip().getCurrentStar().getCoords().getY()) - r;
            canvas.drawBitmap(shipBitmap, x - l * 2, y - l * 2, paintShip);
            // draw path
            canvas.drawLine(x, y, x1, y1, paintStar);
        }

        // debug text
        canvas.drawText(debugStr, 10, 50, paintDebug);
        moved = false;
    }

    // Handle galaxy map controls
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        moved = false;
        boolean oneUp = false;
        // Two fingers event
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
            // clicking
            case MotionEvent.ACTION_DOWN:
                if (!zooming) {
                    oldY = event.getY();
                    oldX = event.getX();
                    for (Star star : getScreenStars()) {
                        float distance = SpaceMath.distance(getScrX(star.getCoords().getX()),
                                event.getX(),
                                getScrY(star.getCoords().getY()),
                                event.getY());
                        if (distance <= ratio * 2.5) {
                            targetStar = star;
                            debugStr = star.getName() + "(" + star.getStarClass() + ")" +
                                    ", security: " + star.getSecurity() +
                                    ", distance " + (int) SpaceMath.distanceLY(galaxy.getPlayerShip().getCurrentStar(), targetStar) + " l.y.";
                            break;
                        }
                    }
                }
                break;
            // Moving and zooming
            case MotionEvent.ACTION_MOVE:
                if (zooming && oldDistance > 0 && event.getPointerCount() == 2) {
                    float distance = SpaceMath.distance(event.getX(0), event.getX(1),
                            event.getY(0), event.getY(1));
                    setRatio(getRatio() * distance / oldDistance);
                    oldDistance = distance;
                    if (getRatio() < MIN_RATIO ) {
                        setRatio(MIN_RATIO);
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
                moved = true;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        v.invalidate();
        return true;
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

    public Galaxy getGalaxy() {
        return galaxy;
    }

    public static Star getTargetStar() {
        return targetStar;
    }

    public void setAnimating(boolean animating) {
        isAnimating = animating;
    }

    public void setShipX(int shipX) {
        this.shipX = shipX;
    }

    public void setShipY(int shipY) {
        this.shipY = shipY;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }
}