package com.example.choujiang.network.retrofit;


import com.example.choujiang.cj.ac_acSetting.m.ActivityDetail_cj;
import com.example.choujiang.cj.ac_acSetting.m.Award;
import com.example.choujiang.cj.ac_acSetting.m.QiniuToKen;
import com.example.choujiang.cj.ac_cjbb.m.CjHistory;
import com.example.choujiang.cj.ac_cjbb.m.TichengDetail;
import com.example.choujiang.cj.ac_staffSend.m.AccountDetail_cj;
import com.example.choujiang.cj.ac_staffSend.m.Activity_cj;
import com.example.choujiang.cj.ac_staffSend.m.StaffSend_cj;
import com.example.choujiang.cj.ac_staffSend.m.Staff_cj;
import com.example.choujiang.cj.ac_withdrawSetting.m.WithdrawSetting;
import com.example.choujiang.cj.m.LoginData_pt;
import com.example.choujiang.cj.m.Report_cj;
import com.example.choujiang.model.Response;

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

    //抽奖汇总报表
    @GET("api/cj/report/total")
    Observable<Response<Report_cj>> getReport_pt(@Header("authorization") String authorization, @Query("status") int status);

    //活动报表
    @GET("api/cj/report/report")
    Observable<Response<ArrayList<Activity_cj>>> getAcBb(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);

    //抽奖记录
    @GET("api/cj/cjAwardDetail/list")
    Observable<Response<ArrayList<CjHistory>>> getCjHistory(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //员工排行-0全部 1本日 2本月 3本年
    @GET("api/cj/report/staffRanking")
    Observable<Response<ArrayList<Staff_cj>>> getStaffRanking_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);
    @GET("api/cj/report/staffRanking")
    Observable<Response<ArrayList<Staff_cj>>> getStaffRanking_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status, @Query("actId") String actId);

    //抽奖员工提成明细
    @GET("api/cj/cjStaffCommission/list")
    Observable<Response<ArrayList<TichengDetail>>> getTichengDetail(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //活动列表 状态 0全部 1可用 2暂停 3作废
    @GET("api/cj/cjActivity/list")
    Observable<Response<ArrayList<Activity_cj>>> getAc_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);

    //活动详情
    @GET("api/cj/cjActivity/info")
    Observable<Response<ActivityDetail_cj>> getAcDetail_cj(@Header("authorization") String authorization, @Query("actId") String actId);

    //中奖记录
    @GET("api/cj/cjAwardDetail/list")
    Observable<Response<ArrayList<CjHistory>>> getWinHistory(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("isAward") String isAward, @Query("actId") String actId);

    //员工列表
    @GET("api/common/staff/list")
    Observable<Response<ArrayList<Staff_cj>>> getStaff_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //账户明细
    @GET("api/common/staffAccount/{staffId}")
    Observable<Response<ArrayList<AccountDetail_cj>>> getAccountDetail(@Header("authorization") String authorization, @Path("staffId") String staffId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //员工账户提现
    @POST("api/common/staffAccount/withdrawals")
    Observable<Response> accountWithDraw(@Header("authorization") String authorization, @Query("staffId") String staffId, @Query("amount") String amount);

    //员工发送列表
    @GET("api/cj/cjActivityStaff/list")
    Observable<Response<ArrayList<StaffSend_cj>>> getStaffSends_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //员工活动列表
    @GET("api/cj/cjActivityStaff/act/list")
    Observable<Response<ArrayList<Activity_cj>>> getStaffAcDetail_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("staffId") int staffId);

    //提交员工发送
    @POST("api/cj/cjActivityStaff/save")
    Observable<Response> saveStaffSend(@Header("authorization") String authorization, @Query("actIds") String actIds, @Query("staffId") String staffId);

    //抽奖兑换记录
    @GET("api/cj/cjAwardDetail/exChangeRecord")
    Observable<Response<ArrayList<CjHistory>>> getMembergetList_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //活动停用/作废
    @POST("api/cj/cjActivity/state")
    Observable<Response> changeAcStatus(@Header("authorization") String authorization, @Query("actId") String actId, @Query("status") int status);

    //获取七牛上传token
    @GET("api/common/uploadTokens")
    Observable<Response<QiniuToKen>> getQiniuToken(@Header("authorization") String authorization);

    //活动奖品列表
    @GET("api/cj/cjActivityAward/list")
    Observable<Response<ArrayList<Award>>> getAwardList(@Header("authorization") String authorization, @Query("actId") String actId);

    //新增中奖记录
    @POST("api/cj/cjAwardDetail/saveRecord")
    Observable<Response> saveWinHistory(@Header("authorization") String authorization, @Query("actId") String actId, @Query("mobile") String mobile, @Query("awardId") String awardId);

    //创建活动
    @POST("api/cj/cjActivity/save")
    Observable<Response<Activity_cj>> saveAc(@Header("authorization") String authorization, @Query("activityName") String activityName, @Query("activityImgUrl") String activityImgUrl, @Query("beginTime") String beginTime, @Query("endTime") String endTime, @Query("count") String count, @Query("shareCount") String shareCount, @Query("exchangeCount") String exchangeCount, @Query("remarks") String remarks);

    //修改活动
    @POST("api/cj/cjActivity/save")
    Observable<Response> saveAc(@Header("authorization") String authorization, @Query("activityName") String activityName, @Query("activityImgUrl") String activityImgUrl, @Query("beginTime") String beginTime, @Query("endTime") String endTime, @Query("count") String count, @Query("shareCount") String shareCount, @Query("exchangeCount") String exchangeCount, @Query("remarks") String remarks, @Query("actId") String actId);

    //创建奖品
    @POST("api/cj/cjActivityAward/save")
    Observable<Response<Award>> saveAward(@Header("authorization") String authorization, @Query("actId") String actId, @Query("awardName") String awardName, @Query("awardPrice") String awardPrice, @Query("awardNum") String awardNum, @Query("awardOdds") String awardOdds, @Query("awardImgUrl") String awardImgUrl);

    //修改奖品
    @POST("api/cj/cjActivityAward/save")
    Observable<Response> saveAward(@Header("authorization") String authorization, @Query("actId") String actId, @Query("awardName") String awardName, @Query("awardPrice") String awardPrice, @Query("awardNum") String awardNum, @Query("awardOdds") String awardOdds, @Query("awardImgUrl") String awardImgUrl, @Query("awardId") String awardId);

    //商户配置
    @GET("api/common/merchantConfig")
    Observable<Response<WithdrawSetting>> getSetting(@Header("authorization") String authorization, @Query("model") String model);

    //保存商户配置
    @POST("api/common/merchantConfig/save")
    Observable<Response> saveSetting(@Header("authorization") String authorization, @Query("model") String model, @Query("type") int type, @Query("value") int value);

    //抽奖明细--后台(扫描二维码获取detailId
    @GET("api/cj/cjAwardDetail/{detailId}")
    Observable<Response<CjHistory>> getCjQrDetail(@Header("authorization") String authorization, @Path("detailId") String detailId);

    //奖品兑换
    @POST("api/cj/cjAwardDetail/exchange")
    Observable<Response> AwardExchange(@Header("authorization") String authorization, @Query("detailId") String detailId, @Query("redeemCode") String redeemCode);


}
