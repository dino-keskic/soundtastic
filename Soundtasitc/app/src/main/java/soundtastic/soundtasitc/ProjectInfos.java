package soundtastic.soundtasitc;


import java.io.Serializable;

import soundtastic.soundtasitc.note.Project;

/**
 * Created by Dominik on 20.05.2015.
 */
enum TimeSignatures {
    two_quarter, three_quarter, four_quarter
}

public class ProjectInfos implements Serializable {

    private String project_name;

    private int bpm;

    private TimeSignatures time_signature;

    private  Project project = new Project();

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

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

}
