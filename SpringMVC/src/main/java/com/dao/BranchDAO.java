package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dto.Branch;

import java.sql.*;
public class BranchDAO {
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

	public List<Branch> getBranches(int restaurantId){
		final List<Branch> branches = new ArrayList<Branch>();
			jdbcTemplet.query("select * from branch where restaurant_id = ?",new Object[]{restaurantId},new ResultSetExtractor<List<Branch>>(){
				public List<Branch> extractData(ResultSet rs) throws SQLException {
				if(rs.next()){
					System.out.println("records found in branch");
					do {
						Branch b = new Branch();
						b.setId(rs.getInt(1));
						b.setLocation(rs.getString(2));
						b.setCity(rs.getString(3));
						b.setState(rs.getString(4));
						b.setCountry(rs.getString(5));
						b.setPostalCode(rs.getInt(6));
						b.setEmail(rs.getString(7));
						b.setMobileNo(rs.getLong(8));
						
						branches.add(b);
					} while (rs.next());
				}else{
					System.out.println("No records found in branch");
				}
				
				return branches;
			}
			});
			return branches;
		}
		


	public List<String> getBranchImages(int id) {
		final List<String> list = new ArrayList<String>();
		jdbcTemplet.query("select * from branch_image where branch_id = ?",new Object[]{id} ,new ResultSetExtractor<List<String>>() {
			public List<String> extractData(ResultSet rs) throws SQLException {
				if (rs.next()) {
					list.add(rs.getString(2));
				}
				return list;
			}
		});
		return list;
	}
	 
	
}