package pl.ndsm.model.dataTransport;

public class ShotResultMessage {
	
	private int operationCode;
	private String localization;
	private String message;
	
	public ShotResultMessage() {
		super();
	}

	public int getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(int operationCode) {
		this.operationCode = operationCode;
	}

	public String getLocalization() {
		return localization;
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ShotResultMessage [operationCode=" + operationCode + ", localization=" + localization + ", message="
				+ message + "]";
	}
	
}
