package soundtastic.soundtasitc;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;


/**
 * Created by Dominik on 22.04.2015.
 */
public class MixingInterfaceTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo mySolo;

    public MixingInterfaceTest() {super(MainActivity.class);}

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testButtons() {

        mySolo.clickOnView(mySolo.getView(R.id.buttonRec));
        mySolo.clickOnView(mySolo.getView(R.id.buttonPauseRec));
        mySolo.clickOnView(mySolo.getView(R.id.buttonStopRec));
        mySolo.clickOnView(mySolo.getView(R.id.buttonRec));
        mySolo.clickOnView(mySolo.getView(R.id.buttonSaveRec));
        mySolo.clickOnView(mySolo.getView(R.id.buttonDiscardRec));
        mySolo.clickOnView(mySolo.getView(R.id.buttonPlay));
        mySolo.clickOnView(mySolo.getView(R.id.buttonPause));
        mySolo.clickOnView(mySolo.getView(R.id.buttonStop));
        mySolo.clickOnView(mySolo.getView(R.id.buttonForward));
        mySolo.clickOnView(mySolo.getView(R.id.buttonRewind));
        mySolo.clickOnView(mySolo.getView(R.id.media));
    }

    public void testPlay() {
        mySolo.clickOnView(mySolo.getView(R.id.buttonPlay));
        mySolo.sleep(1200);
    }

    public void testStop() {
        mySolo.clickOnView(mySolo.getView(R.id.buttonPlay));
        mySolo.sleep(1200);
        mySolo.clickOnView(mySolo.getView(R.id.buttonStop));
        mySolo.sleep(1200);

    }

    public void testPause() {
        mySolo.clickOnView(mySolo.getView(R.id.buttonPlay));
        mySolo.sleep(2000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonPause));
        mySolo.sleep(2000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonPlay));
        mySolo.sleep(2000);
    }

    public void testForward() {
        mySolo.clickOnView(mySolo.getView(R.id.buttonPlay));
        mySolo.sleep(2000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonForward));
        mySolo.sleep(2000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonForward));
        mySolo.sleep(2000);
    }

    public void testRewind() {
        mySolo.clickOnView(mySolo.getView(R.id.buttonPlay));
        mySolo.sleep(8000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonRewind));
        mySolo.sleep(2000);
        mySolo.clickOnView(mySolo.getView(R.id.buttonRewind));
        mySolo.sleep(2000);
    }

    public void testMedia() {
        mySolo.clickOnView(mySolo.getView(R.id.media));
        mySolo.sleep(2000);
    }

}
