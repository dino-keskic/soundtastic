package soundtastic.soundtasitc;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.RadioButton;
import com.robotium.solo.Solo;
import junit.framework.Assert;

public class SoundTasticTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo mySolo;

    public SoundTasticTest() {super(MainActivity.class);}

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testMixingInterface() {
        mySolo.clickOnText("ProjectName");
        char[] array = new char[4];
        array = "test".toCharArray();

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

        Assert.assertEquals("test", ProjectInfos.getInstance().getProjectName());
        Assert.assertEquals(120, ProjectInfos.getInstance().getBpm());
        Assert.assertEquals(TimeSignatures.TWO_QUARTER, ProjectInfos.getInstance().getTimeSignature());

        mySolo.clickOnView(mySolo.getView(R.id.mi_add_sounds));
        mySolo.clickOnView(mySolo.getView(R.id.mi_add_sounds_title));

        char[] array2 = new char[4];
        array2 = "test".toCharArray();

        for (int i = 0; i < array2.length; i++) {
            int keycode = array2[i];

            mySolo.sendKey(keycode - 68);
        }

        mySolo.clickOnView(mySolo.getView(R.id.mi_add_sounds_mic));

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
        mySolo.sleep(1000);
        mySolo.clickOnView(mySolo.getView(R.id.mixint_stop_track1));

        mySolo.sleep(5000);
        mySolo.clickOnView(mySolo.getView(R.id.mi_track_edit1));

        mySolo.clickOnView(mySolo.getView(R.id.mi_play_all));
        mySolo.sleep(500);
        mySolo.clickOnView(mySolo.getView(R.id.mi_track_copy));
        mySolo.sleep(500);
        mySolo.setProgressBar(0, 60);
        mySolo.sleep(500);
        mySolo.clickOnView(mySolo.getView(R.id.mi_enabled));
        mySolo.sleep(500);

        mySolo.clickOnView(mySolo.getView(R.id.mi_track_delete));
        mySolo.sleep(2000);
    }
}