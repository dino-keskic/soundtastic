package soundtastic.soundtasitc;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 20.05.2015.
 */
enum TimeSignatures {
    two_quarter, three_quarter, four_quarter
}

public class ProjectInfos implements Serializable {

    private static ProjectInfos instance;

    private List<TrackInfo> tracks = new ArrayList<TrackInfo>();

    private String project_name;
    private int bpm;
    private TimeSignatures time_signature;
    private int selected_track_nr;

    public TrackInfo getTrack(int trackNr)
    {
        if(tracks.size() >= trackNr)
            return tracks.get(trackNr-1);
        return null;
    }
    public TrackInfo getSelectedTrack()
    {
        return getTrack(getSelectedTrackNr());
    }

    public void addTrack(TrackInfo track)
    {
        tracks.add(track);
    }

    public void deleteTrack(int trackNr)
    {
        if(tracks.size() >= trackNr)
            tracks.remove(trackNr-1);
    }
    public static ProjectInfos getInstance()
    {
        if(instance == null)
        {
            instance = new ProjectInfos();
        }

        return instance;
    }

    private ProjectInfos() {}

    public String getProjectName() {
        return project_name;
    }

    public int getBpm() {
        return bpm;
    }

    public TimeSignatures getTimeSignature() {
        return time_signature;
    }

    public void setProjectName(String name) {
        project_name = name;
    }

    public void  setBpm(int beats) {
        bpm = beats;
    }

    public void setTimeSignature(TimeSignatures time) {
        time_signature = time;
    }

    public void setSelectedTrackNr(int track_nr) { selected_track_nr = track_nr; }
    public int getSelectedTrackNr() { return selected_track_nr; }

    public int getAmountOfTracks()
    {
        return tracks.size();
    }

}
