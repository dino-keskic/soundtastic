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
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class GridView extends ActionBarActivity {

    FrameLayout gridViewLayout;
    Button[][] myButton = new Button[16][12];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridViewLayout = (FrameLayout) findViewById(R.id.gridViewLayout);


        ScrollView sv = new ScrollView(this);

        TableLayout ll=new TableLayout(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        for(int i=1;i<30;i++) {
            TableRow tbrow=new TableRow(this);

            for(int j=1;j<=20;j++) {
                TextView tv1=new TextView(this);
                String s1 = Integer.toString(i);
                String s2 = Integer.toString(j);
                String s3 = s1+s2;
                int id = Integer.parseInt(s3);
                tv1.setId(id);

                tv1.setText("Dynamic TextView  no:     "+id);
                tbrow.addView(tv1);
            }
            ll.addView(tbrow);
        }
        hsv.addView(ll);
        sv.addView(hsv);
        gridViewLayout.addView(sv);
        setContentView(gridViewLayout);
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
