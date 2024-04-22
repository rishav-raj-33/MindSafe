package mindSafe.service;

import mindSafe.entity.User;

public interface EmailTokenService {
	
	 public String generateSaveToken(String email,int time);
     public boolean ConfirmToken(String token);
     public void deleteAllConfirmationToken(User user);
     
    

}
