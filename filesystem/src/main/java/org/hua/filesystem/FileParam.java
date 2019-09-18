package org.hua.filesystem;

import java.util.Arrays;

public class FileParam {

	private String fullname;
	private String encodetype;
	private String base64file;
	private byte[] fileByte;
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEncodetype() {
		return encodetype;
	}
	public void setEncodetype(String encodetype) {
		this.encodetype = encodetype;
	}
	public String getBase64file() {
		return base64file;
	}
	public void setBase64file(String base64file) {
		this.base64file = base64file;
	}

	public byte[] getFileByte() {
		return fileByte;
	}

	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}

	@Override
	public String toString() {
		return "FileParam{" +
				"fullname='" + fullname + '\'' +
				", encodetype='" + encodetype + '\'' +
				", base64file='" + base64file + '\'' +
				", fileByte=" + Arrays.toString(fileByte) +
				'}';
	}
}
