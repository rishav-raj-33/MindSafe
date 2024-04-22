package mindSafe.serviceClass;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.transaction.Transactional;
import mindSafe.entity.User;
import mindSafe.exception.ResourceNotFound;
import mindSafe.helpers.AppConstants;
import mindSafe.helpers.EmaillSender;
import mindSafe.helpers.UserDto;
import mindSafe.repo.UserRepo;
import mindSafe.service.UserService;

@Service
public class UserServiceClass implements UserService {
	@Autowired
	private UserRepo repo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private FileServiceClass serviceClass;
	@Autowired
	private EmaillSender emaillSender;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private EmailTokenServiceClass tokenServiceClass;
	
	
	
	

	@Override
	@Transactional
	public UserDto registerUser(UserDto user) throws AddressException, MessagingException {
		this.emaillSender.sendEmail(user.getEmail(), AppConstants.FROM, AppConstants.WELCOME_SUBJECT, AppConstants.WELCOME_TEXT);
		user.setProfilePhoto("default.png");
		user.setPassword(this.encoder.encode(user.getPassword()));
		User registerUser=this.repo.save(this.mapper.map(user, User.class));
		   String generatedToken=this.tokenServiceClass.generateSaveToken(registerUser.getEmail(),2);
			String otpSubject=AppConstants.OTP_SUBJECT;
			String otpText="Hii, \n"+AppConstants.OTP_TEXT+"\n"+AppConstants.OTP_PARAGRAPH_STRING+generatedToken;
			this.emaillSender.sendEmail(registerUser.getEmail(), AppConstants.FROM, otpSubject, otpText);
	       return this.mapper.map(registerUser, UserDto.class);
		
	}

	@Override
	@Transactional
	public UserDto updateUser(UserDto user, Integer id) {
		User getUser=this.repo.findById(id).orElseThrow(()->new ResourceNotFound("User","id" , id));		
		getUser.setPassword(this.encoder.encode(user.getPassword()));
		getUser.setProfilePhoto(user.getProfilePhoto());
		this.repo.save(getUser);
		return this.mapper.map(this.repo.save(getUser), UserDto.class);
	}

	@Override
	@Transactional
	public void disableUser(Integer id,String profilePath) {
		User getUser=this.repo.findById(id).orElseThrow(()->new ResourceNotFound("User","id" , id));
		this.serviceClass.deleteFile(getUser.getProfilePhoto(), profilePath);
		this.repo.delete(getUser);
	}

	@Override
	public UserDto findUser(int id) {
		User getUser=this.repo.findById(id).orElseThrow(()->new ResourceNotFound("User","id" , id));
		return this.mapper.map(this.repo.save(getUser), UserDto.class);
	}

}
