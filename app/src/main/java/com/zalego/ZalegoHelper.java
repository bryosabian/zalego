package com.zalego;

import com.zalego.users.ZalegoUser;

/**
 * Created by edith on 05/04/2018.
 */
public class ZalegoHelper {

    public static ZalegoUser user;

    public static boolean userIsLoggedIn=false;

    public static ZalegoUser getUser() {
        return user;
    }

    public static void setUser(ZalegoUser user) {
        ZalegoHelper.user = user;
    }

    public static boolean isUserIsLoggedIn() {
        return userIsLoggedIn;
    }

    public static void setUserIsLoggedIn(boolean userIsLoggedIn) {
        ZalegoHelper.userIsLoggedIn = userIsLoggedIn;
    }
}
