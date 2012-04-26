/**
 * 
 */
package com.zenika.mongo;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.zenika.domain.Contact;
import com.zenika.repo.ContactRepository;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpringDataMongoDbTest {
	
	@Autowired ContactRepository contactRepository;

	@Test public void mongoTemplate() throws Exception {
		MongoOperations tpl = new MongoTemplate(new Mongo(),"spring-mongo");
		if(tpl.collectionExists("contact")) {
			tpl.dropCollection("contact");
		}
		DBObject doc = BasicDBObjectBuilder.start()
			.add("firstname","Arnaud")
			.add("lastname","Cogoluègnes")
			.get();
		tpl.getCollection("contact").insert(doc);
		assertThat(tpl.getCollection("contact").count(),is(1L));
		
		tpl.dropCollection("contact");
		Contact contact = new Contact("Arnaud","Cogoluègnes");
		tpl.insert(contact);
		assertThat(tpl.getCollection("contact").count(),is(1L));
		
		List<Contact> contacts = tpl.find(query(where("firstname").is("Arnaud")),Contact.class);
		assertThat(contacts.size(),is(1));
		
		contacts = tpl.find(query(where("firstname").is("Mickey")),Contact.class);
		assertThat(contacts.size(),is(0));
	}
	
	@Test public void repo() {
		contactRepository.deleteAll();
		Contact contact = new Contact("Arnaud","Cogoluègnes");
		contactRepository.save(contact);
		assertThat(contactRepository.count(),is(1L));
		
		List<Contact> contacts = contactRepository.findByFirstname("Arnaud");
		assertThat(contacts.size(),is(1));
		
		contacts = contactRepository.findByFirstname("Mickey");
		assertThat(contacts.size(),is(0));
	}
		
}
