package evgenyt.dangerousgalaxy.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import evgenyt.dangerousgalaxy.data.Star;

public class SystemView extends View {

    private final Star systemStar = GalaxyView.getTargetStar();
    private final Paint paintText = new Paint();

    public SystemView(Context context) {
        super(context);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(120, 120, 100, paintText);
        canvas.drawText(systemStar.getName(), 250, 120, paintText);
        for (int idx = 0; idx < systemStar.getPlanets().size(); idx++) {
            int y = idx * 100 + 300;
            canvas.drawCircle(120, y, 50, paintText);
            canvas.drawText(systemStar.getPlanets().get(idx).getName(), 250, y, paintText);
        }
    }
}
