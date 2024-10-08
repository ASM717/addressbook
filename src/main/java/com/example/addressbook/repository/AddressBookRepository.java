package com.example.addressbook.repository;

import com.example.addressbook.model.AddressBook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AddressBookRepository extends ReactiveCrudRepository<AddressBook, Long> {

    Flux<AddressBook> findAllBy(Pageable pageable);

}
