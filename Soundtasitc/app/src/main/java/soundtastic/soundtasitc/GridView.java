package soundtastic.soundtasitc;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import soundtastic.soundtasitc.note.NoteName;


public class GridView extends ActionBarActivity implements View.OnClickListener{
    String[] notes = {"A''", "A''#", "H''",
            "C'","C'#", "D'", "D'#", "E'", "F'", "F'#", "G'", "G'#", "A'", "A'#", "H'",
            "C","C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "H",
            "c","c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "h",
            "c'","c'#", "d'", "d'#", "e'", "f'", "f'#", "g'", "g'#", "a'", "a'#", "h'",
            "c''","c''#", "d''", "d''#", "e''", "f''", "f''#", "g''", "g''#", "a''", "a''#", "h''",
            "c'''","c'''#", "d'''", "d'''#", "e'''", "f'''", "f'''#", "g'''", "g'''#", "a'''", "a'''#", "h'''",
            "c''''","c''''#", "d''''", "d''''#", "e''''", "f''''", "f''''#", "g''''", "g''''#", "a''''", "a''''#", "h''''",
            "c''''''"};

    int numTones = notes.length;
    int midiLength = 100;
    FrameLayout gridViewLayout;
    FrameLayout pianoLayout;
    TextView[][] grid = new TextView[numTones][midiLength];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        createGrid();
    }

    public void createGrid()
    {
        gridViewLayout = (FrameLayout) findViewById(R.id.gridViewLayout);
        ScrollView sv = new ScrollView(this);

        TableLayout ll=new TableLayout(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        for(int i=0; i<numTones; i++)
        {
            TableRow tbrow = new TableRow(this);

            for(int j=0; j<midiLength; j++)
            {
                TextView tv1 = new TextView(this);
                String s1 = Integer.toString(i);
                String s2 = Integer.toString(j);
                String s3 = s1 + s2;
                int id = Integer.parseInt(s3);
                tv1.setId(id);
                if (j%16 == 0)
                {
                    grid[i][j] = tv1;
                    tv1.setText(notes[i]);
                }
                else
                {
                    grid[i][j] = tv1;
                    grid[i][j].setOnClickListener(this);
                    tv1.setBackgroundColor(Color.WHITE);
                    tv1.setText("=0");
                    tv1.setTextColor(Color.WHITE);
                    tv1.setBackground(getResources().getDrawable(R.drawable.border));
                }
                tbrow.addView(tv1);
            }
            ll.addView(tbrow);
        }
        hsv.addView(ll);
        sv.addView(hsv);
        gridViewLayout.addView(sv);
    }
    @Override
    public void onClick(View v) {

        TextView clickedTextView = (TextView) v;
        if (clickedTextView.getText() == "=0")
        {
            clickedTextView.setText(" ");
            clickedTextView.setBackground(getResources().getDrawable(R.drawable.border_green));
        }
        else
        {
            clickedTextView.setText("=0");
            clickedTextView.setTextColor(Color.WHITE);
            clickedTextView.setBackground(getResources().getDrawable(R.drawable.border));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid_view, menu);
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
