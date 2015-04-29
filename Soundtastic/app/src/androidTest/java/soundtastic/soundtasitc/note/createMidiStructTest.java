package soundtastic.soundtasitc.note;

import android.test.AndroidTestCase;

import junit.framework.TestCase;
import java.io.File;
import java.io.IOException;
import android.util.Log;

import soundtastic.soundtasitc.midi.MidiException;
import soundtastic.soundtasitc.midi.ProjectToMidiConverter;

public class createMidiStructTest extends AndroidTestCase {

    private File file;

    protected void setUp() {
        file = new File(getContext().getCacheDir(), "test.midi");
        Log.d("FILE", String.valueOf(getContext().getCacheDir()));
    }

    protected void tearDown() {
        file.delete();
    }

    public void testCreateProject()
    {
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


        try{
            converter.writeProjectAsMidi(testProject, file);
             File newFile = ProjectToMidiConverter.getMidiFileFromProjectName(ProjectToMidiConverter.removeMidiExtensionFromString(file.getName()));
        }catch(IOException e){
            e.printStackTrace();
        } catch (MidiException e) {
            e.printStackTrace();
        }


    }
}