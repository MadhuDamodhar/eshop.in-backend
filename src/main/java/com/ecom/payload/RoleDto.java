package com.ecom.payload;

import javax.persistence.Id;

public class RoleDto {
	 
		private int id;
		private String name;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public RoleDto(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		public RoleDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		

}
