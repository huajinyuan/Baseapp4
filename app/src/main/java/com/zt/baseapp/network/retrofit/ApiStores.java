package com.zt.baseapp.network.retrofit;


import com.zt.baseapp.model.Response;
import com.zt.baseapp.pt.ac_ptbb.m.PinDan_pt;
import com.zt.baseapp.pt.ac_ptbb.m.PinTuan_pt;
import com.zt.baseapp.pt.m.LoginData_pt;
import com.zt.baseapp.pt.m.PtReport_pt;
import com.zt.baseapp.pt.ac_staffSend.m.Activity_pt;
import com.zt.baseapp.pt.ac_staffSend.m.StaffSend_pt;
import com.zt.baseapp.pt.ac_staffSend.m.Staff_pt;

import java.util.ArrayList;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiStores {
    //    String API_SERVER_URL = "http://www.vip177.cn:8080/webcast/";
//    String URL_LOGIN = API_SERVER_URL + "api/member/login";
//    String URL_BASE = "https://www.baby25.cn/jeesite";

    @POST("api/common/tokens")
    Observable<Response<LoginData_pt>> doLogin_pt(@Query("name") String name, @Query("password") String password);

    @DELETE("api/common/tokens")
    Observable<Response<LoginData_pt>> doLogout(@Query("authorization") String authorization);

    //拼团汇总报表
    @GET("api/pt/report/total/report")
    Observable<Response<PtReport_pt>> getReport_pt(@Header("authorization") String authorization, @Query("status") int status);

    //拼团员工发送
    @GET("api/pt/ptActivityStaff/list")
    Observable<Response<ArrayList<StaffSend_pt>>> getStaffSends_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //员工活动列表 状态 0全部 1可用 2暂停 3作废
    @GET("api/pt/ptActivities/list")
    Observable<Response<ArrayList<Activity_pt>>> getStaffDetail_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status, @Query("userId") int userId);

    //活动列表 状态 0全部 1可用 2暂停 3作废
    @GET("api/pt/ptActivities/list")
    Observable<Response<ArrayList<Activity_pt>>> getAc_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);

    //员工列表
    @GET("api/pt/ptActivityStaff/staff/list")
    Observable<Response<ArrayList<Staff_pt>>> getStaffSelect_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //提交员工发送
    @POST("api/pt/ptActivityStaff/submit")
    Observable<Response> saveStaffSend(@Header("authorization") String authorization, @Query("activityIds") String activityIds, @Query("userId") int userId);

    //活动停用/作废 2停用 3作废
    @POST("api/pt/ptActivities/state")
    Observable<Response> changeAcStatus(@Header("authorization") String authorization, @Query("actId") String actId, @Query("status") int status);

    //活动报表-0全部 1本日 2本月 3本年
    @GET("api/pt/ptActivities/report")
    Observable<Response<ArrayList<Activity_pt>>> getAcReport_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);

    //员工排行-0全部 1本日 2本月 3本年
    @GET("api/pt/ptActivities/report/staffRanking")
    Observable<Response<ArrayList<Staff_pt>>> getStaffRanking_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);
    @GET("api/pt/ptActivities/report/staffRanking")
    Observable<Response<ArrayList<Staff_pt>>> getStaffRanking_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status, @Query("actId") String actId);

    //拼团记录 0全部 1待成团 2已成团 3拼团失败
    @GET("api/pt/ptAppGroupOrder/list")
    Observable<Response<ArrayList<PinTuan_pt>>> getPtList_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);

    //拼单记录 0全部 1待成团 2已成团 3拼团失败 4退款
    @GET("api/pt/ptAppGroupOrder/order/list")
    Observable<Response<ArrayList<PinDan_pt>>> getPdList_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);

    @GET("/api/pt/ptOrderDetail/exChangeRecord")
    Observable<Response<LoginData_pt>> exChangeRecord(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("authorization")String authorization);

    @POST("/api/pt/ptOrderDetail/exchange")
    Observable<Response<LoginData_pt>> exchange(@Query("authorization") String authorization, @Query("detailId") int detailId, @Query("redeemCode")String redeemCode);

    @GET("/api/pt/ptOrderDetail/{detailId}")
    Observable<Response<LoginData_pt>> ptOrderDetail(@Path("detailId") String detailId, @Query("authorization") String authorization, @Query("detailId")String id);

    @GET("/api/common/store/list")
    Observable<Response<LoginData_pt>> list(@Query("authorization") String authorization);

    @POST("/api/common/store/save")
    Observable<Response<LoginData_pt>> save(@Query("authorization") String authorization, @Query("storeName")String storeName, @Query("storeMobile")String storeMobile, @Query("storeAddress")String storeAddress);


    @GET("/api/pt/ptGroupOrder/group")
    Observable<Response<LoginData_pt>> group(@Query("authorization") String authorization, @Query("id")String id);

    @GET("/api/pt/ptGroupOrder/list")
    Observable<Response<LoginData_pt>> ptGroupOrderList(@Query("authorization") String authorization, @Query("pageNo")int pageNo, @Query("pageSize")int pageSize, @Query("status")int status);

    @GET("/api/pt/ptGroupOrder/refund")
    Observable<Response<LoginData_pt>> refund(@Query("authorization") String authorization, @Query("id")String id);

    @GET("/api/pt/ptActivities/info")
    Observable<Response<LoginData_pt>> info(@Query("authorization") String authorization, @Query("actId")String actId, @Query("inviteId")String inviteId);

    @GET("/api/pt/ptActivities/list")
    Observable<Response<LoginData_pt>> ptActivitiesList(@Query("authorization") String authorization, @Query("pageNo")String pageNo, @Query("pageSize")int pageSize, @Query("status")int status, @Query("userId")int userId);

    @GET("/api/pt/ptActivities/report")
    Observable<Response<LoginData_pt>> ptActivitiesReport(@Query("authorization") String authorization, @Query("pageNo")String pageNo, @Query("pageSize")int pageSize, @Query("status")int status, @Query("userId")int userId);

    @GET("/api/pt/ptActivities/report/staffRanking")
    Observable<Response<LoginData_pt>> staffRanking(@Query("authorization") String authorization, @Query("pageNo")String pageNo, @Query("pageSize")int pageSize, @Query("status")int status, @Query("actId")int actId);

    @POST("/api/pt/ptActivities/save")
    Observable<Response<LoginData_pt>> ptActivitiesSave(@Query("authorization") String authorization, @Query("goodName")String goodName, @Query("originalPrice")double originalPrice, @Query("price")double price, @Query("count")int  count, @Query("videoUrl")String videoUrl, @Query("goodImgUrl")String goodImgUrl, @Query("activityName")String activityName, @Query("activityImgUrl")String activityImgUrl, @Query("beginTime")String beginTime, @Query("endTime")String endTime, @Query("num")int num, @Query("saleNum")int  saleNum, @Query("storeId")String storeId, @Query("saleRemarks")String saleRemarks);

    @GET("/api/pt/ptActivities/state")
    Observable<Response<LoginData_pt>> ptActivitiesState(@Query("authorization") String authorization, @Query("status")int status, @Query("actId")int actId);

    @POST("/api/pt/ptActivities/update")
    Observable<Response<LoginData_pt>> ptActivitiesUpdate(@Query("authorization") String authorization, @Query("goodName")String goodName, @Query("originalPrice")double originalPrice, @Query("price")double price, @Query("count")int  count, @Query("videoUrl")String videoUrl, @Query("goodImgUrl")String goodImgUrl, @Query("activityName")String activityName, @Query("activityImgUrl")String activityImgUrl, @Query("beginTime")String beginTime, @Query("endTime")String endTime, @Query("num")int num, @Query("saleNum")int  saleNum, @Query("storeId")String storeId, @Query("saleRemarks")String saleRemarks);

    @GET("/api/pt/ptAppGroupOrder/detail")
    Observable<Response<LoginData_pt>> ptAppGroupOrderDetail(@Query("authorization") String authorization, @Query("orderNumber")int orderNumber);

    @GET("/api/pt/ptAppGroupOrder/list")
    Observable<Response<LoginData_pt>> ptAppGroupOrderList(@Query("authorization") String authorization, @Query("status")int status, @Query("pageNo")int pageNo, @Query("pageSize")int pageSize);

    @GET("//api/pt/ptAppGroupOrder/order/list")
    Observable<Response<LoginData_pt>> ptAppGroupOrderOrderList(@Query("authorization") String authorization, @Query("pageNo")int pageNo, @Query("pageSize")int pageSize);

//    @GET("/api/pt/ptActivities/update")
//    Observable<Response<LoginData_pt>> ptActivitiesUpdate(@Query("authorization") String authorization,@Query("status")int status,@Query("actId")int actId);
//
//    @GET("/api/pt/ptActivities/update")
//    Observable<Response<LoginData_pt>> ptActivitiesUpdate(@Query("authorization") String authorization,@Query("status")int status,@Query("actId")int actId);
//
//    @GET("/api/pt/ptActivities/update")
//    Observable<Response<LoginData_pt>> ptActivitiesUpdate(@Query("authorization") String authorization,@Query("status")int status,@Query("actId")int actId);

}
