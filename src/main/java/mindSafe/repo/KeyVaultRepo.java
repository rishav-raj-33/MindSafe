package mindSafe.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mindSafe.entity.KeyVault;
import mindSafe.entity.User;

public interface KeyVaultRepo extends JpaRepository<KeyVault, Integer> {
	
	Page<KeyVault> findByUser(User user,Pageable pageable);

}
