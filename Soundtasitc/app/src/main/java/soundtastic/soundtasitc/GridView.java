package soundtastic.soundtasitc;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Button;



public class GridView extends ActionBarActivity {

    FrameLayout gridViewLayout;
    Button[] myButton = new Button[192];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridViewLayout = (FrameLayout) findViewById(R.id.gridViewLayout);

        for (Integer i = 0; i < 16; i++)
        {
            for (Integer j = 0; j < 12; j++)
            {
                myButton[12 * i + j] = new Button(this);
                int layoutHeight = gridViewLayout.getHeight();
                int layoutWidth= gridViewLayout.getWidth();
                myButton[12 * i + j].setHeight(layoutHeight / 12);
                myButton[12 * i + j].setWidth(layoutWidth / 16);
                myButton[12 * i + j].setText("Button" + Integer.toString((12 * i + j)));

                gridViewLayout.addView(myButton[12 * i + j]);
            }
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
