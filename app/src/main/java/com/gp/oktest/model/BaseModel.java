package com.gp.oktest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseModel implements Parcelable {

    public String TAG = getClass().getSimpleName();

    public BaseModel() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(TAG);
    }



    public static final Parcelable.Creator<BaseModel> CREATOR = new Parcelable.Creator<BaseModel>() {

        @Override
        public BaseModel createFromParcel(Parcel source) {
            return new BaseModel(source);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };

    public BaseModel(Parcel source) {
        TAG = source.readString();
    }
}
