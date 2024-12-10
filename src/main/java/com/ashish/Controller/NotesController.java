package com.ashish.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ashish.Entity.FileDetails;
import com.ashish.Service.NoteService;
import com.ashish.dto.NoteDto;
import com.ashish.util.CommonUtils;

@RestController
@RequestMapping("/api/Notes")
public class NotesController {
	
	@Autowired
	private NoteService NoteService;
	
	@PostMapping("/")
	public ResponseEntity<?> SaveNotes(@RequestParam String notes,
			@RequestParam (required = false) MultipartFile file) throws Exception{
		Boolean saveNotes = NoteService.SaveNotes(notes,file);
		if(saveNotes) {
			return CommonUtils.createBuilrdResponseMessage("Saved Sucessfully", HttpStatus.CREATED);
		}
		return CommonUtils.createErrorResponseMessage("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadnotes(@PathVariable Integer id) throws Exception{
		FileDetails fileDetails=NoteService.getfiledetails(id);
		byte[] data=NoteService.downloadfile(fileDetails);
		
		HttpHeaders header= new HttpHeaders();
		String contentType = CommonUtils.getContentType(fileDetails.getOriginalFileName());
		header.setContentType(MediaType.parseMediaType(contentType));
		header.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(header).body(data);
	}
	

	@GetMapping("/")
	public ResponseEntity<?> GetAllNotes(){
		List<NoteDto> allNotes = NoteService.getAllNotes();
		if(CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtils.createBuildResponse(allNotes, HttpStatus.OK);
	}
}
