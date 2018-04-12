package com.example.alextarasyuk.currencyconverter.api;



public class CurrencyConverterServiceImpl extends CurrencyConverterServiceBase {

    private CurrencyConverterService mCurrencyConverterService;

    public CurrencyConverterServiceImpl() {
        super();
        this.mCurrencyConverterService = this.mRetrofit.create(CurrencyConverterService.class);
    }


    public CurrencyConverterService getCurrencyConverterService() {
        return mCurrencyConverterService;
    }
}
