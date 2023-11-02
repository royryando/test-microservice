package me.royryando.example.mailservice.mailservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "mail_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailLog {

    @Id
    private String id;

    @Field(name = "mail_to")
    private String to;

    @Field(name = "mail_subject")
    private String subject;

    @Field(name = "mail_content")
    private String content;

    @Field(name = "timestamp", targetType = FieldType.INT64)
    private Long timestamp;

}
