package com.example.p2pchat.repositories;

import com.example.p2pchat.models.Message;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAll();
}
