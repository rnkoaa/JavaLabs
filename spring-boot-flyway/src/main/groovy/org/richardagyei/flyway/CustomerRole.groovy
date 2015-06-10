package org.richardagyei.flyway

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Version

/**
 * Created by rnkoaa on 6/10/15.
 */
@Entity
@Table(name = "role")
class CustomerRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    Long id;

    @Version
    Long version;

    @Column(name = "role_name")
    String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    Set<Customer> customers = new HashSet<>(0);
}
