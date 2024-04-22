package mindSafe.service;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import mindSafe.helpers.UserDto;

public interface UserService {
	UserDto registerUser(UserDto user) throws AddressException, MessagingException;
	 UserDto updateUser(UserDto user,Integer id);
	 void disableUser(Integer id,String profilePath);
	 UserDto findUser(int id);
	 
	 
}
