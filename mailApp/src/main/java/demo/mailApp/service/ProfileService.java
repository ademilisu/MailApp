package demo.mailApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import demo.mailApp.model.Image;
import demo.mailApp.model.Profile;
import demo.mailApp.repository.ProfileRepository;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ImageService imageService;

	public Profile saveProfile(Profile profile, MultipartFile file, String defaultImage) {
		Image image = imageService.saveImage(profile, file, defaultImage);
		profile.setImage(image);
		return profileRepository.save(profile);
	}

}
