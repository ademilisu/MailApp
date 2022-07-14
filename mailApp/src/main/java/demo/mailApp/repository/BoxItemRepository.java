package demo.mailApp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import demo.mailApp.model.Box;
import demo.mailApp.model.BoxItem;
import demo.mailApp.model.Mail;
import demo.mailApp.model.Profile;

public interface BoxItemRepository extends MongoRepository<BoxItem, String> {

	List<BoxItem> findAllByMail(Mail mail);

	List<BoxItem> findAllByReceiverAndBox(Profile profile, Box type);

	List<BoxItem> findAllBySenderAndBox(Profile profile, Box type);

	List<BoxItem> findAllByReceiverAndBoxOrSenderAndBox(Profile profile, Box box, Profile profile2, Box trash);

}
