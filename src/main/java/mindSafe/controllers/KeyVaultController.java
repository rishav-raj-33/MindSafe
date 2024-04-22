package mindSafe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import mindSafe.helpers.ApiResponse;
import mindSafe.helpers.AppConstants;
import mindSafe.helpers.KeyPageResponse;
import mindSafe.helpers.KeyVaultDto;
import mindSafe.serviceClass.KeyVaultServiceClass;

@RestController
@RequestMapping("/api/key")
public class KeyVaultController {
	@Autowired
	private KeyVaultServiceClass serviceClass;

	
	@PostMapping("/user/{id}")
	public ResponseEntity<KeyVaultDto> createKey(@RequestBody KeyVaultDto keyVaultDto,@PathVariable int id){
		KeyVaultDto key=this.serviceClass.addKey(keyVaultDto,id);
		return new ResponseEntity<KeyVaultDto>(key,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<KeyVaultDto> updateKey(@PathVariable int id,@RequestBody KeyVaultDto key){
		KeyVaultDto updateKeyVaultDto=this.serviceClass.updateKey(key, id);
		return new ResponseEntity<KeyVaultDto>(updateKeyVaultDto,HttpStatus.ACCEPTED);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteKey(@PathVariable int id){
		this.serviceClass.deleteKey(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Key Deleted..", true),HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<KeyVaultDto> viewKey(@PathVariable int id){
		KeyVaultDto key=this.serviceClass.viewKey(id);
		return new ResponseEntity<KeyVaultDto>(key,HttpStatus.FOUND);
	}
	
	
	@GetMapping("/user/{id}")
	public ResponseEntity<KeyPageResponse> getUserKey(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)int pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir,@PathVariable int id){
		KeyPageResponse response=this.serviceClass.getKeyByUser(id, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<KeyPageResponse>(response,HttpStatus.OK);
	}
				
			
	
	
}
