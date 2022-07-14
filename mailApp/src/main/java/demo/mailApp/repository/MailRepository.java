package demo.mailApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import demo.mailApp.model.Mail;

public interface MailRepository extends MongoRepository<Mail, String> {

}
