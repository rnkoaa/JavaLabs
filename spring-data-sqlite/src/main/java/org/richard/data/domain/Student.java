package org.richard.data.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rnkoaa on 6/5/15.
 * This class contains the student details.
 */
@Entity
@Table(name = "student")
public class Student implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private long studentId;

    @Column(name = "student_name", nullable = false, length = 100)
    private String studentName;

    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = {@JoinColumn(name = "student_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "course_id", nullable = false, updatable = false)})
    private Set<Course> courses = new HashSet<>(0);

    public Student() {
    }

    public Student(String studentName) {
        this.studentName = studentName;
    }

    public Student(String studentName, Set<Course> courses) {
        this.studentName = studentName;
        this.courses = courses;
    }

    public long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Set<Course> getCourses() {
        //force clients through our add and remove methods
        return Collections.unmodifiableSet(courses);
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

}