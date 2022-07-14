package demo.mailApp.dto;

import demo.mailApp.model.Profile;

public class AccountDto {

	private int code;
	private Profile profile;

	public AccountDto() {
		super();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
