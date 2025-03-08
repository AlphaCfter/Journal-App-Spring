package com.alphacfter.journalApp.repository;

import com.alphacfter.journalApp.entity.Config;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<Config, ObjectId> {

}
