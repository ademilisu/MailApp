package demo.mailApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import demo.mailApp.model.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String> {

}
