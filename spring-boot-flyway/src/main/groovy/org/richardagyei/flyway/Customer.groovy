package org.richardagyei.flyway

import javax.persistence.*

/**
 * Created by rnkoaa on 6/10/15.
 */
@Entity
@Table(name = "customer")
class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    String firstname;

    @Column(name = "last_name", nullable = false)
    String lastname;


    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_roles",
            joinColumns =
                    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"),
            inverseJoinColumns =
                    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    Set<CustomerRole> roles = new HashSet<>(0);

    public Customer() {
    }

    public Customer(String email, String password, Set<CustomerRole> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", version=" + version +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles.size() +
                '}';
    }

    public void addRole(CustomerRole customerRole) {
        if (roles == null)
            roles = new HashSet<>();
        roles.add(customerRole)
    }
}
