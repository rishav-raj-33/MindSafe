package mindSafe.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;
import mindSafe.helpers.ApiResponse;
import mindSafe.helpers.UserDto;
import mindSafe.serviceClass.FileServiceClass;
import mindSafe.serviceClass.UserServiceClass;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
	@Autowired
	private UserServiceClass serviceClass;
	
	@Value("${project.image}")
	private String path;
	@Autowired
	private FileServiceClass fileService;
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable int id,@RequestBody UserDto user){
		UserDto userDto=this.serviceClass.updateUser(user, id);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> disableUser(@PathVariable int id){
	this.serviceClass.disableUser(id,path);
	return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted", true),HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/dp/upload/{id}")
	public ResponseEntity<ApiResponse> uploadPostImage(@RequestParam MultipartFile image,@PathVariable int id) throws IOException{
		UserDto user=this.serviceClass.findUser(id);
		String fileName=this.fileService.uploadImage(path, image);
		user.setProfilePhoto(fileName);
		this.serviceClass.updateUser(user, id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("File Name: "+image.getOriginalFilename(), true),HttpStatus.ACCEPTED);
	}
	@GetMapping(value = "/dp/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	void downloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException {
        InputStream resource=this.fileService.getResource(path, imageName);		
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	
}
