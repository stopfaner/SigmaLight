package com.stopfan.sigmalight.core.net;

import com.stopfan.sigmalight.core.models.User;
import com.stopfan.sigmalight.core.net.response.RequestResult;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Denys on 11/7/2015.
 */
public interface LoginService {

    String ENDPOINT = "http://sigma-light.com/";

    @POST("/api/")
    Observable<RequestResult> loginAction(@Body Request<User> request);

    @POST("/api/")
    Observable<RequestResult> registerAction(@Body Request<User> userRequest);

    @POST("/api/")
    Observable<RequestResult> changePasswordAction(@Body Request<User> userRequest);

    @POST("/api/")
    Observable<RequestResult> changeProfileAction(@Body Request<User> userRequest);

}
