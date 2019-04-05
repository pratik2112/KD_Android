package kdgs.kdgroup.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerResponse {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public boolean status;

    public static class Data {
        @SerializedName("created_at")
        public String createdAt;
        @SerializedName("b_name")
        public String bName;
        @SerializedName("b_id")
        public String bId;
    }
}