package com.ashish.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NoteDto {
	
    private Integer id;
	
	private String title;
	
	private String description;
	
	private CategoryDto category;
	
	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;
	
	private fileDto fileDetails;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class fileDto{
		private Integer id;
		private String OriginalFileName;
		private String DisplayFileName;
		
	}
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class CategoryDto{
		private Integer id;
		private String name;
	}

}
