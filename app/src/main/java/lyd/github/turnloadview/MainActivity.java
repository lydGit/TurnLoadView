package lyd.github.turnloadview;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import lyd.github.library.Turn;
import lyd.github.library.TurnLoadView;

public class MainActivity extends AppCompatActivity {

    private TurnLoadView tlvLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tlvLoad = (TurnLoadView) findViewById(R.id.tlv_load);
        tlvLoad.setSpeed(32);
        tlvLoad.setMaxRange(180);
        tlvLoad.setStrokeWidth(30);
        tlvLoad.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueGrey));
        tlvLoad.setTurnColorList(ContextCompat.getColor(this, R.color.colorBlue), ContextCompat.getColor(this, R.color.colorTeal));

        tlvLoad.addTurn(0, new Turn(30, ContextCompat.getColor(this, R.color.colorDeepPurple), true));
    }
}
