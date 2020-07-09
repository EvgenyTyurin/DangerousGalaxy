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
import evgenyt.dangerousgalaxy.universe.PlayerInfo;
import evgenyt.dangerousgalaxy.universe.SpaceMath;
import evgenyt.dangerousgalaxy.universe.SpaceShip;

public class BattleActivity extends AppCompatActivity {

    enum BattleResult{ESCAPED, BRAKED_THROUGH, ROBBED, DESTROYED, WINNER, UNKNOWN};

    private Galaxy galaxy = Galaxy.getInstance();
    private SpaceShip playerShip = galaxy.getPlayerShip();
    private final PlayerInfo playerInfo = galaxy.getPlayer();
    public static BattleResult battleResult = BattleResult.UNKNOWN;
    TextView txtResult;
    Button escapeButton, breakButton, submitButton, fightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        // Prepare battle info
        txtResult = findViewById(R.id.textResult);
        TextView txtPlayerShip = findViewById(R.id.textPlayerShip);
        SpaceShip.Type myType = playerShip.getType();
        txtPlayerShip.setText("You ship: " + myType +
                ", attack: " + myType.attack +
                ", speed: " + myType.speed);
        SpaceShip.Type enemyType = SpaceShip.Type.values()[new Random().nextInt(SpaceShip.Type.values().length)];
        final SpaceShip enemyShip = new SpaceShip(null, null, enemyType);
        TextView txtEnemyShip = findViewById(R.id.textEnemyShip);
        txtEnemyShip.setText("Enemy ship: " + enemyType +
                ", attack: " + enemyType.attack +
                ", speed: " + enemyType.speed);
        TextView txtDemand = findViewById(R.id.textDemand);
        txtDemand.setText("Enemy demands: Drop all cargo or DIE!");
        txtResult.setText("Encounter result: UNKNOWN");
        // Battle actions
        escapeButton = findViewById(R.id.buttonEscape);
        escapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStruggleResult(playerShip.getType().speed * 4, enemyShip.getType().speed)) {
                    battleResult = BattleResult.ESCAPED;
                    txtResult.setText("Encounter result: Your ship is " + battleResult.toString());
                }
                else {
                    shipDestroyed();
                }
                disableButtons();
            }
        });
        breakButton = findViewById(R.id.buttonBreak);
        breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStruggleResult(playerShip.getType().speed * 2, enemyShip.getType().speed)) {
                    battleResult = BattleResult.BRAKED_THROUGH;
                    playerShip.setCurrentPlanet(SystemActivity.systemView.getTargetPlanet());
                    txtResult.setText("Encounter result: Your ship is " + battleResult.toString());
                }
                else {
                    shipDestroyed();
                }
                disableButtons();
            }
        });
        submitButton = findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battleResult = BattleResult.ROBBED;
                playerShip.getCargoList().clear();
                txtResult.setText("Encounter result: Your ship is " + battleResult.toString());
                playerShip.setCurrentPlanet(SystemActivity.systemView.getTargetPlanet());
                disableButtons();
            }
        });
        fightButton = findViewById(R.id.buttonFight);
        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStruggleResult(playerShip.getType().attack * 2, enemyShip.getType().attack)) {
                    playerShip.setCurrentPlanet(SystemActivity.systemView.getTargetPlanet());
                    battleResult = BattleResult.WINNER;
                    int price = enemyShip.getType().price / 50;
                    playerInfo.credBalance(price);
                    txtResult.setText("Encounter result: Your ship is " + battleResult.toString() +
                            "Awarded " + price +"cr.");
                    PrefsWork.changeIntSlot("kills", 1);
                }
                else {
                    shipDestroyed();
                }
                disableButtons();
            }
        });
    }

    private void disableButtons() {
        escapeButton.setEnabled(false);
        breakButton.setEnabled(false);
        submitButton.setEnabled(false);
        fightButton.setEnabled(false);
    }

    private void shipDestroyed() {
        SpaceMath.shipDestruct();
        battleResult = BattleResult.DESTROYED;
        txtResult.setText("Encounter result: Your ship is DESTROYED! New ship provided at Earth");
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
