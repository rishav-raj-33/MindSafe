package mindSafe.helpers;



import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	   private Integer id;
	   @Email
	   private String email;
	   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	   @NotBlank
	   private String password;
	   @NotBlank
	   private String name;
	   private String profilePhoto;
	   
	   
}


