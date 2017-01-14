package com.example.testapp.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class TestTable {

    @DatabaseField(generatedId=true)
    private Long id;
    @DatabaseField
    private String attendanceYmd;
    @DatabaseField
    private Date attendanceDate;
    @DatabaseField
    private Date leavingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttendanceYmd() {
        return attendanceYmd;
    }

    public void setAttendanceYmd(String attendanceYmd) {
        this.attendanceYmd = attendanceYmd;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Date getLeavingDate() {
        return leavingDate;
    }

    public void setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
    }


    @SuppressWarnings("unset")
    private TestTable(){}

    public TestTable(String attendanceYmd, Date attendanceDate, Date leavingDate) {
        this.attendanceYmd = attendanceYmd;
        this.attendanceDate = attendanceDate;
        this.leavingDate = leavingDate;
    }
}
