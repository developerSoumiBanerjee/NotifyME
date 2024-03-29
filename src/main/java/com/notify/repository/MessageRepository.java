package com.notify.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.notify.api.dto.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>{

}
