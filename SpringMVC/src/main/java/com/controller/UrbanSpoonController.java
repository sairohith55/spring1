package com.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.dto.Branch;
import com.dto.Cuisine;
import com.dto.Feedback;
import com.dto.Recipe;
import com.dto.Restaurant;
import com.dto.User;
import com.service.UrbanSpoonService;

public class UrbanSpoonController implements Controller{
	private static final long serialVersionUID = 1L;
	String msg = "";
	private UrbanSpoonService service;
	public UrbanSpoonService getService() {
		return service;
	}

	public void setService(UrbanSpoonService service) {
		this.service = service;
	}

	public UrbanSpoonController() {
		// TODO Auto-generated constructor stub
		System.out.println("***UrbanSpoonController object is created..");
	}

	protected void m1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	 
		List<Restaurant> rest= service.getRestaurants();
		List<Cuisine> cuisine_list=new ArrayList<Cuisine>();
		List<Recipe> r = new ArrayList<Recipe>();
		for (Restaurant restaurant : rest) {
			List<Branch> branchesList = BranchDAO.getBranches(restaurant.getId());
			restaurant.setBranchesList(branchesList);
			for (Branch branch : branchesList) {
				List<String> imagesList = BranchDAO.getBranchImages(branch.getId());
				cuisine_list = CuisineDAO.getCuisinesList();
				branch.setImagesList(imagesList);
				branch.setCuisinesList(cuisine_list);
				for(Cuisine c: cuisine_list){
					r =RecipeDAO.getRecipes(branch.getId(), c.getId());
					c.setRecipesList(r);	
				}
			}	
		}
		for (Restaurant r1 : rest) {
			System.out.println(r1);
		}
		
		request.setAttribute("restaurantsList",rest);
		request.getRequestDispatcher("home.jsp").forward(request, response);	
		
	}

	protected void m2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> fileItemsList=upload.parseRequest(request);
				for (FileItem item : fileItemsList) {
					if(item.getFieldName().equals("action")){
						if(item.getString().equals("restRegForm")){
							service.addRestaurant(fileItemsList);
						}if(item.getString().equals("addRecipe")){
							String msg = service.addRecipe(fileItemsList);
							request.setAttribute("msg", msg);
							request.getRequestDispatcher("show.jsp").forward(request, response);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("error in controller");
			}
		
		}else if(action.equals("userRegForm")){
			service.addUser(request, response);
		}else if(action.equals("login")){
			
			List<Feedback> FeedbackList = service.getFeedback();
			request.setAttribute("feedList",FeedbackList );
			List<Restaurant> rest=service.getRestaurants();
			request.setAttribute("restaurantsList",rest);
			
			
			String type = request.getParameter("person");
			if(type.equals("user")){
				User user = service.getUser(request,response);
				if(user.getName()!=null){
					request.setAttribute("userName", user.getName());
					request.getRequestDispatcher("user.jsp").include(request, response);
				}else{
					request.setAttribute("msg", "Invalid user id or password");
					request.getRequestDispatcher("show.jsp").include(request, response);
				}
			}else if(type.equals("restaurant")){
				Restaurant restaurant = service.getRestaurant(request,response);
				if(restaurant.getName()!=null){
					request.setAttribute("currentRest", restaurant);
					request.getRequestDispatcher("restaurant.jsp").include(request, response);
				}else{
					request.setAttribute("msg", "Invalid restaurant id or password");
					request.getRequestDispatcher("show.jsp").include(request, response);
				}
			}
		}
	}

	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {

		
		return null;
	}

}