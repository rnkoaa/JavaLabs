package org.richardagyei.flyway

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by rnkoaa on 6/10/15.
 */
interface PersonRepository extends JpaRepository<Person, Long> {

}