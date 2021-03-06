package org.richardagyei.flyway

import org.hibernate.annotations.Type
import org.joda.time.DateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Version

/**
 * Created by rnkoaa on 6/10/15.
 */
@MappedSuperclass
abstract class BaseEntity {
    @Version
    long version

    @CreatedDate
    @Column(name = "created_time")
    DateTime created = DateTime.now();

    @LastModifiedDate
    @Column(name = "last_updated")
    DateTime updated = DateTime.now()

}
