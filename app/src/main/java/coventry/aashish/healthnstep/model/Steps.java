package coventry.aashish.healthnstep.model;

public class Steps {
    private int daysid;
    private String stepsid;
    private String days;

    public Steps(int daysid, String stepid, String days) {
        this.daysid = daysid;
        this.stepsid = stepid;
        this.days = days;
    }

    public int getDaysid() {
        return daysid;
    }

    public void setDaysid(int daysid) {
        this.daysid = daysid;
    }

    public String getStepsid() {
        return stepsid;
    }

    public void setStepsid(String stepsid) {
        this.stepsid = stepsid;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
