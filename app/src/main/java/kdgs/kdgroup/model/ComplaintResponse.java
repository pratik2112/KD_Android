package kdgs.kdgroup.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComplaintResponse {
    @SerializedName("complaints")
    public List<ComplaintEntity> complaints;
    @SerializedName("status")
    public boolean status;

    public static class ComplaintEntity {
        @SerializedName("order_id")
        public String order_id;
    }
}