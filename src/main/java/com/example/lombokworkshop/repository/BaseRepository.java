package com.example.lombokworkshop.repository;

import com.example.lombokworkshop.model.Serializable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T extends Serializable> extends MongoRepository<T, String> {

}
