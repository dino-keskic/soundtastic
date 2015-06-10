package soundtastic.soundtasitc.note;

import android.os.Environment;
import android.test.AndroidTestCase;

import junit.framework.TestCase;
import java.io.File;
import java.io.IOException;
import android.util.Log;

import soundtastic.soundtasitc.midi.MidiException;
import soundtastic.soundtasitc.midi.ProjectToMidiConverter;

public class createMidiStructTest extends AndroidTestCase {

    private File file;

    protected void setUp() throws IOException {

        File folder = new File(Environment.getExternalStorageDirectory() + "/soundtastic");

        file = new File(folder.getAbsolutePath(), "test.midi");

        if (!folder.exists()) {
            folder.mkdir();
        }
        
        assertTrue(folder.exists());
    }

    protected void tearDown() {
        /*file.delete();*/
    }

    public void testCreateProject()
    {
        Project testProject = Project.getInstance();
        testProject.setBeatsPerMinute(60);
        testProject.setName("testProject");
        assertEquals(testProject.getName(), "testProject");

        Track firstTrack = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);
        assertEquals(firstTrack.getInstrument(), MusicalInstrument.ACOUSTIC_GRAND_PIANO);

        // start note1
        NoteEvent note1_begin = new NoteEvent(NoteName.C5, true);
        assertEquals(note1_begin.getNoteName(), NoteName.C5);
        NoteEvent note2_begin = new NoteEvent(NoteName.D5, true);

        // end note1
        NoteEvent note1_end = new NoteEvent(NoteName.C5, false);
        assertFalse(note1_end.isNoteOn());
        NoteEvent note2_end = new NoteEvent(NoteName.D5, false);

        // add quarter note
        firstTrack.addNoteEvent(0, note1_begin);
        firstTrack.addNoteEvent(480, note1_end);
        firstTrack.addNoteEvent(480, note2_begin);

        firstTrack.addNoteEvent(960, note2_end);

        testProject.addTrack("first", firstTrack);

        ProjectToMidiConverter converter = new ProjectToMidiConverter();

        try{
            converter.writeProjectAsMidi(testProject, file);
        }catch(IOException e){
            e.printStackTrace();
        } catch (MidiException e) {
            e.printStackTrace();
        }

        assertTrue(file.exists());
    }
}