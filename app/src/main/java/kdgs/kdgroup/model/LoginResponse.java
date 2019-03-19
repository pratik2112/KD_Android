package kdgs.kdgroup.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("data")
    public Data data;
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public boolean status;

    public static class Data {
        @SerializedName("u_modified")
        public String uModified;
        @SerializedName("u_created")
        public String uCreated;
        @SerializedName("is_login")
        public String isLogin;
        @SerializedName("device_id")
        public String deviceId;
        @SerializedName("device_type")
        public String deviceType;
        @SerializedName("device_token")
        public String deviceToken;
        @SerializedName("u_status")
        public String uStatus;
        @SerializedName("u_login_time")
        public String uLoginTime;
        @SerializedName("u_password")
        public String uPassword;
        @SerializedName("u_email")
        public String uEmail;
        @SerializedName("u_name")
        public String uName;
        @SerializedName("u_mobile")
        public String uMobile;
        @SerializedName("u_image")
        public String uImage;
        @SerializedName("u_dob")
        public String uDob;
        @SerializedName("u_gander")
        public String uGander;
        @SerializedName("u_lastname")
        public String uLastname;
        @SerializedName("u_firstname")
        public String uFirstname;
        @SerializedName("u_id")
        public String uId;
    }
}