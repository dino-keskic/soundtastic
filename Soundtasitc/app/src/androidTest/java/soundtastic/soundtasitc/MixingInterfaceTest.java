package soundtastic.soundtasitc;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.robotium.solo.Solo;

import junit.framework.Assert;

import static android.view.View.*;


/**
 * Created by Dominik on 22.04.2015.
 */
public class MixingInterfaceTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo mySolo;

    private static final String trackOne = "testeins";
    private static final String trackTwo = "testzwei";

    public MixingInterfaceTest() {super(MainActivity.class);}

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }


    public void testMixingInterface() {

        testSetProjectInfos();

        mySolo.clickOnView(mySolo.getView(R.id.mixint_add_sounds));
        mySolo.clickOnView(mySolo.getView(R.id.mixint_add_sounds_title));

        char[] array2 = null;
        array2 = trackOne.toCharArray();

        for (int i = 0; i < array2.length; i++) {
            int keycode = array2[i];

            mySolo.sendKey(keycode - 68);
        }

        mySolo.clickOnView(mySolo.getView(R.id.mixint_add_sounds_mic));

        mySolo.clickOnView(mySolo.getView(R.id.mixint_rec_track1));
        mySolo.sleep(2000);

        mySolo.clickOnView(mySolo.getView(R.id.buttonRecord));
        mySolo.sleep(5000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonRecord));

        mySolo.clickOnView(mySolo.getView(R.id.buttonPlay));
        mySolo.sleep(2000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonStop));
        mySolo.sleep(2000);

        mySolo.clickOnView(mySolo.getView(R.id.buttonSave));
        mySolo.sleep(2000);

        mySolo.clickOnView(mySolo.getView(R.id.mixint_play_track1));
        mySolo.sleep(5000);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_stop_track1));

        mySolo.clickOnView(mySolo.getView(R.id.mixint_play_all));
        mySolo.sleep(500);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_stop_all));
        mySolo.sleep(500);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_track_copy));
        mySolo.sleep(500);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_enabled));
        mySolo.sleep(500);

        mySolo.clickOnView(mySolo.getView(R.id.mixint_track_delete));
        mySolo.sleep(2000);

        mySolo.clickOnView(mySolo.getView(R.id.mixint_track_rename));
        mySolo.sleep(500);

        mySolo.clickOnView(mySolo.getView(R.id.mixint_rename_track_title));


        char[] array3 = new char[4];
        array3 = "tesx".toCharArray();

        for (int i = 0; i < array3.length; i++) {
            int keycode = array3[i];

            mySolo.sendKey(keycode - 68);
        }

        mySolo.clickOnView(mySolo.getView(R.id.mixint_rename_track_save));
        mySolo.sleep(500);
    }

    public void testSetProjectInfos() {
        mySolo.clickOnText("ProjectName");
        char[] array = new char[4];
        array = trackOne.toCharArray();

        for (int i = 0; i < array.length; i++) {
            int keycode = array[i];

            mySolo.sendKey(keycode - 68);
        }

        RadioButton rb = (RadioButton) mySolo.getView(R.id.twoquarter);
        mySolo.clickOnView(rb);

        mySolo.setProgressBar(0, 60);

        Button apply = (Button) mySolo.getView(R.id.applyButton);
        mySolo.clickOnView(apply);
        mySolo.sleep(3000);

        Assert.assertEquals(trackOne, ProjectInfos.getInstance().getProjectName());
        Assert.assertEquals(120, ProjectInfos.getInstance().getBpm());
        Assert.assertEquals(TimeSignatures.two_quarter, ProjectInfos.getInstance().getTimeSignature());
    }

    public void testAddTracks() {
        testSetProjectInfos();

        mySolo.clickOnView(mySolo.getView(R.id.mixint_add_sounds));
        mySolo.clickOnView(mySolo.getView(R.id.mixint_add_sounds_title));

        char[] array = null;
        array = trackOne.toCharArray();

        for (int i = 0; i < array.length; i++) {
            int keycode = array[i];

            mySolo.sendKey(keycode - 68);
        }
        mySolo.sleep(3000);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_add_sounds_mic));

        mySolo.clickOnView(mySolo.getView(R.id.mixint_add_sounds));
        mySolo.clickOnView(mySolo.getView(R.id.mixint_add_sounds_title));


        array = trackTwo.toCharArray();

        for (int i = 0; i < array.length; i++) {
            int keycode = array[i];

            mySolo.sendKey(keycode - 68);
        }
        mySolo.sleep(3000);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_add_sounds_mic));

        mySolo.clickOnText(trackOne);
        mySolo.clickOnText(trackTwo);

        RelativeLayout rl = (RelativeLayout)mySolo.getView(R.id.mixint_layout_track1);
        assertTrue(rl.getVisibility() == VISIBLE);

        rl = (RelativeLayout)mySolo.getView(R.id.mixint_layout_track2);
        assertTrue(rl.getVisibility() == VISIBLE);

        rl = (RelativeLayout)mySolo.getView(R.id.mixint_layout_track3);
        assertFalse(rl.getVisibility() == VISIBLE);

        rl = (RelativeLayout)mySolo.getView(R.id.mixint_layout_track4);
        assertFalse(rl.getVisibility() == VISIBLE);

        mySolo.sleep(3000);
    }

    public void testDeleteTracks() {
        testAddTracks();

        mySolo.clickOnView(mySolo.getView(R.id.mixint_track_delete));
        mySolo.sleep(2000);

        mySolo.clickOnText(trackOne);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_track_delete));
        mySolo.sleep(2000);

        RelativeLayout rl = (RelativeLayout)mySolo.getView(R.id.mixint_layout_track1);
        assertFalse(rl.getVisibility() == VISIBLE);

        rl = (RelativeLayout)mySolo.getView(R.id.mixint_layout_track2);
        assertFalse(rl.getVisibility() == VISIBLE);

        rl = (RelativeLayout)mySolo.getView(R.id.mixint_layout_track3);
        assertFalse(rl.getVisibility() == VISIBLE);

        rl = (RelativeLayout)mySolo.getView(R.id.mixint_layout_track4);
        assertFalse(rl.getVisibility() == VISIBLE);

    }

    public void testEnabledTracks() {
        testAddTracks();

        mySolo.clickOnText(trackOne);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_enabled));

        mySolo.clickOnText(trackTwo);
        mySolo.sleep(200);
        //  mySolo.clickOnView(mySolo.getView(R.id.mixint_enabled));
        CheckBox cb = (CheckBox)mySolo.getView(R.id.mixint_enabled);
        assertTrue(cb.isChecked());

        mySolo.clickOnText(trackOne);
        mySolo.sleep(200);
        assertFalse(cb.isChecked());
    }

    public void testRecordTrack1() {
        testAddTracks();

        mySolo.clickOnText(trackOne);
        mySolo.sleep(200);

        mySolo.clickOnView(mySolo.getView(R.id.mixint_rec_track1));
        mySolo.sleep(200);

        mySolo.clickOnView(mySolo.getView(R.id.buttonRecord));
        mySolo.sleep(3000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonRecord));

        mySolo.clickOnView(mySolo.getView(R.id.buttonPlay));
        mySolo.sleep(2000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonStop));
        mySolo.sleep(2000);

        mySolo.clickOnView(mySolo.getView(R.id.buttonSave));
        mySolo.sleep(200);

        mySolo.clickOnView(mySolo.getView(R.id.mixint_play_track1));
        mySolo.sleep(3000);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_stop_track1));
        mySolo.sleep(2000);
    }

    public void testRecordTrack2() {
        testAddTracks();

        mySolo.clickOnText(trackTwo);
        mySolo.sleep(200);

        mySolo.clickOnView(mySolo.getView(R.id.mixint_rec_track2));
        mySolo.sleep(200);

        mySolo.clickOnView(mySolo.getView(R.id.buttonRecord));
        mySolo.sleep(3000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonRecord));

        mySolo.clickOnView(mySolo.getView(R.id.buttonPlay));
        mySolo.sleep(2000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonStop));
        mySolo.sleep(2000);

        mySolo.clickOnView(mySolo.getView(R.id.buttonSave));
        mySolo.sleep(200);

        mySolo.clickOnView(mySolo.getView(R.id.mixint_play_track2));
        mySolo.sleep(3000);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_stop_track2));
        mySolo.sleep(2000);
    }

}
