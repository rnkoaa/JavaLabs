package org.richardagyei.flyway

import javax.persistence.*

/**
 * Created by rnkoaa on 6/10/15.
 */
@Entity
@Table(name = "role")
class CustomerRole extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    Long id;

    @Column(name = "role_name")
    String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    Set<Customer> customers = new HashSet<>(0);
}
