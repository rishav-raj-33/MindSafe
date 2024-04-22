package mindSafe.helpers;

import java.security.SecureRandom;

public class OtpGenerator {
	
	public String genrateOtp() {
		
		SecureRandom random=new SecureRandom();
		int randomNumber = random.nextInt(10000);
	    String otp = String.format("%04d", randomNumber);
		
		return otp;
	}
	
	

}
