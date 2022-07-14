package demo.mailApp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import demo.mailApp.dto.ActionDto;
import demo.mailApp.dto.MailDto;
import demo.mailApp.model.Box;
import demo.mailApp.model.BoxItem;
import demo.mailApp.model.Mail;
import demo.mailApp.model.MailType;
import demo.mailApp.model.Profile;
import demo.mailApp.repository.BoxItemRepository;
import demo.mailApp.repository.MailRepository;

@Service
public class MailService {

	@Autowired
	private MailRepository mailRepository;

	@Autowired
	private BoxItemRepository boxItemRepository;

	@Autowired
	private AccountService accountService;

	public Mail findMail(String id) {
		return mailRepository.findById(id).orElse(null);
	}

	public BoxItem findItem(String id) {
		return boxItemRepository.findById(id).orElse(null);
	}

	public Mail saveMail(Mail mail) {
		mail.setId(UUID.randomUUID().toString());
		mail.setDate(new Date());
		return mailRepository.save(mail);
	}

	public BoxItem saveItem(BoxItem item) {
		return boxItemRepository.save(item);
	}

	public void deleteMail(String id) {
		mailRepository.deleteById(id);
	}

	public void deleteItem(String id) {
		boxItemRepository.deleteById(id);
	}

	public List<BoxItem> list(Profile profile, Box box) {
		if (box.equals(Box.INBOX)) {
			return boxItemRepository.findAllByReceiverAndBox(profile, Box.INBOX);
		}
		if (box.equals(Box.OUTBOX)) {
			return boxItemRepository.findAllBySenderAndBox(profile, Box.OUTBOX);
		}
		if (box.equals(Box.TRASH)) {
			return boxItemRepository.findAllByReceiverAndBoxOrSenderAndBox(profile, Box.TRASH, profile, Box.TRASH);
		} else
			return null;
	}

	public MailDto send(Profile profile, MailDto mailDto) {
		Mail mail = mailDto.getItem().getMail();
		mail = saveMail(mail);
		BoxItem item = null;
		if (mailDto.getReceivers() != null) {
			for (Profile temp : mailDto.getReceivers()) {
				Profile tempProfile = accountService.getProfile(temp.getUsername());
				if (tempProfile != null) {
					item = new BoxItem();
					item.setId(UUID.randomUUID().toString());
					item.setBox(Box.INBOX);
					item.setSender(profile);
					item.setType(MailType.UNREAD);
					item.setReceiver(tempProfile);
					item.setMail(mail);
					item = saveItem(item);
				}
			}
			if (item != null) {
				item.setId(UUID.randomUUID().toString());
				item.setBox(Box.OUTBOX);
				item.setType(MailType.SEEN);
				item = saveItem(item);
				mailDto.setItem(item);
				mailDto.setCode(10);
			} else {
				mailDto.setCode(0);
			}
		}

		return mailDto;
	}

	public ActionDto action(Profile profile, ActionDto actionDto, String action) {
		boolean check = false;
		List<BoxItem> list = new ArrayList<>();
		if (actionDto.getBoxItems() != null) {
			for (BoxItem temp : actionDto.getBoxItems()) {
				if (action.equals("trash")) {
					check = true;
					temp.setBox(Box.TRASH);
				}
				if (action.equals("see")) {
					check = true;
					temp.setType(MailType.SEEN);
				}
				if (action.equals("recycle")) {
					check = true;
					if (profile.getUsername().equals(temp.getSender().getUsername())) {
						temp.setBox(Box.OUTBOX);
					} else {
						temp.setBox(Box.INBOX);
					}
				}
				if (action.equals("delete")) {
					delete(temp);
				}
				if (check == true) {
					temp = saveItem(temp);
					list.add(temp);
				}
			}
			actionDto.setCode(10);
			actionDto.setBoxItems(list);
		}
		return actionDto;
	}

	public void delete(BoxItem item) {
		deleteItem(item.getId());
		List<BoxItem> list = boxItemRepository.findAllByMail(item.getMail());
		if (list.size() == 0) {
			deleteMail(item.getMail().getId());
		}
	}

	public MailDto getMail(String id) {
		MailDto mailDto = new MailDto();
		mailDto.setCode(10);
		BoxItem boxItem = findItem(id);
		mailDto.setItem(boxItem);
		return mailDto;
	}

}
