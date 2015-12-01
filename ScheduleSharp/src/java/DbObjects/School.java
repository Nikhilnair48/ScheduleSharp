package DbObjects;

/**
 *
 * @author nikhilnair
 */
public class School implements java.io.Serializable {
    private int SchoolID;
    private String SchoolName;
    private int Semester;
    private int Days;
    private int Period;
    private String RangeLunch;
    
    public School() {
        ;
    }

    public int getSchoolID() {
        return SchoolID;
    }

    public void setSchoolID(int SchoolID) {
        this.SchoolID = SchoolID;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String SchoolName) {
        this.SchoolName = SchoolName;
    }

    public int getSemester() {
        return Semester;
    }

    public void setSemester(int Semester) {
        this.Semester = Semester;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int Days) {
        this.Days = Days;
    }

    public int getPeriod() {
        return Period;
    }

    public void setPeriod(int Period) {
        this.Period = Period;
    }

    public String getRangeLunch() {
        return RangeLunch;
    }

    public void setRangeLunch(String RangeLunch) {
        this.RangeLunch = RangeLunch;
    }
    
}
