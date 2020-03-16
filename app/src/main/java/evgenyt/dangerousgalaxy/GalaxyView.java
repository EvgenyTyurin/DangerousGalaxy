package evgenyt.dangerousgalaxy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import evgenyt.dangerousgalaxy.data.Galaxy;
import evgenyt.dangerousgalaxy.data.Star;
import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class GalaxyView extends View  {

    Paint paint = new Paint();

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
        paint.setTextSize(SpaceMath.Point.ratio);
        for (Star star: Galaxy.getInstance().getStars()) {
            canvas.drawCircle(star.getCoords().getScrX(), star.getCoords().getSxrY(),
                    SpaceMath.Point.ratio, paint);
            if (SpaceMath.Point.ratio > 10)
                canvas.drawText(star.getName(), star.getCoords().getScrX() + SpaceMath.Point.ratio,
                        star.getCoords().getSxrY(), paint);
        }
    }

}