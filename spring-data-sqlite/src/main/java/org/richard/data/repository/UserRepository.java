package org.richard.data.repository;

import org.richard.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by rnkoaa on 6/4/15.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByLastName(String lastName);
}
