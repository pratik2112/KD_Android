package kdgs.kdgroup.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressResponse {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public boolean status;

    public static class Data {
        @SerializedName("created_at")
        public String createdAt;
        @SerializedName("type")
        public String type;
        @SerializedName("address")
        public String address;
        @SerializedName("pincode")
        public String pincode;
        @SerializedName("u_id")
        public String uId;
        @SerializedName("ua_id")
        public String uaId;
    }
}