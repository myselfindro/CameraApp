package com.example.cameraapp.Allurl;

public class Allurl {


    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static String KEY_PASSWORD = null;
    public static String USER_NAME = "USER_NAME";
    public static String baseUrl;
    public static String Studiolighturl;
    public static String NumberOfLightsByProduct;
    public static String PhoneAperture;
    public static String DSLRExposureSettings;
    public static String CameraExposureSettingsIncrementalValues;
    public static String token;
    public static String VersionDetails;
    public static String backgroundimage;
    public static String AppVersion;
    public static String AppContent;







    static {

        baseUrl = "http://dev1.ivantechnology.in/cameraapp/api/mystudiophotoasst/v1";
        Studiolighturl = baseUrl + "/number-of-lights-by-product";
        NumberOfLightsByProduct = baseUrl+"/number-of-lights-by-product";
        PhoneAperture = baseUrl+"/phone-aperture";
        DSLRExposureSettings = baseUrl+"/get-dslr-exposure-settings";
        CameraExposureSettingsIncrementalValues = baseUrl+"/get-camera-exposure-settings";
        token = baseUrl+"/device-token-add";
        VersionDetails = baseUrl+"/get-app-version";
        backgroundimage = baseUrl+"/get-app-background-image";
        AppVersion = baseUrl+"/get-app-version";
        AppContent = baseUrl+"/get-app-content";







        USER_NAME = "user_name";
        KEY_PASSWORD = "password";
    }
}
