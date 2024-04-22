package mindSafe.service;



import mindSafe.helpers.KeyPageResponse;
import mindSafe.helpers.KeyVaultDto;

public interface KeyVaultService {
	
	KeyVaultDto addKey(KeyVaultDto key,Integer id);
	KeyVaultDto updateKey(KeyVaultDto key,Integer id);
	KeyVaultDto viewKey(Integer id);
	void deleteKey(Integer id);
	KeyPageResponse getKeyByUser(int userId,int pageNumber,int pageSize,String sortBy,String sortDir);
	
	
}
