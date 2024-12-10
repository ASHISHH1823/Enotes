package com.ashish.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ashish.handler.GenericResponse;

public class CommonUtils {
	public static ResponseEntity<?> createBuildResponse(Object date,HttpStatus status){
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("Succes")
				.message("Succes")
				.data(date)
				.build();
				return response.create();
	}
	public static ResponseEntity<?> createBuilrdResponseMessage(String message,HttpStatus status){
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("Succes")
				.message(message)
				.build();
				return response.create();
	}
	public static ResponseEntity<?> createErrorResponse(Object data,HttpStatus status){
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message("failed")
				.data(data)
				.build();
		return response.create();
	}
	public static ResponseEntity<?> createErrorResponseMessage(String message,HttpStatus status){
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message(message)
				.build();
		return response.create();
	}
	
	public static String getContentType(String originalFileName) {
		String extension = FilenameUtils.getExtension(originalFileName);
		
		switch (extension) {
		case "pdf": 
			return "application/pdf";
		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
		case "txt":
			return "text/plan";
		case "png":
			return "image/png";
		case "jpeg":
			return "image/jpeg";
		
		default:
			return "application/octet-stream";
		}
		
	}
}