package com.southwicksstorage.southwicksstorage.models.attribute;

import java.io.Serializable;

public class Modal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8791720361175520945L;
	private String showModal;
	private String modalType;
	private String modalTitle;
	private String modalMessage;
	
	/**
	 * Create modal for easier use in OOP workflow
	 * @param showModal Whether or not to show modal; This will usually be true but it specifies it in javascript
	 * @param modalType The type of modal (info, success, warning, and error)
	 * @param modalTitle The title of the modal
	 * @param modalMessage The message for the modal
	 */
	public Modal(String showModal, String modalType, String modalTitle, String modalMessage) {
		this.setShowModal(showModal);
		this.setModalType(modalType);
		this.setModalTitle(modalTitle);
		this.setModalMessage(modalMessage);
	}

	/**
	 * @return the showModal
	 */
	public String getShowModal() {
		return showModal;
	}

	/**
	 * @param showModal the showModal to set
	 */
	public void setShowModal(String showModal) {
		this.showModal = showModal;
	}

	/**
	 * @return the modalType
	 */
	public String getModalType() {
		return modalType;
	}

	/**
	 * @param modalType the modalType to set
	 */
	public void setModalType(String modalType) {
		this.modalType = modalType;
	}

	/**
	 * @return the modalTitle
	 */
	public String getModalTitle() {
		return modalTitle;
	}

	/**
	 * @param modalTitle the modalTitle to set
	 */
	public void setModalTitle(String modalTitle) {
		this.modalTitle = modalTitle;
	}

	/**
	 * @return the modalMessage
	 */
	public String getModalMessage() {
		return modalMessage;
	}

	/**
	 * @param modalMessage the modalMessage to set
	 */
	public void setModalMessage(String modalMessage) {
		this.modalMessage = modalMessage;
	}
	
	

}
