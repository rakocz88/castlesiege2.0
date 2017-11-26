package com.pilaf.cs.tests.helper;

public interface RestEndpoints {
	
	public static String LOGIN_ENDPOINT = "http://localhost:%d/auth";
	public static String REFRESH_ENDPOINT = "http://localhost:%d/refresh";
	public static String GET_USER_ENDPOINT = "http://localhost:%d/users/%s";
	public static String GET_ALL_USERS_ENDPOINT = "http://localhost:%d/users/all";
	public static String REGISTRATION_ENDPOINT= "http://localhost:%d/registration";
	public static String ACTIVATION_ENDPOINT= "http://localhost:%d/activate/%s";

}
