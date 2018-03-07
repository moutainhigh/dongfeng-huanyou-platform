package com.navinfo.opentsp.dongfeng.report.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by Administrator on 2017/3/1.
 */
/*@Entity
@Table(name = "hy_course", schema = "")
@IdClass(HyCourseEntityPK.class)*/
public class HyCourseEntity {
    private long personId;
    private String courseNo;
    private String courseName;

    @Id
    @Column(name = "person_id")
    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    @Id
    @Column(name = "course_no")
    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    @Basic
    @Column(name = "course_name")
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HyCourseEntity that = (HyCourseEntity) o;

        if (personId != that.personId) return false;
        if (courseNo != null ? !courseNo.equals(that.courseNo) : that.courseNo != null) return false;
        if (courseName != null ? !courseName.equals(that.courseName) : that.courseName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (personId ^ (personId >>> 32));
        result = 31 * result + (courseNo != null ? courseNo.hashCode() : 0);
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        return result;
    }
}
