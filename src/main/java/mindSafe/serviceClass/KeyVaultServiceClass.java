package mindSafe.serviceClass;

import java.util.List;

import org.jasypt.encryption.StringEncryptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mindSafe.entity.KeyVault;
import mindSafe.entity.User;
import mindSafe.exception.ResourceNotFound;
import mindSafe.helpers.AppConstants;
import mindSafe.helpers.KeyPageResponse;
import mindSafe.helpers.KeyVaultDto;
import mindSafe.repo.KeyVaultRepo;
import mindSafe.repo.UserRepo;
import mindSafe.service.KeyVaultService;

@Service
public class KeyVaultServiceClass implements KeyVaultService {
	
	@Autowired
	private KeyVaultRepo repo;
	@Autowired
	private StringEncryptor encryptor;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepo userRepo;

	@Override
	@Transactional
	public KeyVaultDto addKey(KeyVaultDto key,Integer id) {
		User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFound("user","id" , id));
		KeyVault savedKey=this.mapper.map(key, KeyVault.class);
		savedKey.setPassword(this.encryptor.encrypt(key.getPassword()));
		savedKey.setUser(user);
		KeyVaultDto keyVaultDto=this.mapper.map(this.repo.save(savedKey), KeyVaultDto.class);
		return keyVaultDto;
	}

	@Override
	@Transactional
	public KeyVaultDto updateKey(KeyVaultDto key, Integer id) {
		KeyVault getKey=this.repo.findById(id).orElseThrow(()->new ResourceNotFound("key","id" , id));
		getKey.setNotes(key.getNotes());
		getKey.setUserName(key.getUserName());
		getKey.setPassword(this.encryptor.encrypt(key.getPassword()));
		return this.mapper.map(this.repo.save(getKey),KeyVaultDto.class);
	}

	@Override
	public KeyVaultDto viewKey(Integer id) {
		KeyVault keyVault=this.repo.findById(id).orElseThrow(()->new ResourceNotFound("key","id" , id));
		keyVault.setPassword(this.encryptor.decrypt(keyVault.getPassword()));
		return this.mapper.map(keyVault, KeyVaultDto.class);
	}

	@Override
	@Transactional
	public void deleteKey(Integer id) {

		KeyVault key=this.repo.findById(id).orElseThrow(()->new ResourceNotFound("key","id" , id));
		this.repo.delete(key);
	}

	@Override
	public KeyPageResponse getKeyByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort=null;
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFound("User","id" , userId));
		if(sortDir.equalsIgnoreCase(AppConstants.SORT_DIR)) {
			sort=Sort.by(sortBy).ascending();
		}else {
			sort=Sort.by(sortBy).descending();
		}
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<KeyVault> pagePost=this.repo.findByUser(user, pageable);
		List<KeyVault> listKeyVaults=pagePost.getContent();
		List<KeyVaultDto> listOfkeyDtos=listKeyVaults.stream().map((obj)->this.mapper.map(obj, KeyVaultDto.class)).toList();
		listOfkeyDtos.forEach(
				(obj)-> obj.setPassword(this.encryptor.decrypt(obj.getPassword())));
		KeyPageResponse response=new KeyPageResponse();
		response.setContent(listOfkeyDtos);
		response.setPageNumber(pagePost.getNumber());
		response.setPageSize(pagePost.getSize());
		response.setTotalPages(pagePost.getTotalPages());
		response.setTotalElemnets(pagePost.getTotalElements());
		response.setLastPage(pagePost.isLast());
		return response;
	}

}
