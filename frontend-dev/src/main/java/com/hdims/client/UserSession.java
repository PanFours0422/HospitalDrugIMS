package com.hdims.client;

public class UserSession {
    private static String loggedInUserId;
    private static String loggedInUserRole;

    public static String getLoggedInUserId() {
        return loggedInUserId;
    }

    public static void setLoggedInUserId(String userId) {
        loggedInUserId = userId;
    }

    public static String getLoggedInUserRole() {
        return loggedInUserRole;
    }

    public static void setLoggedInUserRole(String userRole) {
        loggedInUserRole = userRole;
    }
}
