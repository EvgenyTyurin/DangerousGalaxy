package evgenyt.dangerousgalaxy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import evgenyt.dangerousgalaxy.R;
import evgenyt.dangerousgalaxy.universe.Galaxy;
import evgenyt.dangerousgalaxy.universe.PlayerInfo;
import evgenyt.dangerousgalaxy.universe.SpaceShip;

public class BuyShipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ship);
        TextView txtShipBuy = findViewById(R.id.text_ship_buy);
        Intent intent = getIntent();
        String shipTypeStr = intent.getStringExtra(ShipyardActivity.EXTRA_SHIP_BUY);
        final SpaceShip.Type shipBuyType = SpaceShip.Type.valueOf(shipTypeStr);
        txtShipBuy.setText("Ship to buy: " + shipBuyType +
                " attack:" + shipBuyType.attack +
                ", speed:" + shipBuyType.speed +
                ", cargo:" + shipBuyType.maxCargo +
                ", fuel:" + shipBuyType.maxFuel +
                ", price: " + shipBuyType.price + "cr.");
        Galaxy galaxy = Galaxy.getInstance();
        final SpaceShip playerShip = galaxy.getPlayerShip();
        SpaceShip.Type playerShipType = playerShip.getType();
        TextView txtPlayerShip = findViewById(R.id.text_ship_player);
        txtPlayerShip.setText("Ship to buy: " + playerShipType +
                " attack:" + playerShipType.attack +
                ", speed:" + playerShipType.speed +
                ", cargo:" + playerShipType.maxCargo +
                ", fuel:" + playerShipType.maxFuel +
                ", price: " + playerShipType.price + "cr.");
        final int delta = shipBuyType.price - playerShipType.price;
        Button buyButton = findViewById(R.id.button_buy_ship);
        if (shipBuyType.maxCargo >= playerShip.getCurrentCargoTonnage()) {
            buyButton.setText("Buy (" + delta + "cr.)");
            buyButton.setEnabled(true);
        } else {
            buyButton.setText("Get rid of excess load");
            buyButton.setEnabled(false);
        }
        final PlayerInfo playerInfo = galaxy.getPlayer();
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerInfo.debBalance(delta)) {
                    playerShip.setType(shipBuyType);
                    playerShip.setFuel(Math.min(shipBuyType.maxFuel, playerShip.getFuel()));
                    finish();
                }
            }
        });
    }
}
