package com.kiduyu.patriciproject.householdtrackingsystem.Constants;

import android.content.Context;

import com.kiduyu.patriciproject.householdtrackingsystem.SharedPref.SharedPrefManager;

public class Constants {

    private static final String TAG = "Constants";

    private static final String ROOT_URL = "https://the-bookhub.co.ke/houseItems/";

    public static final String URL_REGISTER = ROOT_URL + "registerUser.php";
    public static final String URL_LOGIN = ROOT_URL + "userLogin.php";
    public static final String URL_ADD_CONSUMER= ROOT_URL + "addconsumer.php";
    public static final String CONSUMERBLE_API= ROOT_URL + "consumableApi.php";
    public static final String PENDING_SCHEDULE_API =  ROOT_URL + "pendingscheduleApi.php";
    public static final String DELETE_SCHEDULE =  ROOT_URL + "deletecheduleApi.php";
    public static final String APPROVE_SCHEDULE =  ROOT_URL + "approvecheduleApi.php";
    public static final String DENY_SCHEDULE =  ROOT_URL + "denycheduleApi.php";
    public static final String UPDATE_SCHEDULE =  ROOT_URL + "updatecheduleApi.php";
    public static final String COMPLETED_SCHEDULE_API =  ROOT_URL + "completescheduleApi.php";
    public static final String URL_ADD_SCHEDULE= ROOT_URL + "scheduleApi.php";
    public static final String URL_PROFILE= ROOT_URL + "getprofile.php?name_id=";
}
