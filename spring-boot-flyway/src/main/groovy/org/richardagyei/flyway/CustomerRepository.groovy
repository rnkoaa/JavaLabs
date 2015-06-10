package org.richardagyei.flyway

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by rnkoaa on 6/10/15.
 */
interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email)
}