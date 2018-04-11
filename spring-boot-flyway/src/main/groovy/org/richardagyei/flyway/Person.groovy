package org.richardagyei.flyway

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Version

/**
 * Created by rnkoaa on 6/10/15.
 */
@Entity
@Table(name = "PERSON")
class Person implements Serializable {
    @Id
    @GeneratedValue
    long id
    @Column(name = "first_name")
    String firstName

    @Column(name = "last_name")
    String lastName



    @Override
    public String toString() {
        return "Person [id= " + id + " firstName=" + this.firstName + ", " +
                "lastName=" + this.lastName + ", " +
                "Version=" + this.version + "]";
    }
}
