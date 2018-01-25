package com.dto;

import java.util.List;

public class Restaurant {
	
		 private int id;
		 private String govtRegistrationId;
		 private String name;
		 private String password;
		 private String logoName;
		 private List<Branch> branchesList;
		
		 
		 public List<Branch> getBranchesList() {
			return branchesList;
		}


		public String getGovtRegistrationId() {
			return govtRegistrationId;
		}


		public void setGovtRegistrationId(String govtRegistrationId) {
			this.govtRegistrationId = govtRegistrationId;
		}


		public void setBranchesList(List<Branch> branchesList) {
			this.branchesList = branchesList;
		}


		public Restaurant() {
			System.out.println("Rest object is created");
		}


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


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public String getLogoName() {
			return logoName;
		}


		public void setLogoName(String logoName) {
			this.logoName = logoName;
		}


		@Override
		public String toString() {
			return "Restaurant [id=" + id + ", govtRegistrationId=" + govtRegistrationId + ", name=" + name + ", password="
					+ password + ", logoName=" + logoName + "]";
		}
		 
		 
	}
