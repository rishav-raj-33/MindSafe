package mindSafe;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.RandomSaltGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import mindSafe.helpers.EmaillSender;
import mindSafe.helpers.OtpGenerator;



@SpringBootApplication
@EnableTransactionManagement
public class MindSafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindSafeApplication.class, args);
	}
	
	
	@Bean
	ModelMapper mapper() {
		return new ModelMapper();
	}
	
	
	@Bean
	StringEncryptor encryptor() {
		 StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	        encryptor.setPassword("F423B124789012A3C5DE6FGH1J2KL3MN4O5P6QR7ST8UVW9X0YZ");
	        encryptor.setSaltGenerator(new RandomSaltGenerator());
	        return encryptor;
	}
	
	@Bean
	OtpGenerator generator() {
		return new OtpGenerator();
	}
	
	   @Bean
	     AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
	        return builder.getAuthenticationManager();
	    }
	   
	   @Bean
	   EmaillSender sendEmail() {
		   return new EmaillSender();
	   }
	   @Bean
	   PasswordEncoder encoder() {
		   return new BCryptPasswordEncoder();
	   }

}
