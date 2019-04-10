package kdgs.kdgroup.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComplaintResponse {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public boolean status;

    public static class Data {
        @SerializedName("complainimage")
        public List<Complainimage> complainimage;
        @SerializedName("u_email")
        public String uEmail;
        @SerializedName("u_mobile")
        public String uMobile;
        @SerializedName("u_lastname")
        public String uLastname;
        @SerializedName("u_firstname")
        public String uFirstname;
        @SerializedName("status")
        public String status;
        @SerializedName("ername")
        public String ername;
        @SerializedName("servicetype")
        public String servicetype;
        @SerializedName("complaintype")
        public String complaintype;
        @SerializedName("username")
        public String username;
        @SerializedName("ua_id")
        public String uaId;
        @SerializedName("modified_at")
        public String modifiedAt;
        @SerializedName("created_at")
        public String createdAt;
        @SerializedName("uc_barcode")
        public String ucBarcode;
        @SerializedName("uc_desc")
        public String ucDesc;
        @SerializedName("uc_title")
        public String ucTitle;
        @SerializedName("stat_id")
        public String statId;
        @SerializedName("er_id")
        public String erId;
        @SerializedName("s_id")
        public String sId;
        @SerializedName("c_id")
        public String cId;
        @SerializedName("uc_no")
        public String ucNo;
        @SerializedName("u_id")
        public String uId;
        @SerializedName("uc_id")
        public String ucId;
    }

    public static class Complainimage {
        @SerializedName("url")
        public String url;
    }
}