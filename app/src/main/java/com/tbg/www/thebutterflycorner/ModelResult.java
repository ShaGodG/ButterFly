package com.tbg.www.thebutterflycorner;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class ModelResult implements Parcelable {

    String imagePath;
    String status;
    int image;

    public ModelResult(String imagePath, String status,int image) {
        this.imagePath = imagePath;
        this.status = status;
        this.image=image;
    }

    protected ModelResult(Parcel in) {
        imagePath = in.readString();
        status = in.readString();
        image = in.readInt();
    }

    public static final Creator<ModelResult> CREATOR = new Creator<ModelResult>() {
        @Override
        public ModelResult createFromParcel(Parcel in) {
            return new ModelResult(in);
        }

        @Override
        public ModelResult[] newArray(int size) {
            return new ModelResult[size];
        }
    };

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
        dest.writeString(status);
        dest.writeInt(image);
    }
}
