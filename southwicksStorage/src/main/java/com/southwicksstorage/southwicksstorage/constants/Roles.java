package com.southwicksstorage.southwicksstorage.constants;

public enum Roles {

	MANAGER("MANAGER"),
	TEAM_MEMBER("TEAM_MEMBER"),
	MANAGER_ROLE("ROLE_MANAGER"),
	TEAM_MEMBER_ROLE("ROLE_TEAM_MEMBER");
	
	private String role;
	
	private Roles(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
}
