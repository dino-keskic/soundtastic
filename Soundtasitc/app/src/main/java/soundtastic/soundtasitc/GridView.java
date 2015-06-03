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

import java.util.*;

import soundtastic.soundtasitc.note.NoteName;


public class GridView extends ActionBarActivity{
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
    int midiLength = 20;
    FrameLayout gridViewLayout;
    TextView[][] grid = new TextView[numTones][midiLength];

    Button buttonIncrHeight;
    Button buttonDecrHeight;
    Button buttonIncrLength;
    Button buttonDecrLength;
    Button buttonNew_Delete;

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

        buttonIncrHeight.setOnClickListener(buttonHandler);
        buttonDecrHeight.setOnClickListener(buttonHandler);
        buttonIncrLength.setOnClickListener(buttonHandler);
        buttonDecrLength.setOnClickListener(buttonHandler);
        buttonNew_Delete.setOnClickListener(buttonHandler);

        createGrid();
    }

    public void createGrid()
    {
        gridViewLayout = (FrameLayout) findViewById(R.id.gridViewLayout);
        ScrollView sv = new ScrollView(this);

        TableLayout ll=new TableLayout(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        for(int i = 0; i<numTones; i++)
        {
            TableRow tbrow = new TableRow(this);

            for(int j=0; j<midiLength; j++)
            {
                TextView tv1 = new TextView(this);
                tv1.setId(i * midiLength + j);
                if (j%16 == 0)
                {
                    grid[i][j] = tv1;
                    tv1.setText(notes[i]);
                }
                else
                {
                    grid[i][j] = tv1;
                    grid[i][j].setOnClickListener(textViewHandler);
                    tv1.setBackgroundColor(Color.WHITE);
                    tv1.setText("WH");
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

    public void drawWhite(TextView tv)
    {
        tv.setText("WH");
        tv.setTextColor(Color.WHITE);
        tv.setBackground(getResources().getDrawable(R.drawable.border));
    }

    public void drawGreen(TextView tv)
    {
        tv.setText("GR");
        tv.setTextColor(0x008000);
        tv.setBackground(getResources().getDrawable(R.drawable.border_green));
    }

    public void drawBlue(TextView tv)
    {
        tv.setText("BL");
        tv.setTextColor(0x0000FF);
        tv.setBackground(getResources().getDrawable(R.drawable.border_new));

    }

    public void drawYellow(TextView tv)
    {
        tv.setText("YE");
        tv.setTextColor(0xFFFF00);
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

            if (clickedTextView.getText() == "WH")
            {
                disableButtons();
                buttonNew_Delete.setEnabled(true);
                clickedTextViewListAdd.add(clickedTextView);
                drawBlue(clickedTextView);
            }
            else if(clickedTextView.getText() == "BL")
            {
                clickedTextViewListAdd.remove(clickedTextView);
                drawWhite(clickedTextView);
            }
            else if(clickedTextView.getText() == "YE")
            {
                clickedTextViewList.remove(clickedTextView);
                drawGreen(clickedTextView);
            }
            else
            {
                enableButtons();
                drawYellow(clickedTextView);
                clickedTextViewList.add(clickedTextView);
            }
        }
    };
    View.OnClickListener buttonHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Button clickedButton = (Button) v;
            switch(clickedButton.getId())
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
            }
        }
    };

    public void increaseHeight()
    {
        ArrayList<TextView> editedTextViewList = new ArrayList<TextView>();
        for (TextView textView : clickedTextViewList) {
            int clickedID = textView.getId();
            int targedID = clickedID + midiLength;
            if (targedID > numTones * midiLength)
                targedID = clickedID;

            TextView targedTextView = (TextView) findViewById(targedID);
            drawWhite(textView);
            editedTextViewList.add(targedTextView);
            if (targedTextView.getText() != "GR")
                drawYellow(targedTextView);
        }

        clickedTextViewList.clear();
        clickedTextViewList = editedTextViewList;
    }

    public void decreaseHeight()
    {
        ArrayList<TextView> editedTextViewList = new ArrayList<TextView>();
        for (TextView textView : clickedTextViewList) {
            int clickedID = textView.getId();
            int targedID = clickedID - midiLength;
            if (targedID < 0)
                targedID = clickedID;

            TextView targedTextView = (TextView) findViewById(targedID);
            drawWhite(textView);
            editedTextViewList.add(targedTextView);
            if (targedTextView.getText() != "GR")
                drawYellow(targedTextView);
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
