package org.richardagyei.flyway

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Created by rnkoaa on 6/10/15.
 */
@Configuration
@SpringBootApplication
@ComponentScan
class SpringConfig implements CommandLineRunner {

    /* @Autowired
     private PersonRepository repository;*/

    @Autowired
    CustomerRepository customerRepository

    @Autowired
    CustomerRoleRepository customerRoleRepository

    @Override
    public void run(String... args) throws Exception {
        //System.err.println(this.customerRepository.findAll());
        Customer customer = customerRepository.findByEmail("richard.agyei@gmail.com")
        Set<CustomerRole> roles = customer.roles
        roles.each { println it.roleName }
        CustomerRole customerRole = customerRoleRepository.save(new CustomerRole(roleName: "CUSTOMER"))
        customer.addRole(customerRole)
        customerRepository.save(customer)

        Customer foundCustomer = customerRepository.findByEmail("richard.agyei@gmail.com")
        roles = foundCustomer.roles
        println "-----------------------------------"
        roles.each { println it.roleName }
        println customer
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringConfig.class, args);
    }
}
