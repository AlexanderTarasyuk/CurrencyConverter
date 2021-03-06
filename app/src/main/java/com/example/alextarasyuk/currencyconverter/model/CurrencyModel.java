package com.example.alextarasyuk.currencyconverter.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CurrencyModel implements Parcelable {

    private String base;
    private String date;
    private List<Map<String, Double>> rates;

    public CurrencyModel() {
    }

    public CurrencyModel(String base, String date, List<Map<String, Double>> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    protected CurrencyModel(Parcel in) {
        base = in.readString();
        date = in.readString();
    }

    public static final Creator<CurrencyModel> CREATOR = new Creator<CurrencyModel>() {
        @Override
        public CurrencyModel createFromParcel(Parcel in) {
            return new CurrencyModel(in);
        }

        @Override
        public CurrencyModel[] newArray(int size) {
            return new CurrencyModel[size];
        }
    };

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Map<String, Double>> getRates() {
        return rates;
    }

    public void setRates(List<Map<String, Double>> rates) {
        this.rates = rates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(base);
        dest.writeString(date);
    }
}
