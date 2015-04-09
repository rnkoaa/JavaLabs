package com.richard.tests

import com.richard.config.ApplicationConfiguration
import com.richard.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 *  Created by rnkoaa on 3/25/15.
 */
//@ActiveProfiles("local")
@ContextConfiguration(classes = [ApplicationConfiguration.class])
class BaseSpecification extends Specification {

    /*  @Value('${activemq.url}')
      String activeMqUrl */

    @Autowired
    String activemqUrl;

    @Autowired
    UserService userService;

    def "test that spock and groovy loaded successfully"() {
        expect:
        userService != null
        activemqUrl != null
    }
}