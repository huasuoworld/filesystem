package org.hua.filesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fileService")
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileCommDAO hdfsFileDAO;

	@Override
	public FileParam upload(FileParam upload) {
		return hdfsFileDAO.upload(upload);
	}

	@Override
	public FileParam download(FileParam download) {
		return hdfsFileDAO.download(download);
	}

}
