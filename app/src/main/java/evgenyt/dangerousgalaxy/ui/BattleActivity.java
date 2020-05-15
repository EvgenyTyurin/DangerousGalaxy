package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.SpaceMath;
import evgenyt.dangerousgalaxy.universe.SpaceShip;

public class BattleActivity extends AppCompatActivity {

    enum BattleResult{ESCAPED, BRAKED_THROUGH, ROBBED, DESTROYED, WINNER, UNKNOWN};

    private Galaxy galaxy = Galaxy.getInstance();
    private SpaceShip playerShip = galaxy.getPlayerShip();
    public static BattleResult battleResult = BattleResult.UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        // Prepare battle info
        TextView txtPlayerShip = findViewById(R.id.textPlayerShip);
        SpaceShip.Type myType = playerShip.getType();
        txtPlayerShip.setText("You ship: " + myType +
                ", attack: " + myType.attack +
                ", speed: " + myType.speed);
        final SpaceShip enemyShip = new SpaceShip(null, null, SpaceShip.Type.DOLPHIN);
        TextView txtEnemyShip = findViewById(R.id.textEnemyShip);
        txtEnemyShip.setText("Enemy ship: " + myType +
                ", attack: " + myType.attack +
                ", speed: " + myType.speed);
        TextView txtDemand = findViewById(R.id.textDemand);
        txtDemand.setText("Enemy demands: Drop all cargo or DIE!");
        final TextView txtResult = findViewById(R.id.textResult);
        txtResult.setText("Encounter result: UNKNOWN");
        // Battle actions
        Button escapeButton = findViewById(R.id.buttonEscape);
        escapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStruggleResult(playerShip.getType().speed * 2, enemyShip.getType().speed)) {
                    battleResult = BattleResult.ESCAPED;
                }
                else {
                    battleResult = BattleResult.DESTROYED;
                }
                txtResult.setText("Encounter result: Your ship is " + battleResult.toString());
            }
        });
        Button breakButton = findViewById(R.id.buttonBreak);
        breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStruggleResult(playerShip.getType().speed, enemyShip.getType().speed)) {
                    battleResult = BattleResult.BRAKED_THROUGH;
                    playerShip.setCurrentPlanet(SystemActivity.systemView.getTargetPlanet());
                }
                else {
                    battleResult = BattleResult.DESTROYED;
                }
                txtResult.setText("Encounter result: Your ship is " + battleResult.toString());
            }
        });
        Button submitButton = findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battleResult = BattleResult.ROBBED;
                playerShip.getCargoList().clear();
                txtResult.setText("Encounter result: Your ship is " + battleResult.toString());
                playerShip.setCurrentPlanet(SystemActivity.systemView.getTargetPlanet());
            }
        });
        Button fightButton = findViewById(R.id.buttonFight);
        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStruggleResult(playerShip.getType().attack, enemyShip.getType().attack)) {
                    playerShip.setCurrentPlanet(SystemActivity.systemView.getTargetPlanet());
                    battleResult = BattleResult.WINNER;
                    txtResult.setText("Encounter result: Your ship is " + battleResult.toString());
                }
                else {
                    battleResult = BattleResult.DESTROYED;
                    playerShip.setCurrentStar(Galaxy.SOL);
                    playerShip.setCurrentPlanet(Galaxy.EARTH);
                    txtResult.setText("Encounter result: Your ship is DESTROYED! New ship provided at Earth");
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (battleResult != BattleResult.UNKNOWN)
            super.onBackPressed();
    }

    boolean getStruggleResult(int playerK, int enemyK) {
        float success = (float) playerK / (enemyK + playerK);
        return success > new Random().nextFloat();
    }
    
}
