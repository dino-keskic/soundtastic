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
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/xxXXxx321/", "test.midi");

        File folder = new File(Environment.getExternalStorageDirectory() + "/xxXXxx321");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
            Log.d("MIDIF231", String.valueOf(success));
        }

        //file.createNewFile();

        Log.d("MIDIF231", String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()));
    }

    protected void tearDown() {
        /*file.delete();*/
    }

    public void testCreateProject()
    {
        Log.d("second", "Das ist eindeutig! noch immer...");
        Project testProject = new Project("testProject", 60);
        assertEquals(testProject.getName(), "testProject");

        Track firstTrack = new Track(MusicalKey.BASS, MusicalInstrument.CLARINET);
        assertEquals(firstTrack.getInstrument(), MusicalInstrument.CLARINET);

        // start note1
        NoteEvent note1_begin = new NoteEvent(NoteName.C1, true);
        assertEquals(note1_begin.getNoteName(), NoteName.C1);

        // end note1
        NoteEvent note1_end = new NoteEvent(NoteName.C1, false);
        assertEquals(note1_end.isNoteOn(), false);

        // add quarter note
        firstTrack.addNoteEvent(0, note1_begin);
        firstTrack.addNoteEvent(480, note1_end);

        testProject.addTrack("first", firstTrack);

        ProjectToMidiConverter converter = new ProjectToMidiConverter();

        Log.d("MIDIF231", "before Try");
        try{
            converter.writeProjectAsMidi(testProject, file);
            /* File newFile = ProjectToMidiConverter.getMidiFileFromProjectName(file);
                     //ProjectToMidiConverter.removeMidiExtensionFromString(file.getName()));
            newFile.createNewFile();*/
            Log.d("MIDIF231", "fileName" + file.getAbsoluteFile());
        }catch(IOException e){
            Log.d("MIDIF231", "exception createNewFile");
            e.printStackTrace();
        } catch (MidiException e) {
            Log.d("MIDIF231", "SCHEISSE!");
            e.printStackTrace();
        }
    }
}