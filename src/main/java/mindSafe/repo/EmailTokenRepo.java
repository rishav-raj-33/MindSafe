package mindSafe.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import mindSafe.entity.EmailToken;
import mindSafe.entity.User;

public interface EmailTokenRepo extends JpaRepository<EmailToken, Integer> {
	
Optional<EmailToken> findByToken(String token);
	
	List<EmailToken> findByUser(User user);

}
