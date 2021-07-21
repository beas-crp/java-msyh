package com.msyh.service.jpa;

import com.msyh.enttity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 13778
 */
public interface PersonService extends JpaRepository<Person,Long> {
}
