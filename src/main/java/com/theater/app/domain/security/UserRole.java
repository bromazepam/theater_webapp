package com.theater.app.domain.security;


import com.theater.app.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

//@Entity
//@Table(name="user_role")
//@Document(collection = "user_role")
public class UserRole {

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private String userRoleId;

//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="user_id")
	@DBRef
	private User user;

//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="role_id")
	@DBRef
	private Role role;

	public UserRole(){}

	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}


	public String getUserRoleId() {
		return userRoleId;
	}


	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


}
