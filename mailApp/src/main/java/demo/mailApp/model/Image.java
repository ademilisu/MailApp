package demo.mailApp.model;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Image")
public class Image {

	private String id;

	private Binary photo;

	public Image() {

	}

	public void setPhoto(Binary photo) {
		this.photo = photo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getPhoto() {
		return photo.getData();
	}

	public void setPhoto(String photo) {
		Binary binary = new Binary(photo.getBytes());
		this.photo = binary;
	}

}
