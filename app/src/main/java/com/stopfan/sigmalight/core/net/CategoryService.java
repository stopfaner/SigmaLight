package com.stopfan.sigmalight.core.net;

import com.stopfan.sigmalight.core.models.NavItem;
import com.stopfan.sigmalight.core.models.Shop;
import com.stopfan.sigmalight.core.models.SubCategory;
import com.stopfan.sigmalight.core.net.response.CategoryResult;
import com.stopfan.sigmalight.core.net.response.RequestResult;
import com.stopfan.sigmalight.core.net.response.SubResult;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Denys on 11/7/2015.
 */
public interface CategoryService {
    String ENDPOINT = "http://sigma-light.com/";

    @POST("/api/")
    Observable<CategoryResult> getMenu(@Body Request<NavItem> itemRequest);

    @POST("/api/")
    Observable<SubResult> getSubCategories(@Body Request<SubCategory> subCategoryRequest);

    @POST("/api/")
    Observable<RequestResult> getShops(@Body Request<Shop> shopRequest);

    @POST("/api/")
    Observable<RequestResult> getShopList(@Body Request<Shop> shopRequest);

    @POST("/api/")
    Observable<RequestResult> getShopInfo(@Body Request<Shop> shopRequest);

    @POST("/api/")
    Observable<RequestResult> getTRCInfo(@Body Request<Shop> shopRequest);

}
