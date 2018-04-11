package org.richard.data.repository;

import org.richard.data.domain.Student;
import org.richard.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rnkoaa on 6/5/15.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
