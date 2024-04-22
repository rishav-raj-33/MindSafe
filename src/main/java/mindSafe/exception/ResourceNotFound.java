package mindSafe.exception;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFound extends RuntimeException{
	
	String resouceName;
	int fieldValue;
	String fieldName;
	public ResourceNotFound(String resouceName, String fieldName, int fieldValue) {
		super(String.format("%s not found with %s :%s",resouceName,fieldName,fieldValue));
		this.resouceName = resouceName;
		this.fieldValue = fieldValue;
		this.fieldName = fieldName;
	}
	

}
