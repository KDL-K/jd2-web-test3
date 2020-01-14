package by.htp.ts.dao.impl;

import by.htp.ts.bean.User;
import by.htp.ts.bean.UserPlusLogPass;
import by.htp.ts.controller.Controller;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

public class UserDAOImpl implements UserDAO{
	public User authorization(String login, String password) throws DAOException{
		
		final String PST_CHECK_PARAM = "SELECT login, password FROM user WHERE login=? AND password=?";
		final String PST_GET_USER_PARAM = "SELECT user.id, email, name, surname, role_title, age FROM ((user_details JOIN user ON user_details.id=user.user_details_id)"
				+ "JOIN roles ON user.roles_id=roles.id) WHERE user.login=?";
		
		Connection connection = Controller.getConnectionPool().takeConnection();
		
		PreparedStatement pstCheck = null;
		PreparedStatement pstGetUser = null;
		ResultSet rsCheck = null;
		ResultSet rsGetUser = null;
		try {
			pstCheck=connection.prepareStatement(PST_CHECK_PARAM);
			
			pstCheck.setString(1, login);
			pstCheck.setString(2, password);
			rsCheck=pstCheck.executeQuery();
			
			if(rsCheck.isBeforeFirst()) {
				
				pstGetUser = connection.prepareStatement(PST_GET_USER_PARAM);
				pstGetUser.setString(1, login);
				rsGetUser = pstGetUser.executeQuery();
				rsGetUser.next();
				
				User user = new User();
				user.setId(rsGetUser.getInt(UserParameter.ID));
				user.setEmail(rsGetUser.getString(UserParameter.EMAIL));
				user.setName(rsGetUser.getString(UserParameter.NAME));
				user.setSurname(rsGetUser.getString(UserParameter.SURNAME));
				user.setRole(rsGetUser.getString(UserParameter.ROLE));
				user.setAge(rsGetUser.getInt(UserParameter.AGE));
				return user;
			}
			
		}catch(SQLException e) {
			throw new DAOException(e);
		}finally {
			if(rsCheck != null) {
				try {
					rsCheck.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			if(rsGetUser != null) {
				try {
					rsGetUser.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			if(pstCheck != null) {
				try {
					pstCheck.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			if(pstGetUser != null) {
				try {
					pstGetUser.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					//log
				}
			}
			
		}
        return null;

	}
	
	public boolean registration(UserPlusLogPass userPLP) throws DAOException{
		Connection connection=Controller.getConnectionPool().takeConnection();
		final String PST_CHECK_LOGIN = "SELECT login FROM user WHERE login=?";
		final String PST_IN_USER_DETAILS = "INSERT INTO user_details(name,surname,sex,age) VALUE(?,?,?,?)";
		final String PST_IN_USER = "INSERT INTO user(login,password,email,user_details_id,roles_id)"+
		                   " VALUE(?,?,?,LAST_INSERT_ID(),(SELECT id FROM roles WHERE role_title=?))";
		PreparedStatement pstCheck = null;
		PreparedStatement pstInUser = null;
		PreparedStatement pstInUserDetails = null;
		ResultSet rsCheck = null;
		ReentrantLock dbLock = new ReentrantLock();
		dbLock.lock();
		boolean isRegistered = false;
		try {
			pstCheck=connection.prepareStatement(PST_CHECK_LOGIN);
			pstCheck.setString(1, userPLP.getLogin());
			rsCheck=pstCheck.executeQuery();
			if(!rsCheck.isBeforeFirst()){
				isRegistered = true;
				connection.setAutoCommit(false);
				
				pstInUserDetails = connection.prepareStatement(PST_IN_USER_DETAILS);
				pstInUserDetails.setString(1, userPLP.getUser().getName());
				
				pstInUserDetails.setString(2, userPLP.getUser().getSurname());
				pstInUserDetails.setString(3, userPLP.getUser().getSex());
				pstInUserDetails.setInt(4, userPLP.getUser().getAge());
				pstInUserDetails.executeUpdate();
				
				pstInUser = connection.prepareStatement(PST_IN_USER);
				pstInUser.setString(1, userPLP.getLogin());
				pstInUser.setString(2, userPLP.getPassword());
				pstInUser.setString(3, userPLP.getUser().getEmail());
				
				if(userPLP.getUser().getRole().equals(UserParameter.ADMIN_ROLE)) {
					pstInUser.setString(4,"admin");
				}
				if(userPLP.getUser().getRole().equals(UserParameter.USER_ROLE)) {
					pstInUser.setString(4, "user");
				}
				
				pstInUser.executeUpdate();
				connection.commit();
			}
			
			
		}catch(SQLException e) { 
			throw new DAOException(e);
		}finally {
			try {
				if(isRegistered) {
					connection.rollback();
				}
			} catch (SQLException e) {
				//log
				throw new DAOException(e);
			}
			
			if(rsCheck!=null) {
				try {
					rsCheck.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(pstCheck!=null) {
				try {
					pstCheck.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			if(pstInUser!=null) {
				try {
					pstInUser.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			if(pstInUserDetails!=null) {
				try {
					pstInUserDetails.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					//log
				}
			}
		}
		dbLock.unlock();
		return isRegistered;
		
	}

}
