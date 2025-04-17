package com.aj.blog.blogappapis.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */

	String resourceName;
	String fieldName;
	long fieldValue;
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s Not found with %s: %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
