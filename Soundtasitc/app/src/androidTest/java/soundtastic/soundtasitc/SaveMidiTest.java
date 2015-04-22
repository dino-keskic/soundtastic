package soundtastic.soundtasitc;

import junit.framework.TestCase;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

public class SaveMidiTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testInitProjectToMidiConverter() {
        ProjectToMidiConverter converter = new ProjectToMidiConverter();
        assertEquals(0,0);
    }
}