package com.example.huaxiang.network.retrofit;


import com.example.huaxiang.hx.ac_acSetting.m.ActivityDetail_cj;
import com.example.huaxiang.hx.ac_acSetting.m.Award;
import com.example.huaxiang.hx.ac_acSetting.m.QiniuToKen;
import com.example.huaxiang.hx.ac_bb.m.CjDetail;
import com.example.huaxiang.hx.ac_bb.m.HxTopic;
import com.example.huaxiang.hx.ac_bb.m.IntentionCustomer;
import com.example.huaxiang.hx.ac_bb.m.Reback_hx;
import com.example.huaxiang.hx.ac_bb.m.TichengDetail;
import com.example.huaxiang.hx.ac_memberget.m.CjHistory;
import com.example.huaxiang.hx.ac_staffSend.m.Activity_cj;
import com.example.huaxiang.hx.ac_staffSend.m.StaffSend_hx;
import com.example.huaxiang.hx.ac_staffSend.m.Staff_cj;
import com.example.huaxiang.hx.ac_withdrawSetting.m.AccountDetail_cj;
import com.example.huaxiang.hx.ac_withdrawSetting.m.WithdrawSetting;
import com.example.huaxiang.hx.m.LoginData_pt;
import com.example.huaxiang.hx.m.Report_hx;
import com.example.huaxiang.model.Response;

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

    //画像汇总报表  0全部 1本日 2本月 3本年
    @GET("api/hx/report/total")
    Observable<Response<Report_hx>> getReport_hx(@Header("authorization") String authorization, @Query("status") int status);

    //抽奖兑换记录
    @GET("api/hx/hxAwardDetail/exChangeRecord")
    Observable<Response<ArrayList<CjHistory>>> getMembergetList_hx(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //员工发送列表
    @GET("api/hx/hxActivityStaff/list")
    Observable<Response<ArrayList<StaffSend_hx>>> getStaffSends_hx(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //员工活动列表 状态 0全部 1可用 2暂停 3作废
    @GET("api/hx/hxActivity/list")
    Observable<Response<ArrayList<Activity_cj>>> getStaffAcDetail_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status, @Query("userId") String userId);

    //员工列表
    @GET("api/common/staff/list")
    Observable<Response<ArrayList<Staff_cj>>> getStaff_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //活动列表 状态 0全部 1可用 2暂停 3作废
    @GET("api/hx/hxActivity/list")
    Observable<Response<ArrayList<Activity_cj>>> getAc_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);

    //提交员工发送
    @POST("api/hx/hxActivityStaff/save")
    Observable<Response> saveStaffSend(@Header("authorization") String authorization, @Query("staffId") String staffId, @Query("actIds") String actIds);

    //账户明细
    @GET("api/common/staffAccount/{staffId}")
    Observable<Response<ArrayList<AccountDetail_cj>>> getAccountDetail(@Header("authorization") String authorization, @Path("staffId") String staffId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //员工账户提现
    @POST("api/common/staffAccount/withdrawals")
    Observable<Response> accountWithDraw(@Header("authorization") String authorization, @Query("staffId") String staffId, @Query("amount") String amount);

    //活动报表  0全部 1本日 2本月 3本年
    @GET("api/hx/report")
    Observable<Response<ArrayList<Activity_cj>>> getAcBb(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);

    //意向客户  是否中奖(0否 1是 不传：全部)
    @GET("api/hx/hxOrder/list")
    Observable<Response<ArrayList<IntentionCustomer>>> getIntentionCustomer(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);
    @GET("api/hx/hxOrder/list")
    Observable<Response<ArrayList<IntentionCustomer>>> getIntentionCustomer(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("isAward") String isAward);

    //抽奖详情
    @GET("api/hx/hxOrder/{number}")
    Observable<Response<CjDetail>> getCjDetail(@Header("authorization") String authorization, @Path("number") String number);

    //问卷记录
    @GET("api/hx/hxTopic/{orderId}")
    Observable<Response<ArrayList<HxTopic>>> getCjDetailTopic(@Header("authorization") String authorization, @Path("orderId") String orderId);

    //员工排行-0全部 1本日 2本月 3本年
    @GET("api/hx/report/staffRanking")
    Observable<Response<ArrayList<Staff_cj>>> getStaffRanking_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);
    @GET("api/hx/report/staffRanking")
    Observable<Response<ArrayList<Staff_cj>>> getStaffRanking_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status, @Query("actId") String actId);

    //抽奖员工提成明细
    @GET("api/hx/hxStaffCommission/list")
    Observable<Response<ArrayList<TichengDetail>>> getTichengDetail(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //回访记录
    @GET("api/hx/hxMemberVisit/list")
    Observable<Response<ArrayList<Reback_hx>>> getRebackList(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("type") int type);

    //回访详情--提交
    @POST("api/hx/hxMemberVisit/submit")
    Observable<Response<Reback_hx>> addRecord(@Header("authorization") String authorization, @Query("visitId") String visitId, @Query("content") String content);

    //回访详情--操作   2转化客户 3无意向
    @POST("api/hx/hxMemberVisit/operating")
    Observable<Response<Reback_hx>> setRebackIntention(@Header("authorization") String authorization, @Query("visitId") String visitId, @Query("type") String type);

    //回访详情
    @GET("api/hx/hxMemberVisit/{visitId}")
    Observable<Response<Reback_hx>> getRebackDetail(@Header("authorization") String authorization, @Path("visitId") String visitId);

    //活动详情
    @GET("api/hx/hxActivity/info")
    Observable<Response<ActivityDetail_cj>> getAcDetail_cj(@Header("authorization") String authorization, @Query("actId") String actId);

    //活动停用/作废  2停用 3作废
    @POST("api/hx/hxActivity/state")
    Observable<Response> changeAcStatus(@Header("authorization") String authorization, @Query("actId") String actId, @Query("status") int status);

    //获取七牛上传token
    @GET("api/common/uploadTokens")
    Observable<Response<QiniuToKen>> getQiniuToken(@Header("authorization") String authorization);

    //创建活动
    @POST("api/hx/hxActivity/save")
    Observable<Response<ActivityDetail_cj>> saveAc(@Header("authorization") String authorization, @Query("activityName") String activityName, @Query("activityImgUrl") String activityImgUrl, @Query("activityVideoUrl") String activityVideoUrl, @Query("beginTime") String beginTime, @Query("endTime") String endTime, @Query("money") String money, @Query("num") String num, @Query("saleNum") String saleNum, @Query("replaceTime") String replaceTime, @Query("carCheck") int carCheck);

    //修改活动
    @POST("api/hx/hxActivity/save")
    Observable<Response> saveAc(@Header("authorization") String authorization, @Query("activityName") String activityName, @Query("activityImgUrl") String activityImgUrl, @Query("activityVideoUrl") String activityVideoUrl, @Query("beginTime") String beginTime, @Query("endTime") String endTime, @Query("money") String money, @Query("num") String num, @Query("saleNum") String saleNum, @Query("replaceTime") String replaceTime, @Query("carCheck") int carCheck, @Query("actId") String actId);

    //活动奖品列表  type  1正常 2代抽奖品
    @GET("api/hx/hxActivityAward/list")
    Observable<Response<ArrayList<Award>>> getAwardList(@Header("authorization") String authorization, @Query("actId") String actId, @Query("type") int type);

    //创建奖品
    @POST("api/hx/hxActivityAward/save")
    Observable<Response<Award>> saveAward(@Header("authorization") String authorization, @Query("actId") String actId, @Query("awardName") String awardName, @Query("awardPrice") String awardPrice, @Query("awardNum") String awardNum, @Query("awardOdds") String awardOdds, @Query("replaceAwardOdds") String replaceAwardOdds, @Query("awardImgUrl") String awardImgUrl);

    //修改奖品
    @POST("api/hx/hxActivityAward/save")
    Observable<Response> saveAward(@Header("authorization") String authorization, @Query("actId") String actId, @Query("awardName") String awardName, @Query("awardPrice") String awardPrice, @Query("awardNum") String awardNum, @Query("awardOdds") String awardOdds, @Query("replaceAwardOdds") String replaceAwardOdds, @Query("awardImgUrl") String awardImgUrl, @Query("awardId") String awardId);

    //创建代抽奖品
    @POST("api/hx/hxActivityAward/replace")
    Observable<Response<Award>> saveReplaceAward(@Header("authorization") String authorization, @Query("actId") String actId, @Query("awardName") String awardName, @Query("awardPrice") String awardPrice, @Query("awardImgUrl") String awardImgUrl);

    //修改代抽奖品
    @POST("api/hx/hxActivityAward/replace")
    Observable<Response> saveReplaceAward(@Header("authorization") String authorization, @Query("actId") String actId, @Query("awardName") String awardName, @Query("awardPrice") String awardPrice, @Query("awardImgUrl") String awardImgUrl, @Query("awardId") String awardId);

    //删除活动奖品
    @DELETE("api/hx/hxActivityAward")
    Observable<Response> deleteAward(@Header("authorization") String authorization, @Query("awardId") String awardId);

    //中奖记录
    @GET("api/hx/hxAwardDetail/list")
    Observable<Response<ArrayList<CjHistory>>> getWinHistory(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("actId") String actId);

    //新增中奖记录
    @POST("api/hx/hxAwardDetail/saveRecord")
    Observable<Response> addWinHistory(@Header("authorization") String authorization, @Query("actId") String actId, @Query("mobile") String mobile, @Query("awardId") String awardId);

    //问卷调查列表
    @GET("api/hx/hxTopic/list")
    Observable<Response<ArrayList<HxTopic>>> getTopicList(@Header("authorization") String authorization, @Query("actId") String actId);

    //创建问卷调查
    @POST("api/hx/hxTopic/save")
    Observable<Response> addTopic(@Header("authorization") String authorization, @Query("question") String question, @Query("type") int type, @Query("option") String option, @Query("answer") String answer, @Query("actId") String actId);

    //修改问卷调查
    @POST("api/hx/hxTopic/save")
    Observable<Response> editTopic(@Header("authorization") String authorization, @Query("question") String question, @Query("type") int type, @Query("option") String option, @Query("answer") String answer, @Query("actId") String actId, @Query("topicId") String topicId);

    //删除问卷调查
    @DELETE("api/hx/hxTopic")
    Observable<Response> deleteTopic(@Header("authorization") String authorization, @Query("topicId") String topicId);

    //抽奖明细--后台(扫描二维码获取detailId)
    @GET("api/hx/hxAwardDetail/{detailId}")
    Observable<Response<CjHistory>> getCjQrDetail(@Header("authorization") String authorization, @Path("detailId") String detailId);

    //奖品兑换
    @POST("api/hx/hxAwardDetail/exchange")
    Observable<Response> AwardExchange(@Header("authorization") String authorization, @Query("detailId") String detailId, @Query("redeemCode") String redeemCode);

    //商户配置
    @GET("api/common/merchantConfig")
    Observable<Response<WithdrawSetting>> getSetting(@Header("authorization") String authorization, @Query("model") String model);

    //保存商户配置
    @POST("api/common/merchantConfig/save")
    Observable<Response> saveSetting(@Header("authorization") String authorization, @Query("model") String model, @Query("type") int type, @Query("value") int value);

}
