package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dto.Recipe;

public class RecipeDAO {
	@Autowired
	private DataSource datasource;
	private JdbcTemplate jdbcTemplet;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource dataSource) {
		this.datasource = dataSource;
		this.jdbcTemplet = new JdbcTemplate(this.datasource);
	}
	
	
	public List<Recipe> getRecipes(int branch_id, int cuisine_id){
		
			final List<Recipe> list=new ArrayList<Recipe>();
			jdbcTemplet.query("select distinct r.*,s.price,s.image_name from recipe r, serve s where r.recipe_id=s.recipe_id and branch_id = ? and cuisine_id = ?",new Object[]{branch_id,cuisine_id},new ResultSetExtractor<List<Recipe>>() {
				public List<Recipe> extractData(ResultSet rs) throws SQLException {
					if (rs.next()) {
						Recipe recipe = new Recipe();
						recipe.setId(rs.getInt(1));
						recipe.setName(rs.getString(2));
						recipe.setDescription(rs.getString(3));
						recipe.setIsVeg(rs.getInt(5));
						recipe.setPrice(rs.getFloat(6));
						recipe.setImageName(rs.getString(7));
						list.add(recipe);
					}
					return list;
				}
			});
			return list;
	}

	public void insert(Recipe recipe) {
		int n = jdbcTemplet.update("insert into recipe(name,description,is_Veg) values(?,?,?)", new Object[] {recipe.getName(),recipe.getDescription(),recipe.getIsVeg()});

		if (n > 0) {
			System.out.println("record inserted");

		} else {
			System.out.println("some problem");
		}
	}

	public boolean updateImage(int id, String imgName) {
		
		return false;
	}	 
}