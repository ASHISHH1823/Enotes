package com.ashish.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ashish.Entity.FileDetails;
import com.ashish.dto.NoteDto;

public interface NoteService {
	
	public Boolean SaveNotes(String notes,MultipartFile file) throws Exception;
	
	public List<NoteDto> getAllNotes();

	public FileDetails getfiledetails(Integer id) throws Exception;

	public byte[] downloadfile(FileDetails fileDetails) throws Exception;

}