package mindSafe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mindSafe.entity.User;
import mindSafe.exception.UserNotFoundException;
import mindSafe.repo.UserRepo;


@Service
public class UserDetailOverride implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	User user=this.userRepo.findByEmail(username).orElseThrow(()->new UserNotFoundException("User", "User Name", username));
		return user;
	}

}
