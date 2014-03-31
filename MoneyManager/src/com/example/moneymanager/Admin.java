package com.example.moneymanager;
/**
 * @author asomani7
 */
public final class Admin {
    /**
     * Not called.
     */
    private Admin() {
    }
    /**
     * Method to check if the user is an admin user.
     * @param username username that was entered
     * @param password password that was entered
     * @return true if it is an admin user
     */
    public static boolean isAdminUser(final String username,
            final String password) {
        if (username.equals("admin") && password.equals("pass123")) {
            return true;
        }
        return false;
    }
}
