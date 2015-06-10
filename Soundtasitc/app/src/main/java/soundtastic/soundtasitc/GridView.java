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
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.*;

import soundtastic.soundtasitc.note.NoteName;


public class GridView extends Activity{
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
    //List<Integer> rawMidiValues = ProjectInfos.getInstance().getProject().getTrackByName(ProjectInfos.getInstance().getSelectedTrack().getTrackName()).getRawMidiValuesList();
    List<Integer> rawMidiValues = new ArrayList<Integer>();
    List<Integer> outputMidiValues = new ArrayList<Integer>();
    int midiLength;
    int numBar;
    int gridLength;

    FrameLayout gridViewLayout;
    TextView[][] grid;

    Button buttonIncrHeight;
    Button buttonDecrHeight;
    Button buttonIncrLength;
    Button buttonDecrLength;
    Button buttonNew_Delete;
    ImageButton buttonSave;
    ImageButton buttonCancel;

    TextView clickedTextView;
    ArrayList<TextView> clickedTextViewListAdd = new ArrayList<TextView>();
    ArrayList<TextView> clickedTextViewList = new ArrayList<TextView>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        buttonIncrHeight = (Button) findViewById(R.id.IncrHeight);
        buttonDecrHeight = (Button) findViewById(R.id.DecrHeight);
        buttonIncrLength = (Button) findViewById(R.id.IncrLength);
        buttonDecrLength = (Button) findViewById(R.id.DecrLength);
        buttonNew_Delete = (Button) findViewById(R.id.New_Delete);
        buttonSave = (ImageButton) findViewById(R.id.gridview_save);
        buttonCancel = (ImageButton) findViewById(R.id.gridview_cancel);

        buttonIncrHeight.setOnClickListener(buttonHandler);
        buttonDecrHeight.setOnClickListener(buttonHandler);
        buttonIncrLength.setOnClickListener(buttonHandler);
        buttonDecrLength.setOnClickListener(buttonHandler);
        buttonNew_Delete.setOnClickListener(buttonHandler);
        buttonSave.setOnClickListener(buttonHandler);
        buttonCancel.setOnClickListener(buttonHandler);

        buttonIncrLength.setEnabled(false);
        buttonDecrLength.setEnabled(false);

        for (int i = getResources().getInteger(R.integer.midi_nr_offset_min); i < 100; i++)
            rawMidiValues.add(i);

        midiLength = rawMidiValues.size();
        numBar = 1 + midiLength / getResources().getInteger(R.integer.bar);
        gridLength = midiLength + numBar;
        grid = new TextView[numTones][gridLength];
        createGrid();
        writeMidi2Grid();
    }

    @Override
    public void onBackPressed()
    {}

    public void createGrid()
    {
        gridViewLayout = (FrameLayout) findViewById(R.id.gridViewLayout);
        ScrollView sv = new ScrollView(this);

        TableLayout ll=new TableLayout(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        for(int i = 0; i<numTones; i++)
        {
            TableRow tbrow = new TableRow(this);

            for(int j=0; j<gridLength; j++)
            {
                TextView tv1 = new TextView(this);
                tv1.setId(i * midiLength + j);
                if (j%getResources().getInteger(R.integer.bar_plus_1) == 0)
                {
                    grid[i][j] = tv1;
                    tv1.setText(notes[i]);
                }
                else
                {
                    grid[i][j] = tv1;
                    grid[i][j].setOnClickListener(textViewHandler);
                    tv1.setBackgroundColor(Color.WHITE);
                    drawWhite(tv1);
                }
                tbrow.addView(tv1);
            }
            ll.addView(tbrow);
        }
        hsv.addView(ll);
        sv.addView(hsv);
        gridViewLayout.addView(sv);
    }

    public void writeMidi2Grid()
    {
        int time_position = 0;
        for (Integer tone : rawMidiValues)
        {
            if ((tone >= getResources().getInteger(R.integer.midi_nr_offset_min)) && (tone <= getResources().getInteger(R.integer.midi_nr_offset_max)))
            {
                int num_bar = time_position / getResources().getInteger(R.integer.bar);
                TextView position = grid[(tone - getResources().getInteger(R.integer.midi_nr_offset_min))][1 + time_position + num_bar];
                drawGreen(position);
                time_position++;
            }
        }

    }

    public void drawWhite(TextView tv)
    {
        tv.setText(R.string.white);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setBackground(getResources().getDrawable(R.drawable.border));
    }

    public void drawGreen(TextView tv)
    {
        tv.setText(R.string.green);
        tv.setTextColor(getResources().getColor(R.color.green));
        tv.setBackground(getResources().getDrawable(R.drawable.border_green));
    }

    public void drawBlue(TextView tv)
    {
        tv.setText(R.string.blue);
        tv.setTextColor(getResources().getColor(R.color.blue));
        tv.setBackground(getResources().getDrawable(R.drawable.border_new));

    }

    public void drawYellow(TextView tv)
    {
        tv.setText(R.string.yellow);
        tv.setTextColor(getResources().getColor(R.color.yellow));
        tv.setBackground(getResources().getDrawable(R.drawable.border_edit));
    }

    public void enableButtons()
    {
        buttonIncrHeight.setEnabled(true);
        buttonDecrHeight.setEnabled(true);
        buttonIncrLength.setEnabled(true);
        buttonDecrLength.setEnabled(true);
        buttonNew_Delete.setEnabled(true);
    }

    public void disableButtons()
    {
        buttonIncrHeight.setEnabled(false);
        buttonDecrHeight.setEnabled(false);
        buttonIncrLength.setEnabled(false);
        buttonDecrLength.setEnabled(false);
        buttonNew_Delete.setEnabled(false);
    }

     View.OnClickListener textViewHandler = new View.OnClickListener() {
        public void onClick(View v) {

            clickedTextView = (TextView) v;

            if (clickedTextView.getText() == getResources().getString(R.string.white))
            {
                disableButtons();
                buttonNew_Delete.setEnabled(true);
                clickedTextViewListAdd.add(clickedTextView);
                drawBlue(clickedTextView);
            }
            else if(clickedTextView.getText() == getResources().getString(R.string.blue))
            {
                clickedTextViewListAdd.remove(clickedTextView);
                drawWhite(clickedTextView);
            }
            else if(clickedTextView.getText() == getResources().getString(R.string.yellow))
            {
                clickedTextViewList.remove(clickedTextView);
                drawGreen(clickedTextView);
            }
            else
            {
                enableButtons();
                buttonIncrLength.setEnabled(false);
                buttonDecrLength.setEnabled(false);
                drawYellow(clickedTextView);
                clickedTextViewList.add(clickedTextView);
            }
        }
    };
    View.OnClickListener buttonHandler = new View.OnClickListener() {
        public void onClick(View v) {
            //Button clickedButton = (Button) v;
            switch(v.getId())
            {
                case R.id.IncrHeight:
                    increaseHeight();
                    break;
                case R.id.DecrHeight:
                    decreaseHeight();
                    break;
                case R.id.IncrLength:
                    increaseTone();
                    break;
                case R.id.DecrLength:
                    decreaseTone();
                    break;
                case R.id.New_Delete:
                    newDeleteTone();
                    break;
                case R.id.gridview_save:
                    writeGrid2Midi();
                    finish();
                    break;
                case R.id.gridview_cancel:
                    finish();
                    break;

            }
        }
    };

    public void writeGrid2Midi()
    {
        for (int time_position = 0; time_position < gridLength; time_position++)
        {
            for (int tone = 0; tone < numTones; tone++)
            {
                TextView tv = grid[tone][time_position];
                if (tv.getText() == getResources().getString(R.string.green))
                {
                    outputMidiValues.add(tone + getResources().getInteger(R.integer.midi_nr_offset_min));
                    break;
                }

            }
        }
    }

    public void increaseHeight()
    {
        ArrayList<TextView> editedTextViewList = new ArrayList<TextView>();
        for (TextView textView : clickedTextViewList) {
            int clickedID = textView.getId();
            int targetID = clickedID + midiLength;
            if (targetID > numTones * midiLength)
                targetID = clickedID;

            TextView targetTextView = (TextView) findViewById(targetID);
            drawWhite(textView);
            editedTextViewList.add(targetTextView);
            if (targetTextView.getText() != getResources().getString(R.string.green))
                drawYellow(targetTextView);
        }

        clickedTextViewList.clear();
        clickedTextViewList = editedTextViewList;
    }

    public void decreaseHeight()
    {
        ArrayList<TextView> editedTextViewList = new ArrayList<TextView>();
        for (TextView textView : clickedTextViewList) {
            int clickedID = textView.getId();
            int targetID = clickedID - midiLength;
            if (targetID < 0)
                targetID = clickedID;

            TextView targetTextView = (TextView) findViewById(targetID);
            drawWhite(textView);
            editedTextViewList.add(targetTextView);
            if (targetTextView.getText() != getResources().getString(R.string.green))
                drawYellow(targetTextView);
        }

        clickedTextViewList.clear();
        clickedTextViewList = editedTextViewList;
    }

    public void decreaseTone()
    {
    }

    public void increaseTone()
    {
    }

    public void newDeleteTone()
    {
        for (TextView textView : clickedTextViewListAdd) {
                drawGreen(textView);
            }
        clickedTextViewListAdd.clear();

        for (TextView textView : clickedTextViewList) {
                drawWhite(textView);
            }
        clickedTextViewList.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
