package com.service;

import java.io.File;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.dao.RestaurantDAO;
import com.dao.UserDAO;
import com.dto.Feedback;
import com.dto.Recipe;
import com.dto.Restaurant;
import com.dto.User;

public class UrbanSpoonService {
	private static final String IMAGESLOCATION = "D:\\urbanspoon\\UrbanSpoon\\WebContent\\images\\";
	private static int cuisionId; 
	public UrbanSpoonService() {
	}

	public List<Restaurant> getRestaurants() {

		List<Restaurant> restaurantsList = RestaurantDAO.getRestaurants();
		

		return restaurantsList;
	}
	
	

	public void addUser(HttpServletRequest request, HttpServletResponse response) {
		User user = new User();
		user.setName(request.getParameter("uName"));
		user.setGender(request.getParameter("gender"));
		user.setEmail(request.getParameter("eMail"));
		user.setMobileNo(Long.parseLong(request.getParameter("mNo")));
		user.setPassword(request.getParameter("pwd"));
		user.setDate(Date.valueOf(request.getParameter("dt")));
		
		UserDAO.get(user);
	}

	public void addRestaurant(List<FileItem> fileItemsList) {
		Restaurant rest = new Restaurant();
		String uploadPath =  IMAGESLOCATION+"restuarent";
				
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			
			if (fileItemsList != null && fileItemsList.size() > 0) {
	
				for (FileItem item : fileItemsList) {
					if(item.getFieldName().equals("rName")){
						rest.setName(item.getString());
					}
					if(item.getFieldName().equals("regId")){
						rest.setGovtRegistrationId(item.getString());
					}
					if(item.getFieldName().equals("pwd")){
						rest.setPassword(item.getString());
					}
				}
					rest = RestaurantDAO.insert(rest);
					int rId = rest.getId();
					String imgName=rId+".jpg";
					new RestaurantDAO().updateLogoAddress(imgName, rId);
				for (FileItem item : fileItemsList) {
					if (item.getFieldName().equals("image")) {
						String filePath = uploadPath + File.separator +imgName;
						File storeFile = new File(filePath);
						item.write(storeFile);
						System.out.println("file uploaded successfully");
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("error in upload");
		}

	}

	public String addRecipe(List<FileItem> fileItemsList) {
		Recipe recipe = new Recipe();
		String msg="";
		String fileUpload = IMAGESLOCATION+"recipes";
		File fileDir = new File(fileUpload);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		try {
			for (FileItem item : fileItemsList) {
				if(item.getFieldName().equals("recipeName")){
					recipe.setName(item.getString());	
				}if(item.getFieldName().equals("desc")){
					recipe.setDescription(item.getString());	
				}if(item.getFieldName().equals("recipeType")){
					recipe.setIsVeg(Integer.parseInt(item.getString()));
				}
			}
			recipe = RecipeDAO.insert(recipe);
			String imgName = recipe.getId()+".jpg";
			boolean isImgInserted = RecipeDAO.updateImage(recipe.getId(),imgName);
			if(isImgInserted){
				for (FileItem item : fileItemsList) {
					if(item.getFieldName().equals("recipeImage")){
						String filePath = fileUpload+File.separator+imgName;
						File storeFile = new File(filePath);
						item.write(storeFile);
						System.out.println("img stored in folder");
					}
				}
			}else{
				System.out.println("iamge not stored");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "recipe added successfully";
	}

	public User getUser(HttpServletRequest request, HttpServletResponse response) {
		int userId= Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("password");
		User user= UserDAO.login(userId,password);
		return user;
	}

	public Restaurant getRestaurant(HttpServletRequest request, HttpServletResponse response) {
		int restaurantId= Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("password");
		Restaurant restaurant = RestaurantDAO.login(restaurantId,password);
		return restaurant;
	}

	public List<Feedback> getFeedback() {
		User user = new User();
		List<Feedback> feedList = FeedbackDAO.getFeedback();
		for (Feedback list : feedList) {
			user = UserDAO.getUser(list.getUser().getId());
			list.setUser(user);
		}
		return feedList;
	}
}