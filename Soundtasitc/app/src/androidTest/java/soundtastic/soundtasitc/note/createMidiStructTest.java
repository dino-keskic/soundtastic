package soundtastic.soundtasitc.note;

import junit.framework.TestCase;


public class createMidiStructTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

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
    }
}