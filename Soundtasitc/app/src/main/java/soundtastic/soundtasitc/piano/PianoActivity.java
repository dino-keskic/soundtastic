package soundtastic.soundtasitc.piano;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.ArrayList;

import soundtastic.soundtasitc.R;


public class PianoActivity extends Activity implements SeekBar.OnSeekBarChangeListener{

    private SeekBar seekbar1 = null;

    private Button button1_1 = null;
    private Button button1_2 = null;
    private Button button1_3 = null;
    private Button button1_4 = null;
    private Button button1_5 = null;
    private Button button1_6 = null;
    private Button button1_7 = null;
    private Button button2_1 = null;

    private Button button1_A = null;
    private Button button1_B = null;
    private Button button1_C = null;
    private Button button1_D = null;
    private Button button1_E = null;
    private Button button1_F = null;
    private Button button1_G = null;
    private Button button1_H = null;

    private String[] noteList = {
            "C", "D", "E", "F", "G", "A", "H",
            "c", "d", "e", "f", "g", "a", "h",
            "c'", "d'", "e'", "f'", "g'", "a'", "h'",
            "c''", "d''", "e''", "f''", "g''", "a''", "h''",
            "c'''", "d'''", "e'''", "f'''", "g'''", "a'''", "h'''",
    };
    private String[] noteListHalf = {
            " ", "C#", "D#", "E#", "F#", "G#", "A#", "H#",
            "c#", "d#", "e#", "f#", "g#", "a#", "h#",
            "c'#", "d'#", "e'#", "f'#", "g'#", "a'#", "h'#",
            "c''#", "d''#", "e''#", "f''#", "g''#", "a''#", "h''#",
            "c'''#", "d'''#", "e'''#", "f'''#", "g'''#", "a'''#", "h'''#", " ",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        seekbar1 = (SeekBar)findViewById(R.id.seekBar1);
        seekbar1.setOnSeekBarChangeListener(this);

        button1_1 = (Button)findViewById(R.id.button1_1);
        button1_2 = (Button)findViewById(R.id.button1_2);
        button1_3 = (Button)findViewById(R.id.button1_3);
        button1_4 = (Button)findViewById(R.id.button1_4);
        button1_5 = (Button)findViewById(R.id.button1_5);
        button1_6 = (Button)findViewById(R.id.button1_6);
        button1_7 = (Button)findViewById(R.id.button1_7);
        button1_A = (Button)findViewById(R.id.button1_A);
        button1_B = (Button)findViewById(R.id.button1_B);
        button1_C = (Button)findViewById(R.id.button1_C);
        button1_D = (Button)findViewById(R.id.button1_D);
        button1_E = (Button)findViewById(R.id.button1_E);
        button1_F = (Button)findViewById(R.id.button1_F);
        button1_G = (Button)findViewById(R.id.button1_G);
        button1_H = (Button)findViewById(R.id.button1_H);

        button2_1 = (Button)findViewById(R.id.button2_1);
    }

    public void showHideButton(int position)
    {
        button1_A.setVisibility(View.INVISIBLE);
        button1_B.setVisibility(View.INVISIBLE);
        button1_C.setVisibility(View.INVISIBLE);
        button1_D.setVisibility(View.INVISIBLE);
        button1_E.setVisibility(View.INVISIBLE);
        button1_F.setVisibility(View.INVISIBLE);
        button1_G.setVisibility(View.INVISIBLE);
        button1_H.setVisibility(View.INVISIBLE);

        int mod_value = position%7;

        switch(mod_value)
        {
            case 0:
                button1_A.setVisibility(View.INVISIBLE);
                button1_B.setVisibility(View.VISIBLE);
                button1_C.setVisibility(View.VISIBLE);
                button1_D.setVisibility(View.INVISIBLE);
                button1_E.setVisibility(View.VISIBLE);
                button1_F.setVisibility(View.VISIBLE);
                button1_G.setVisibility(View.VISIBLE);
                button1_H.setVisibility(View.INVISIBLE);
            case 1:
                button1_A.setVisibility(View.VISIBLE);
                button1_B.setVisibility(View.INVISIBLE);
                button1_C.setVisibility(View.VISIBLE);
                button1_D.setVisibility(View.VISIBLE);
                button1_E.setVisibility(View.INVISIBLE);
                button1_F.setVisibility(View.VISIBLE);
                button1_G.setVisibility(View.VISIBLE);
                button1_H.setVisibility(View.VISIBLE);
            case 2:
                button1_A.setVisibility(View.VISIBLE);
                button1_B.setVisibility(View.VISIBLE);
                button1_C.setVisibility(View.INVISIBLE);
                button1_D.setVisibility(View.VISIBLE);
                button1_E.setVisibility(View.VISIBLE);
                button1_F.setVisibility(View.INVISIBLE);
                button1_G.setVisibility(View.VISIBLE);
                button1_H.setVisibility(View.VISIBLE);
            case 3:
                button1_A.setVisibility(View.VISIBLE);
                button1_B.setVisibility(View.VISIBLE);
                button1_C.setVisibility(View.VISIBLE);
                button1_D.setVisibility(View.INVISIBLE);
                button1_E.setVisibility(View.VISIBLE);
                button1_F.setVisibility(View.VISIBLE);
                button1_G.setVisibility(View.INVISIBLE);
                button1_H.setVisibility(View.VISIBLE);
            case 4:
                button1_A.setVisibility(View.INVISIBLE);
                button1_B.setVisibility(View.VISIBLE);
                button1_C.setVisibility(View.VISIBLE);
                button1_D.setVisibility(View.VISIBLE);
                button1_E.setVisibility(View.INVISIBLE);
                button1_F.setVisibility(View.VISIBLE);
                button1_G.setVisibility(View.VISIBLE);
                button1_H.setVisibility(View.INVISIBLE);
            case 5:
                button1_A.setVisibility(View.VISIBLE);
                button1_B.setVisibility(View.INVISIBLE);
                button1_C.setVisibility(View.VISIBLE);
                button1_D.setVisibility(View.VISIBLE);
                button1_E.setVisibility(View.VISIBLE);
                button1_F.setVisibility(View.INVISIBLE);
                button1_G.setVisibility(View.VISIBLE);
                button1_H.setVisibility(View.VISIBLE);
            case 6:
                button1_A.setVisibility(View.VISIBLE);
                button1_B.setVisibility(View.VISIBLE);
                button1_C.setVisibility(View.INVISIBLE);
                button1_D.setVisibility(View.VISIBLE);
                button1_E.setVisibility(View.VISIBLE);
                button1_F.setVisibility(View.VISIBLE);
                button1_G.setVisibility(View.INVISIBLE);
                button1_H.setVisibility(View.VISIBLE);
        }
    }


    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        button1_1.setText(noteList[progress]);
        button1_2.setText(noteList[progress + 1]);
        button1_3.setText(noteList[progress + 2]);
        button1_4.setText(noteList[progress + 3]);
        button1_5.setText(noteList[progress + 4]);
        button1_6.setText(noteList[progress + 5]);
        button1_7.setText(noteList[progress + 6]);
        button1_A.setText(noteListHalf[progress]);
        button1_B.setText(noteListHalf[progress + 1]);
        button1_C.setText(noteListHalf[progress + 2]);
        button1_D.setText(noteListHalf[progress + 3]);
        button1_E.setText(noteListHalf[progress + 4]);
        button1_F.setText(noteListHalf[progress + 5]);
        button1_G.setText(noteListHalf[progress + 6]);

        showHideButton(progress);
    }
    public void onStartTrackingTouch(SeekBar seekBar) {
        showHideButton(seekbar1.getProgress());
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        int endPosition = seekbar1.getProgress();

        button1_1.setText(noteList[endPosition]);
        button1_2.setText(noteList[endPosition + 1]);
        button1_3.setText(noteList[endPosition + 2]);
        button1_4.setText(noteList[endPosition + 3]);
        button1_5.setText(noteList[endPosition + 4]);
        button1_6.setText(noteList[endPosition + 5]);
        button1_7.setText(noteList[endPosition + 6]);
        button1_A.setText(noteListHalf[endPosition]);
        button1_B.setText(noteListHalf[endPosition + 1]);
        button1_C.setText(noteListHalf[endPosition + 2]);
        button1_D.setText(noteListHalf[endPosition + 3]);
        button1_E.setText(noteListHalf[endPosition + 4]);
        button1_F.setText(noteListHalf[endPosition + 5]);
        button1_G.setText(noteListHalf[endPosition + 6]);

        showHideButton(endPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_piano, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
