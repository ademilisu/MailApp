package demo.mailApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BoxItem")
public class BoxItem {

	@Id
	private String id;

	private Box box;
	private MailType type;

	@DBRef
	private Mail mail;

	@DBRef
	private Profile sender;

	@DBRef
	private Profile receiver;

	public BoxItem() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public Profile getSender() {
		return sender;
	}

	public void setSender(Profile sender) {
		this.sender = sender;
	}

	public Profile getReceiver() {
		return receiver;
	}

	public void setReceiver(Profile receiver) {
		this.receiver = receiver;
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public MailType getType() {
		return type;
	}

	public void setType(MailType type) {
		this.type = type;
	}

}
