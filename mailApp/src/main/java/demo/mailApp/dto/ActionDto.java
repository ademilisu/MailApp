package demo.mailApp.dto;

import java.util.List;

import demo.mailApp.model.BoxItem;

public class ActionDto {

	private int code;
	private List<BoxItem> boxItems;

	public ActionDto() {
	}

	public List<BoxItem> getBoxItems() {
		return boxItems;
	}

	public void setBoxItems(List<BoxItem> boxItems) {
		this.boxItems = boxItems;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
