package org.richard.data.domain;

import javax.persistence.*;

/**
 * Created by rnkoaa on 6/5/15.
 package com.vaannila.student;

 * 	This class contains the course details.
 *
 */
@Entity
@Table(name = "course")
public class Course implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private long courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    public Course() {
    }

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public long getCourseId() {
        return this.courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

}