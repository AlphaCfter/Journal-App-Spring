package com.alphacfter.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

//The document annotation is used to map POJO with Database(MongoDB)
@Document(collection = "users")
@Data // Annotation used my Lombok to generate @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)   //only allows unique records
    @NonNull
    private String username;
    private String email;
    private boolean sentimentAnalysis;

    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntry = new ArrayList<>();
    private List<String> roles;
}

