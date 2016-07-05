package com.jersey.restful.usermanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	// To get all users
	public List<User> getAllUser() {
		List<User> userList = null;
		try {
			File file = new File("Users.dat");
			if (!file.exists()) {
				User user = new User(1, "Alex", "Teacher");
				User user2 = new User(2, "Alex2", "Teacher2");
				userList = new ArrayList<User>();
				userList.add(user);
				userList.add(user2);
				saveUserList(userList);
			} else {
				FileInputStream fileInputStream = new FileInputStream(file);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				userList = (List<User>) objectInputStream.readObject();
				objectInputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return userList;
	}

	// To get user
	public User getUser(int id) {
		List<User> users = getAllUser();
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	// add user
	public int addUser(User pUser) {
		List<User> userList = getAllUser();
		boolean userExists = false;
		for (User user : userList) {
			if (user.getId() == pUser.getId()) {
				userExists = true;
				break;
			}
		}
		if (!userExists) {
			userList.add(pUser);
			saveUserList(userList);
		}
		return 0;
	}

	// update user
	public int updateUser(User pUser) {
		List<User> userList = getAllUser();

		for (User user : userList) {
			if (user.getId() == pUser.getId()) {
				int index = userList.indexOf(user);
				userList.set(index, pUser);
				saveUserList(userList);
				return 1;
			}
		}
		return 0;
	}

	// delete user
	public int deleteUser(int id) {
		List<User> userList = getAllUser();

		for (User user : userList) {
			if (user.getId() == id) {
				int index = userList.indexOf(user);
				userList.remove(index);
				saveUserList(userList);
				return 1;
			}
		}
		return 0;
	}

	// save user list into file
	private void saveUserList(List<User> userList) {
		try {
			File file = new File("Users.dat");
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(userList);
			objectOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}