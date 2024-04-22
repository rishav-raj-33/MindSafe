package mindSafe.controllers;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mindSafe.entity.User;
import mindSafe.exception.UserNotFoundException;
import mindSafe.helpers.ApiResponse;
import mindSafe.helpers.AppConstants;
import mindSafe.helpers.EmaillSender;
import mindSafe.helpers.JwtHelper;
import mindSafe.helpers.JwtRequest;
import mindSafe.helpers.JwtResponse;
import mindSafe.helpers.UserDto;
import mindSafe.repo.UserRepo;
import mindSafe.service.UserService;
import mindSafe.serviceClass.EmailTokenServiceClass;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
    private UserService service;
	
	 @Autowired
	 private UserRepo userRepo;
	 
	 @Autowired
	 private ModelMapper mapper;
	 @Autowired
	 private EmailTokenServiceClass tokenServiceClass;
	 
	  @Autowired
	    private AuthenticationManager manager;
	  
	  @Autowired
	    private JwtHelper helper;
	  
	  @Autowired
	    private UserDetailsService userDetailsService;
	  @Autowired
	  private EmaillSender sender;
	
	
	  @PostMapping("/register")
	    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto) throws AddressException, MessagingException{
	    	this.service.registerUser(userDto);
	    	return new ResponseEntity<String>("Email Send Verify Email to Enable The Account",HttpStatus.CREATED);
	    }
	  
	  @GetMapping("/current-user/")
	 	public ResponseEntity<UserDto> getUser(Principal principal) {
	 		User user = this.userRepo.findByEmail(principal.getName()).get();
	 		return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class),HttpStatus.OK);

	    
	        }
	  
	  @PostMapping("/confirm")
		public ResponseEntity<ApiResponse> confirmEmail(@RequestParam(required = true) String confirmationToken){
			this.tokenServiceClass.ConfirmToken(confirmationToken);
			return new ResponseEntity<ApiResponse>(new ApiResponse("E-mail Verified", true),HttpStatus.ACCEPTED);
		}
	  
	  @PostMapping("/login")
	    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
			
			User user=this.userRepo.findByEmail(request.getEmail()).orElseThrow(()->new UserNotFoundException("User", "Email", request.getEmail()));
			
			if(user.isEnabled()) {
	        this.doAuthenticate(request.getEmail(), request.getPassword());
	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        String token = this.helper.generateToken(userDetails);

	        JwtResponse response = JwtResponse.builder()
	                .jwtToken(token)
	                .userName(userDetails.getUsername()).build();
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	    	 return new ResponseEntity<JwtResponse>(new JwtResponse("Not Generated,Verify the User Email First",request.getEmail()),HttpStatus.BAD_REQUEST);
		}
		}
	  
	  
	  
	  
	  
	  private void doAuthenticate(String email, String password) {

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	            manager.authenticate(authentication);


	        } catch (BadCredentialsException e) {
	        	 throw new BadCredentialsException(" Invalid Username or Password  !!");
	        }

	    }
	  
	  @PostMapping("/token/resend")
	  @Transactional
	  public ResponseEntity<ApiResponse> reSendOtp(@Valid @RequestBody UserDto userDto) throws AddressException, MessagingException{
		  String Otp=this.tokenServiceClass.generateSaveToken(userDto.getEmail(), 2);
		  String otpSubject=AppConstants.OTP_SUBJECT;
			String otpText="Hii, \n"+AppConstants.OTP_TEXT+"\n"+AppConstants.OTP_PARAGRAPH_STRING+Otp;
			this.sender.sendEmail(userDto.getEmail(), AppConstants.FROM, otpSubject, otpText);
			return new ResponseEntity<ApiResponse>(new ApiResponse("OTP Send...",true),HttpStatus.OK);
	  }
	
	

}
