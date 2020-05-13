package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.SpaceShip;

public class BattleActivity extends AppCompatActivity {

    private SpaceShip playerShip = Galaxy.getInstance().getPlayerShip();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        TextView txtPlayerShip = findViewById(R.id.textPlayerShip);
        SpaceShip.Type myType = playerShip.getType();
        txtPlayerShip.setText("You ship: " + myType +
                ", attack: " + myType.attack +
                ", speed: " + myType.speed);
        SpaceShip enemyShip = new SpaceShip(null, null, SpaceShip.Type.DOLPHIN);
        TextView txtEnemyShip = findViewById(R.id.textEnemyShip);
        txtEnemyShip.setText("Enemy ship: " + myType +
                ", attack: " + myType.attack +
                ", speed: " + myType.speed);
        TextView txtDemand = findViewById(R.id.textDemand);
        txtDemand.setText("Drop all cargo or DIE!");
    }
}
