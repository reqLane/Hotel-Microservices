package com.naukma.clientservice;

import com.naukma.clientservice.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends CrudRepository<Client, Integer> {

    Client findFirstByEmailEquals(String email);

    boolean existsClientByEmailEquals(String email);
}
