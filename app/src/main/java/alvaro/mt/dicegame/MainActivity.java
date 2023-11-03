package alvaro.mt.dicegame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnPlay, btnReset;
    ImageView imgDice;

    int roll;
    int curr = 0;
    Random random;

    TableLayout table;
    MediaPlayer player;
    Animation anim;
    ArrayList ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnReset = findViewById(R.id.btnReset);
        imgDice = findViewById(R.id.imgDice);

        random = new Random();
        ids = new ArrayList<Integer>();

        player = new MediaPlayer();
        table = findViewById(R.id.table);
        setTableIds();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setText("Start");
                curr = 0;
                updateTable();
                btnPlay.setEnabled(true);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setText("Play");
                roll = random.nextInt(6) + 1;
                int resource = 0;
                switch (roll) {
                    case 1:
                        resource = R.drawable.dice1;
                        break;
                    case 2:
                        resource = R.drawable.dice2;
                        break;
                    case 3:
                        resource = R.drawable.dice3;
                        break;
                    case 4:
                        resource = R.drawable.dice4;
                        break;
                    case 5:
                        resource = R.drawable.dice5;
                        break;
                    case 6:
                        resource = R.drawable.dice6;
                        break;
                }
                imgDice.setImageResource(resource);
                curr += roll;
                if (curr > 60) {
                    curr = 60 - (curr - 60);
                }
                Log.d("TAG", String.valueOf(curr));
                updateTable();
                if (curr == 60) {
                    btnPlay.setEnabled(false);
                    Toast.makeText(MainActivity.this, "You've reached the end. Game Over!", Toast.LENGTH_LONG).show();
                    player = MediaPlayer.create(MainActivity.this, R.raw.winnie);
                    player.start();
                }
            }
        });
    }

    private void setTableIds() {
        for (int x = table.getChildCount()-1; x >= 0; x--) {
            TableRow row = (TableRow) table.getChildAt(x);
            if (x % 2 == 0) {
                for (int y = row.getChildCount()-1; y >= 0; y--) {
                    row.getChildAt(y).setId(View.generateViewId());
                    ids.add(row.getChildAt(y).getId());
                }
            } else {
                for (int y = 0; y < row.getChildCount(); y++) {
                    row.getChildAt(y).setId(View.generateViewId());
                    ids.add(row.getChildAt(y).getId());
                }
            }
        }


    }

    private void updateTable() {
        for (int x = 0; x < 60; x++) {
            TextView view = findViewById((Integer) ids.get(x));
            if (x == curr) {
                view.setBackgroundColor(Color.BLACK);
                view.setTypeface(view.getTypeface(), Typeface.BOLD);
            } else {
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setTypeface(view.getTypeface(), Typeface.NORMAL);
            }
        }
    }

}