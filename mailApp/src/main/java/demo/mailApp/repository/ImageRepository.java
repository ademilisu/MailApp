package demo.mailApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import demo.mailApp.model.Image;

public interface ImageRepository extends MongoRepository<Image, String> {

}
