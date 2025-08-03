package com.thryve.pgfinder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    private String pgId;             // FK to PG (optional: @ManyToOne)
    private String imageUrl;
    private String sharing;
    private boolean isAc;
    private String description;
    private double price;

    public String getPgId() {
        return pgId;
    }

    public void setPgId(String pgId) {
        this.pgId = pgId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSharing() {
        return sharing;
    }

    public void setSharing(String sharing) {
        this.sharing = sharing;
    }

    public boolean isAc() {
        return isAc;
    }

    public void setAc(boolean ac) {
        isAc = ac;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
