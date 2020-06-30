package evgenyt.dangerousgalaxy.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.Planet;
import evgenyt.dangerousgalaxy.universe.SpaceShip;
import evgenyt.dangerousgalaxy.universe.Star;

public class SystemView extends View implements View.OnTouchListener {

    private final Star systemStar = GalaxyView.getTargetStar();
    private final Paint paintText = new Paint();
    private final Paint paintStar = new Paint();
    private final Paint paintShip = new Paint();
    private final Paint paintTarget = new Paint();
    private final Paint paintPlanet = new Paint();
    private Planet targetPlanet;
    SpaceShip playerShip = Galaxy.getInstance().getPlayerShip();


    public SystemView(Context context) {
        super(context);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(30);
        paintShip.setColor(Color.CYAN);
        paintTarget.setColor(Color.RED);
        paintTarget.setStyle(Paint.Style.STROKE);
        paintTarget.setStrokeWidth(4);
        targetPlanet = null;
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        // Draw star
        paintStar.setColor(systemStar.getStarClass().getColor().toArgb());
        canvas.drawCircle(220, 120, 100, paintStar);
        canvas.drawText(systemStar.getName(), 350, 120, paintText);
        // Draw planets
        for (int idx = 0; idx < systemStar.getPlanets().size(); idx++) {
            Planet planet = systemStar.getPlanets().get(idx);
            int y = idx * 150 + 300;
            paintPlanet.setColor(planet.getPlanetType().getColor().toArgb());
            canvas.drawCircle(220, y, 50, paintPlanet);
            canvas.drawText(planet.getName() + ": " + planet.getPlanetEconomy(), 350, y, paintText);
            if (PrefsWork.readSlot(planet.getName() + ".visited").equals("1")) {
                canvas.drawText("VISITED", 350, y + 30, paintText);
            }
        }
        // Draw ship and target marks
        if (systemStar == playerShip.getCurrentStar()) {
            int x = 100;
            int y = 120;
            int l = 10;
            if (targetPlanet != null) {
                int x1 = 100;
                int y1 = systemStar.getPlanets().indexOf(targetPlanet) * 150 + 300;
                // canvas.drawLine(x1, y1, x1 - l, y1 - l * 2, paintTarget);
                // canvas.drawLine(x1, y1, x1 + l, y1 - l * 2, paintTarget);
                l += 2;
                canvas.drawCircle(x1, y1, l, paintTarget);
                canvas.drawLine(x1 - l, y1, x1 + l, y1, paintTarget);
                canvas.drawLine(x1, y1 - l, x1, y1 + l, paintTarget);

            } else {
                int x1 = 100;
                int y1 = 120;
                // canvas.drawLine(x1, y1, x1 - l, y1 - l * 2, paintTarget);
                // canvas.drawLine(x1, y1, x1 + l, y1 - l * 2, paintTarget);
                l += 2;
                canvas.drawCircle(x1, y1, l, paintTarget);
                canvas.drawLine(x1 - l, y1, x1 + l, y1, paintTarget);
                canvas.drawLine(x1, y1 - l, x1, y1 + l, paintTarget);
            }
            Planet currentPlanet = playerShip.getCurrentPlanet();
            if (currentPlanet != null) {
                y = systemStar.getPlanets().indexOf(currentPlanet) * 150 + 300;
            }
            /*
            canvas.drawLine(x, y, x - l, y - l * 2, paintShip);
            canvas.drawLine(x, y, x + l, y - l * 2, paintShip);
             */
            canvas.drawBitmap(GalaxyActivity.shipBitmap, x - 45, y - 15, paintShip);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int planetIdx = (int) (event.getY() - 300) / 150;
        if (planetIdx >= 0 && planetIdx < systemStar.getPlanets().size()) {
            targetPlanet = systemStar.getPlanets().get(planetIdx);
        } else {
            targetPlanet = null;
        }
        v.invalidate();
        return false;
    }

    public Planet getTargetPlanet() {
        return targetPlanet;
    }

    public void setTargetPlanet(Planet targetPlanet) {
        this.targetPlanet = targetPlanet;
    }
}
