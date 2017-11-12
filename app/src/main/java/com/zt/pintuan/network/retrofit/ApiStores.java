package com.zt.pintuan.network.retrofit;


import com.zt.pintuan.model.Response;
import com.zt.pintuan.pt.ac_ptList.m.QiniuToKen;
import com.zt.pintuan.pt.ac_ptbb.m.PinDan_pt;
import com.zt.pintuan.pt.ac_ptbb.m.PinTuan_pt;
import com.zt.pintuan.pt.ac_staffSend.m.Activity_pt;
import com.zt.pintuan.pt.ac_staffSend.m.StaffSend_pt;
import com.zt.pintuan.pt.ac_staffSend.m.Staff_pt;
import com.zt.pintuan.pt.m.LoginData_pt;
import com.zt.pintuan.pt.m.PtReport_pt;

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

    //获取七牛上传token
    @GET("api/common/uploadTokens")
    Observable<Response<QiniuToKen>> getQiniuToken(@Header("authorization") String authorization);

    //拼单兑换记录
    @GET("api/pt/ptOrderDetail/exChangeRecord")
    Observable<Response<ArrayList<PinDan_pt>>> getExchangeList(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //拼单明细
    @GET("api/pt/ptOrderDetail/{detailId}")
    Observable<Response<PinDan_pt>> getPinDanDetail(@Header("authorization") String authorization, @Path("detailId") String detailId);

    //拼单兑换
    @POST("api/pt/ptOrderDetail/exchange")
    Observable<Response> PinDanExchange(@Header("authorization") String authorization, @Query("detailId") String detailId, @Query("redeemCode") String redeemCode);


}