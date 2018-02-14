package pl.ndsm.exception;

import java.util.List;

public class ValidationException extends Exception {
	
	private List<String> messages;
	
	public ValidationException(List<String> messages) {
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	

}