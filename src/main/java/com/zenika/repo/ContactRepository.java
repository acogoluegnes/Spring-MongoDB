/**
 * 
 */
package com.zenika.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zenika.domain.Contact;

/**
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends MongoRepository<Contact, String> {

	List<Contact> findByFirstname(String firstname);
	
}
