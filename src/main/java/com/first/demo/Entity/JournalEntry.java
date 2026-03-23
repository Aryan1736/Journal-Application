package com.first.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.first.demo.Enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;

    private String title;
    private String content;
    private LocalDateTime date;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Sentiment sentiment;
}