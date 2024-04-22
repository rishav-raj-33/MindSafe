package mindSafe.serviceClass;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mindSafe.entity.EmailToken;
import mindSafe.entity.User;
import mindSafe.helpers.AppConstants;
import mindSafe.helpers.OtpGenerator;
import mindSafe.repo.EmailTokenRepo;
import mindSafe.repo.UserRepo;
import mindSafe.service.EmailTokenService;


@Service
public class EmailTokenServiceClass implements EmailTokenService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailTokenRepo repo;
	@Autowired
	private OtpGenerator generator;

	@Override
	public String generateSaveToken(String email, int time) {
		User user=this.userRepo.findByEmail(email).orElseThrow(()->new IllegalStateException("User Not Found"));
		List<EmailToken> tokens=this.repo.findByUser(user);
		if(tokens.size()>5) {
			user.setLocked(true);
			this.userRepo.save(user);
			return "User Locked Due to Multiple Request for OTP Contact to Unlock your Account: "+AppConstants.FROM;
		}
		if(user.isEnabled()) {
 			throw new IllegalStateException("Email Already Verified");
 		}
		EmailToken token=new EmailToken();
		String generateToken=generator.genrateOtp();
		token.setToken(generateToken);
		token.setCreateAt(LocalDateTime.now());
		token.setExpires(LocalDateTime.now().plusMinutes(time));
		token.setUser(user);
		this.repo.save(token);
		
		
		return token.getToken();
	}

	@Override
	public boolean ConfirmToken(String token) {
	EmailToken getToken=this.repo.findByToken(token).orElseThrow(()->new IllegalStateException("Confirmation token Not Found"));
		if(getToken.getConfirmedAt()!=null) {
			throw new IllegalStateException("E-mail is Already Confirmed");
		}
		
		if(getToken.getExpires().isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("Confirmation Token Expired");
		}
		
		User getUser=this.userRepo.findByEmail(getToken.getUser().getEmail()).get();
		getUser.setEnabled(true);
		this.userRepo.save(getUser);
		deleteAllConfirmationToken(getUser);
		return true;
	}

	@Override
	public void deleteAllConfirmationToken(User user) {
		List<EmailToken> deleteTokens=this.repo.findByUser(user);
		
		  for(EmailToken tokenToDelete:deleteTokens) {
			  this.repo.delete(tokenToDelete);
			  }
		
	}

	

}
