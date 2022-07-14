package demo.mailApp.dto;

import java.util.List;

import demo.mailApp.model.BoxItem;
import demo.mailApp.model.Profile;

public class MailDto {

	private int code;
	private BoxItem item;
	private List<Profile> receivers;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public BoxItem getItem() {
		return item;
	}

	public void setItem(BoxItem item) {
		this.item = item;
	}

	public List<Profile> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<Profile> receivers) {
		this.receivers = receivers;
	}

}
