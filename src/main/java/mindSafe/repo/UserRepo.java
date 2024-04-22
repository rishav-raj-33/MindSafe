package mindSafe.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import mindSafe.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);

}
