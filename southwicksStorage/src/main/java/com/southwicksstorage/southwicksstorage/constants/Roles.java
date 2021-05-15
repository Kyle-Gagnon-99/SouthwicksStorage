package com.southwicksstorage.southwicksstorage.constants;

public enum Roles {

	MANAGER("MANAGER") {
		@Override
		public String toString() { return "Manager"; }
	},
	TEAM_MEMBER("TEAM_MEMBER") {
		@Override
		public String toString() { return "Team Member"; }
	},
	MANAGER_ROLE("ROLE_MANAGER") {
		@Override
		public String toString() { return "Role Manager"; }
	},
	TEAM_MEMBER_ROLE("ROLE_TEAM_MEMBER") {
		@Override
		public String toString() { return "Role Team Member"; }
	};
	
	private String role;
	
	private Roles(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
}
