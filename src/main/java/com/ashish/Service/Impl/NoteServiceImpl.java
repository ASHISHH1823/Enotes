package com.ashish.Service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ashish.Entity.FileDetails;
import com.ashish.Entity.Notes;
import com.ashish.Repository.CategoryRepository;
import com.ashish.Repository.NoteRepository;
import com.ashish.Repository.fileDetailsrepo;
import com.ashish.Service.NoteService;
import com.ashish.dto.NoteDto;
import com.ashish.dto.NoteDto.CategoryDto;
import com.ashish.dto.NoteResponse;
import com.ashish.exceptionHandler.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private NoteRepository noterepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryrepo;
	
	@Value("${file.upload.path}")
	private String uploadpath;
	
	@Autowired
	private fileDetailsrepo filedetailsrepo;

	@Override
	public Boolean SaveNotes(String notes,MultipartFile file) throws Exception {
		
	ObjectMapper ob = new ObjectMapper();
	NoteDto notedto = ob.readValue(notes, NoteDto.class);
		
		checkCategoryExist(notedto.getCategory());
		
		Notes notemap = mapper.map(notedto, Notes.class);
		
		FileDetails filedtls=saveFiledetails(file);
		if(!ObjectUtils.isEmpty(filedtls)) {
			notemap.setFileDetails(filedtls);
		}else {
			notemap.setFileDetails(null);
		}
		
		Notes save = noterepo.save(notemap);
		if(!ObjectUtils.isEmpty(save)) {
			return true;
		}
		return false;
	}

	private FileDetails saveFiledetails(MultipartFile file) throws IOException {
		if(!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
			
			
			String originalFilename = file.getOriginalFilename();
			
			
			String rndstring = UUID.randomUUID().toString();
			String extension = FilenameUtils.getExtension(originalFilename);
			String uploadFileName=rndstring+"."+extension;
			
			
			List<String> extensionAllow = Arrays.asList("jpg","pdf");
			if(!extensionAllow.contains(extension)) {
			throw new IllegalArgumentException("Invalid file format! upload only pdf,jpg");
			}
			File savefile = new File(uploadpath);
			if(!savefile.exists()) {
				savefile.mkdir();
			}
			Path storepath=Paths.get(uploadpath,uploadFileName);
			
			
			long uploaded = Files.copy(file.getInputStream(), storepath);
			if(uploaded != 0) {
				FileDetails filedetails= new FileDetails();
				filedetails.setOriginalFileName(originalFilename);
				filedetails.setDisplayFileName(getDisplayName(originalFilename));
				filedetails.setUploadFileName(uploadFileName);
				filedetails.setFileSize(file.getSize());
				filedetails.setPath(storepath.toString());
				
				FileDetails save = filedetailsrepo.save(filedetails);
				return save;
				
			}
			
		}
		return null;
	}

	private String getDisplayName(String originalFilename) {
		String extension = FilenameUtils.getExtension(originalFilename);
		String fullname = FilenameUtils.removeExtension(originalFilename);
		if(fullname.length()>8) {
			fullname=fullname.substring(0, 7);
		}
		fullname=fullname+"."+extension;
		return fullname;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		categoryrepo.findById(category.getId()).orElseThrow(() ->new ResourceNotFoundException("Category Id invalid")); 
		
	}

	@Override
	public List<NoteDto> getAllNotes() {
		return noterepo.findAll().stream()
				.map(note -> mapper.map(note, NoteDto.class)).toList();
	}

	@Override
	public FileDetails getfiledetails(Integer id) throws Exception {
		 FileDetails filedetails = filedetailsrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("File not available"));
		 return filedetails ;
	}

	@Override
	public byte[] downloadfile(FileDetails fileDetails) throws Exception {
	InputStream io=new FileInputStream(fileDetails.getPath());
		return StreamUtils.copyToByteArray(io);
	}

	@Override
	public NoteResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Notes> pagenotes=noterepo.findByCreatedBy(userId,pageable);
		
		List<NoteDto> notesDto=pagenotes.get().map(n -> mapper.map(n,NoteDto.class)).toList();
		
		NoteResponse notes=NoteResponse.builder()
		.notes(notesDto)
		.pageNo(pagenotes.getNumber())
		.pageSize(pagenotes.getSize())
		.totalPages(pagenotes.getTotalPages())
		.totalElements(pagenotes.getTotalElements())
		.isFirst(pagenotes.isFirst())
		.isLast(pagenotes.isLast())
		.build();
		
		return notes;
	}

}
