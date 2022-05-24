package com.example.alarm_sb;

public class Time {
    private int hour, minute;
    private String  am_pm;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getAm_pm() {
        return am_pm;
    }

    public void setAm_pm(String am_pm) {
        this.am_pm = am_pm;
    }

    @Override
    public String toString(){
        final StringBuffer sb = new StringBuffer("Time{");
        sb.append("hour=").append(hour);
        sb.append(",minute=").append(minute);
        sb.append('}');
        return sb.toString();
    }

    public void setMonth(String month) {
    }
}
