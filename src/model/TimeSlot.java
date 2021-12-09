package model;

import java.util.*;
import java.time.*;

import jakarta.persistence.*;

@Entity(name = "TIME_SLOTS")
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"daysOfWeek", "startTime", "endTime"})
}) 
public class TimeSlot {
    @Id
    @Column(name = "TIME_SLOT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeSlotId;
    private byte daysOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot() {
    
    }

    public TimeSlot(byte daysOfWeek, LocalTime startTime, LocalTime endTime) {
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public byte getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(byte daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean checkTimeConflict(TimeSlot t) {
        if ((daysOfWeek & t.daysOfWeek) > 0) {
            //check if the times conflict
            return !endTime.isBefore(t.startTime) && !startTime.isAfter(t.endTime);
        }
        return false;
    }
}
