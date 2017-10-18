package com.zt.baseapp.network.retrofit;


import com.zt.baseapp.model.Response;
import com.zt.baseapp.pt.model.Logd;
import com.zt.baseapp.pt.model.PtReport;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiStores {
    //    String API_SERVER_URL = "http://www.vip177.cn:8080/webcast/";
//    String URL_LOGIN = API_SERVER_URL + "api/member/login";
//    String URL_BASE = "https://www.baby25.cn/jeesite";

    @POST("api/common/tokens")
    Observable<Response<Logd>> doLogin(@Query("name") String name, @Query("password") String password);

    @DELETE("api/common/tokens")
    Observable<Response<Logd>> doLogout(@Query("authorization") String authorization);

    @GET("/api/pt/report/total/report")
    Observable<Response<PtReport>> report(@Query("status") int name, @Query("authorization") String password);

    @GET("/api/pt/ptOrderDetail/exChangeRecord")
    Observable<Response<Logd>> exChangeRecord(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize,@Query("authorization")String authorization);

    @POST("/api/pt/ptOrderDetail/exchange")
    Observable<Response<Logd>> exchange(@Query("authorization") String authorization, @Query("detailId") int detailId,@Query("redeemCode")String redeemCode);

    @GET("/api/pt/ptOrderDetail/{detailId}")
    Observable<Response<Logd>> ptOrderDetail(@Path("detailId") String detailId, @Query("authorization") String authorization,@Query("detailId")String id);

    @GET("/api/common/store/list")
    Observable<Response<Logd>> list(@Query("authorization") String authorization);

    @POST("/api/common/store/save")
    Observable<Response<Logd>> save(@Query("authorization") String authorization,@Query("storeName")String storeName,@Query("storeMobile")String storeMobile,@Query("storeAddress")String storeAddress);


    @GET("/api/pt/ptGroupOrder/group")
    Observable<Response<Logd>> group(@Query("authorization") String authorization,@Query("id")String id);

    @GET("/api/pt/ptGroupOrder/list")
    Observable<Response<Logd>> ptGroupOrderList(@Query("authorization") String authorization,@Query("pageNo")int pageNo,@Query("pageSize")int pageSize,@Query("status")int status);

    @GET("/api/pt/ptGroupOrder/refund")
    Observable<Response<Logd>> refund(@Query("authorization") String authorization,@Query("id")String id);

    @GET("/api/pt/ptActivities/info")
    Observable<Response<Logd>> info(@Query("authorization") String authorization,@Query("actId")String actId,@Query("inviteId")String inviteId);

    @GET("/api/pt/ptActivities/list")
    Observable<Response<Logd>> ptActivitiesList(@Query("authorization") String authorization,@Query("pageNo")String pageNo,@Query("pageSize")int pageSize,@Query("status")int status,@Query("userId")int userId);

    @GET("/api/pt/ptActivities/report")
    Observable<Response<Logd>> ptActivitiesReport(@Query("authorization") String authorization,@Query("pageNo")String pageNo,@Query("pageSize")int pageSize,@Query("status")int status,@Query("userId")int userId);

    @GET("/api/pt/ptActivities/report/staffRanking")
    Observable<Response<Logd>> staffRanking(@Query("authorization") String authorization,@Query("pageNo")String pageNo,@Query("pageSize")int pageSize,@Query("status")int status,@Query("actId")int actId);

    @POST("/api/pt/ptActivities/save")
    Observable<Response<Logd>> ptActivitiesSave(@Query("authorization") String authorization,@Query("goodName")String goodName,@Query("originalPrice")double originalPrice,@Query("price")double price,@Query("count")int  count,@Query("videoUrl")String videoUrl,@Query("goodImgUrl")String goodImgUrl,@Query("activityName")String activityName,@Query("activityImgUrl")String activityImgUrl,@Query("beginTime")String beginTime,@Query("endTime")String endTime,@Query("num")int num,@Query("saleNum")int  saleNum,@Query("storeId")String storeId,@Query("saleRemarks")String saleRemarks);

    @GET("/api/pt/ptActivities/state")
    Observable<Response<Logd>> ptActivitiesState(@Query("authorization") String authorization,@Query("status")int status,@Query("actId")int actId);

    @POST("/api/pt/ptActivities/update")
    Observable<Response<Logd>> ptActivitiesUpdate(@Query("authorization") String authorization,@Query("goodName")String goodName,@Query("originalPrice")double originalPrice,@Query("price")double price,@Query("count")int  count,@Query("videoUrl")String videoUrl,@Query("goodImgUrl")String goodImgUrl,@Query("activityName")String activityName,@Query("activityImgUrl")String activityImgUrl,@Query("beginTime")String beginTime,@Query("endTime")String endTime,@Query("num")int num,@Query("saleNum")int  saleNum,@Query("storeId")String storeId,@Query("saleRemarks")String saleRemarks);

    @GET("/api/pt/ptAppGroupOrder/detail")
    Observable<Response<Logd>> ptAppGroupOrderDetail(@Query("authorization") String authorization,@Query("orderNumber")int orderNumber);

    @GET("/api/pt/ptAppGroupOrder/list")
    Observable<Response<Logd>> ptAppGroupOrderList(@Query("authorization") String authorization,@Query("status")int status,@Query("pageNo")int pageNo,@Query("pageSize")int pageSize);

    @GET("//api/pt/ptAppGroupOrder/order/list")
    Observable<Response<Logd>> ptAppGroupOrderOrderList(@Query("authorization") String authorization,@Query("pageNo")int pageNo,@Query("pageSize")int pageSize);

//    @GET("/api/pt/ptActivities/update")
//    Observable<Response<Logd>> ptActivitiesUpdate(@Query("authorization") String authorization,@Query("status")int status,@Query("actId")int actId);
//
//    @GET("/api/pt/ptActivities/update")
//    Observable<Response<Logd>> ptActivitiesUpdate(@Query("authorization") String authorization,@Query("status")int status,@Query("actId")int actId);
//
//    @GET("/api/pt/ptActivities/update")
//    Observable<Response<Logd>> ptActivitiesUpdate(@Query("authorization") String authorization,@Query("status")int status,@Query("actId")int actId);

}
