package kdgs.kdgroup.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactusResponse {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("message")
    public String message;
    @SerializedName("Status")
    public boolean status;

    public static class Data {
        @SerializedName("longi")
        public double longi;
        @SerializedName("lat")
        public double lat;
        @SerializedName("email")
        public String email;
        @SerializedName("__v")
        public int V;
        @SerializedName("Creationdate")
        public String creationdate;
        @SerializedName("address2")
        public String address2;
        @SerializedName("address1")
        public String address1;
        @SerializedName("Phone5")
        public String phone5;
        @SerializedName("Phone4")
        public String phone4;
        @SerializedName("Phone3")
        public String phone3;
        @SerializedName("Phone2")
        public String phone2;
        @SerializedName("Phone1")
        public String phone1;
        @SerializedName("Company_Name")
        public String companyName;
        @SerializedName("Name")
        public String name;
        @SerializedName("_id")
        public String Id;
    }
}