package org.hua.filesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fileService")
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileCommDAO fileCommDAO;

	@Override
	public FileParam upload(FileParam upload) {
		return fileCommDAO.upload(upload);
	}

	@Override
	public FileParam download(FileParam download) {
		return fileCommDAO.download(download);
	}

}
