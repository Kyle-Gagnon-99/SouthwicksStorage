/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models.attribute;

/**
 * @author kyle
 *
 */
public class HelpTableObject {

	private String annotationNumber;
	private String annotationName;
	private String content;
	
	public HelpTableObject(String annotationNumber, String annotationName, String content) {
		this.annotationNumber = annotationNumber;
		this.annotationName = annotationName;
		this.content = content;
	}

	public String getAnnotationNumber() {
		return annotationNumber;
	}

	public void setAnnotationNumber(String annotationNumber) {
		this.annotationNumber = annotationNumber;
	}

	public String getAnnotationName() {
		return annotationName;
	}

	public void setAnnotationName(String annotationName) {
		this.annotationName = annotationName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
