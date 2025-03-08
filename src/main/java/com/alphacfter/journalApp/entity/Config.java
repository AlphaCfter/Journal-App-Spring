package com.alphacfter.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("config_entries")
@Data
@NoArgsConstructor
public class Config {

    private String key;
    private String value;
}
