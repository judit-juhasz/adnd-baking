package name.juhasz.judit.udacity.delicious.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable{

    @SerializedName("quantity")
    private double mQuantity;

    @SerializedName("measure")
    private String mMeasure;

    @SerializedName("ingredient")
    private String mName;

    public Ingredient(double quantity, String measure, String ingredientName) {
        this.mQuantity = quantity;
        this.mMeasure = measure;
        this.mName = ingredientName;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public void setQuantity(double quantity) {
        this.mQuantity = quantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String measure) {
        this.mMeasure = measure;
    }

    public String getIngredientName() {
        return mName;
    }

    public void setIngredientName(String ingredientName) {
        this.mName = ingredientName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(mQuantity);
        parcel.writeString(mMeasure);
        parcel.writeString(mName);
    }

    public Ingredient(Parcel in) {
        mQuantity = in.readDouble();
        mMeasure = in.readString();
        mName = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
