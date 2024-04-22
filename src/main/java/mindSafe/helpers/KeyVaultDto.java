package mindSafe.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyVaultDto {
	    private Integer id;
		private String userName;
		private String password;
		private String notes; 
		private UserDto user;
}
