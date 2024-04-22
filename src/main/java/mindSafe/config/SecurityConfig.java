package mindSafe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import mindSafe.security.JwtEntryPoint;
import mindSafe.security.JwtFilter;



@Configuration
public class SecurityConfig {
	
	  @Autowired
	    private JwtEntryPoint point;
	    @Autowired
	    private JwtFilter jwtFilter; 
	    
		@Bean
	     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http.csrf(csrf -> csrf.disable())
	                .cors(cors->cors.disable())
	                .authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/**")
	                .permitAll().anyRequest().authenticated())
	                .exceptionHandling(ex-> ex.authenticationEntryPoint(point))
	                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	        
	        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	                
	        return http.build();
	    }

}
