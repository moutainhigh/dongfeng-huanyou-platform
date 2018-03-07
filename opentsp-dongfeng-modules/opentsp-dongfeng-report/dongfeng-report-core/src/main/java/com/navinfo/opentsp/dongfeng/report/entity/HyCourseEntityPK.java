package com.navinfo.opentsp.dongfeng.report.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/1.
 */
public class HyCourseEntityPK implements Serializable {
    private long personId;
    private String courseNo;

    @javax.persistence.Column(name = "person_id")
    @javax.persistence.Id
    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    @javax.persistence.Column(name = "course_no")
    @javax.persistence.Id
    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HyCourseEntityPK that = (HyCourseEntityPK) o;

        if (personId != that.personId) return false;
        if (courseNo != null ? !courseNo.equals(that.courseNo) : that.courseNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (personId ^ (personId >>> 32));
        result = 31 * result + (courseNo != null ? courseNo.hashCode() : 0);
        return result;
    }
}
