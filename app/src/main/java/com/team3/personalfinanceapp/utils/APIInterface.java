package com.team3.personalfinanceapp.utils;

import com.team3.personalfinanceapp.model.Transaction;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("transaction/{userId}")
    Call<List<Transaction>> getAllTransactions(@Path("userId") int userId, @Header("Authorization") String token);

    @GET("transaction/{userId}?")
    Call<List<Transaction>> getTransactionsByMonth(@Path("userId") int userId, @Query("month") Integer month, @Header("Authorization") String token);

    @GET("transaction")
    Call<Transaction> getTransactionById(@Query("transactionId") long transactionId, @Header("Authorization") String token);

    @POST("transaction/{userId}")
    Call<Transaction> addTransaction(@Path("userId") int userId, @Body Transaction transaction, @Header("Authorization") String token);

    @PUT("transaction/{userId}")
    Call<Transaction> editTransaction(@Path("userId") int userId, @Body Transaction transaction, @Header("Authorization") String token);

    @DELETE("transaction")
    Call<Long> deleteTransactionById(@Query("transactionId") long transactionId, @Header("Authorization") String token);

    @GET("forecast/{userId}")
    Call<Map<String, Float>> getSpendingForecastById(@Path("userId") int userId, @Header("Authorization") String token);


}
