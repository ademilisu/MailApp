package demo.mailApp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import demo.mailApp.model.Image;
import demo.mailApp.model.Profile;
import demo.mailApp.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	public Image saveImage(Profile profile, MultipartFile file, String defaultImage) {
		Image image = profile.getImage();
		if (profile.getImage() == null) {
			image = new Image();
			image.setId(UUID.randomUUID().toString());
			defaultImage = "defaultImage";
		}
		try {
			if (file != null) {
				image = createImage(image, file.getBytes());
			} else {
				if (defaultImage.equals("defaultImage")) {
					File f = new File("src/main/resources/static/app/img/profile.png");
					FileInputStream stream = new FileInputStream(f);
					byte[] bytes = stream.readAllBytes();
					image = createImage(image, bytes);
					stream.close();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	private Image createImage(Image image, byte[] bytes) {
		image.setPhoto(new Binary(BsonBinarySubType.BINARY, bytes));
		return imageRepository.save(image);
	}
}
