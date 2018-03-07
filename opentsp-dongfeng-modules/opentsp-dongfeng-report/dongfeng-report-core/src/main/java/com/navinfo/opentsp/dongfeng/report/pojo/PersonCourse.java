package com.navinfo.opentsp.dongfeng.report.pojo;

import java.math.BigInteger;

/**
 * 车辆固化数据 总油耗、总行程
 * @author chenjie
 * @version 1.0
 * @since 2016-12-09
 */
public class PersonCourse
{
    private BigInteger personId;

    private String firstName;

    private String lastName;

    private String courseNo;

    private String courseName;

    public BigInteger getPersonId() {
        return personId;
    }

    public void setPersonId(BigInteger personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
