package evgenyt.dangerousgalaxy.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import evgenyt.dangerousgalaxy.data.Galaxy;
import evgenyt.dangerousgalaxy.data.Planet;
import evgenyt.dangerousgalaxy.data.SpaceShip;
import evgenyt.dangerousgalaxy.data.Star;

public class SystemView extends View implements View.OnTouchListener {

    private final Star systemStar = GalaxyView.getTargetStar();
    private final Paint paintText = new Paint();
    private final Paint paintShip = new Paint();
    private final Paint paintTarget = new Paint();
    private Planet targetPlanet;

    public SystemView(Context context) {
        super(context);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(30);
        paintShip.setColor(Color.CYAN);
        paintTarget.setColor(Color.RED);
        targetPlanet = null;
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(220, 120, 100, paintText);
        canvas.drawText(systemStar.getName(), 350, 120, paintText);
        for (int idx = 0; idx < systemStar.getPlanets().size(); idx++) {
            int y = idx * 150 + 300;
            canvas.drawCircle(220, y, 50, paintText);
            canvas.drawText(systemStar.getPlanets().get(idx).getName(), 350, y, paintText);
        }
        SpaceShip playerShip = Galaxy.getInstance().getPlayerShip();
        if (systemStar == playerShip.getCurrentStar()) {
            int x = 100;
            int y = 120;
            int l = 10;
            if (targetPlanet != null) {
                int x1 = 100;
                int y1 = systemStar.getPlanets().indexOf(targetPlanet) * 150 + 300;
                canvas.drawLine(x1, y1, x1 - l, y1 - l * 2, paintTarget);
                canvas.drawLine(x1, y1, x1 + l, y1 - l * 2, paintTarget);
            }
            Planet currentPlanet = playerShip.getCurrentPlanet();
            if (currentPlanet != null) {
                y = systemStar.getPlanets().indexOf(currentPlanet) * 150 + 300;
            }
            canvas.drawLine(x, y, x - l, y - l * 2, paintShip);
            canvas.drawLine(x, y, x + l, y - l * 2, paintShip);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int planetIdx = (int) (event.getY() - 300) / 150;
        if (planetIdx >= 0 && planetIdx < systemStar.getPlanets().size()) {
            targetPlanet = systemStar.getPlanets().get(planetIdx);
        }
        v.invalidate();
        return false;
    }

    public Planet getTargetPlanet() {
        return targetPlanet;
    }
}
