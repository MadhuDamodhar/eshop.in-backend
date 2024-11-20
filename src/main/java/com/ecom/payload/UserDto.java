package com.ecom.payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserDto {
	
	
	private int userId;
	
	@NotEmpty
	@Size(min=4 ,max=50 ,message="name must should be 4 and 50")
	@Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$",message = "Invalid username !!")
	private String name;
	
	@Email(message="Enter Vaild Email")
	private String email;
	@NotEmpty
	@Size(min=6,max=10,message="password should be 6 to 10")
	private String password;
	private String address;
	private String about;
	private String gender;
	private Date   date; 
    private boolean  active;
    private Set<RoleDto> roles=new HashSet<>();
	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	@NotBlank
	private String phone;

    public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date=   date;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	

}
