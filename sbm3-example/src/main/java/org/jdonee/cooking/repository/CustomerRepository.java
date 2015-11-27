/**
 * 
 */
package org.jdonee.cooking.repository;

import java.util.List;

import org.jdonee.cooking.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jdonee
 *
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

	Customer findByFirstName(String firstName);

	List<Customer> findByLastName(String lastName);

}
