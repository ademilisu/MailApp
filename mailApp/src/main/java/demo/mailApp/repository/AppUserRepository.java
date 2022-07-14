package demo.mailApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import demo.mailApp.model.AppUser;

public interface AppUserRepository extends MongoRepository<AppUser, String> {

	AppUser findByUsername(String username);

}
