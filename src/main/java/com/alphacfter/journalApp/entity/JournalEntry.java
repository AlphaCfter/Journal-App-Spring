package com.alphacfter.journalApp.entity;

import lombok.Data;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//The document annotation is used to map POJO with Database(MongoDB)
@Document(collection = "journal_entries")
@Data // Annotation used my Lombok to generate @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
public class JournalEntry {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;

}

