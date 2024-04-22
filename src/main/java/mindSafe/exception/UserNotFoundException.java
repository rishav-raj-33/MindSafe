package mindSafe.exception;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class UserNotFoundException extends RuntimeException {
	
private String resouceName;
private	String fieldValue;
private	String fieldName;
	
	public UserNotFoundException(String resouceName, String fieldName, String fieldValue) {
		super(String.format("%s not found with %s :%s",resouceName,fieldName,fieldValue));
		this.resouceName = resouceName;
		this.fieldValue = fieldValue;
		this.fieldName = fieldName;
	}

}
