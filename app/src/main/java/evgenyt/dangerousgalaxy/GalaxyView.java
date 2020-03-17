package evgenyt.dangerousgalaxy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import evgenyt.dangerousgalaxy.data.Galaxy;
import evgenyt.dangerousgalaxy.data.Star;

public class GalaxyView extends View  {

    private Paint paint = new Paint();

    // User camera
    private long centerX = 0;
    private long centerY = 0;
    private float ratio = 1;

    private void init() {
        paint.setColor(Color.WHITE);
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
        paint.setTextSize(ratio);
        for (Star star: Galaxy.getInstance().getStars()) {
            long x = getScrX(star.getCoords().getX());
            long y = getScrY(star.getCoords().getY());
            if (x > -100 && x < getWidth() + 100 && y > -100 && y < getHeight()) {
                float r = ratio < 1 ? 1 : ratio;
                canvas.drawCircle(x, y, r, paint);
                if (ratio > 10) {
                    canvas.drawText(star.getName(), x + r, y, paint);
                }
            }
        }
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

}