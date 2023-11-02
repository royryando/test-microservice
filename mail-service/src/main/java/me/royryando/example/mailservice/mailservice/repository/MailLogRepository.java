package me.royryando.example.mailservice.mailservice.repository;

import me.royryando.example.mailservice.mailservice.model.MailLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MailLogRepository extends MongoRepository<MailLog, String> {
    @Query("{}")
    Page<MailLog> getPageable(Pageable pageable);
}
