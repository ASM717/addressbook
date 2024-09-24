package com.example.addressbook.controller;

import com.example.addressbook.model.AddressBook;
import com.example.addressbook.repository.AddressBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class AddressBookController {

    private static final Logger logger = LoggerFactory.getLogger(AddressBookController.class);
    final AddressBookRepository repository;

    @Autowired
    public AddressBookController(AddressBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/addressbooks")
    public Flux<AddressBook> getAddressBooks(@RequestParam("page") Optional<Integer> pageOpt,
                                             @RequestParam("size") Optional<Integer> sizeOpt) {

        PageRequest pageRequest = PageRequest.of(
                pageOpt.orElse(0),
                sizeOpt.orElse(100));
        logger.info("getAddressBook: {}", pageRequest);

        return repository.findAllBy(pageRequest);

    }

    @PostMapping("/addressbook")
    public Mono<AddressBook> save(@RequestBody AddressBook addressBook) {
        logger.info("Save: {}", addressBook);
        return repository.save(addressBook);
    }

    // Найти по ID
    @GetMapping("/addressbook/{id}")
    public Mono<ResponseEntity<AddressBook>> findById(@PathVariable Long id) {
        logger.info("GetById: {}", id);
        return repository.findById(id)
                .map(addressBook -> new ResponseEntity<>(addressBook, HttpStatus.OK));
    }

    // Удалить по ID
    @DeleteMapping("/addressbook/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable Long id) {
        logger.info("Delete: {}", id);
        return repository.findById(id)
                .flatMap(existingAddressBook ->
                        repository.deleteById(existingAddressBook.id())
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
