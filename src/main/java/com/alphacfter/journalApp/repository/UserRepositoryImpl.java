package com.alphacfter.journalApp.repository;

import com.alphacfter.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepositoryImpl {

    @Autowired
    public MongoTemplate mongoTemplate;

    public List<User> getUsersForSentimentAnalysis(){
        Query query = new Query();
        query.addCriteria(
                Criteria.where("email").exists(true)
                        .andOperator(
                                Criteria.where("email").regex("^[\\w.\\-]+@([\\w\\-]+\\.)+[\\w\\-]{2,6}$")
                        )
        );
        query.addCriteria(Criteria.where("sentimentAnalysis").ne(null).ne(""));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
