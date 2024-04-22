package mindSafe.serviceClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mindSafe.service.FileService;
@Service
public class FileServiceClass implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		String name=file.getOriginalFilename();
		String generatedFileId=UUID.randomUUID().toString();
		
		String generatedFileName=generatedFileId.concat(name.substring(name.lastIndexOf(".")));
		String filePath=path+File.separator+generatedFileName;
		File fileFolder=new File(path);
		if(!fileFolder.exists()) {
			fileFolder.mkdir();
		}
		Files.copy(file.getInputStream(),Paths.get(filePath));
		return generatedFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath=path+File.separator+fileName;
		InputStream file=new FileInputStream(fullPath);
		return file;
	}

	@Override
	public boolean deleteFile(String imageName, String path) {
		if(imageName.equals("default.png")) {
			return false;
		}
		String fullPath=path+File.separator+imageName;
		File file=new File(fullPath);
		if(file.exists()) {
			file.delete();
			return true;
		}
	return false;
	}

}
