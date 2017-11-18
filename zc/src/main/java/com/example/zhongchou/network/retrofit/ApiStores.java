package com.example.zhongchou.network.retrofit;


import com.example.zhongchou.model.Response;
import com.example.zhongchou.zc.ac_ptList.m.QiniuToKen;
import com.example.zhongchou.zc.ac_ptList.m.Store_cj;
import com.example.zhongchou.zc.ac_ptbb.m.PdDetail;
import com.example.zhongchou.zc.ac_ptbb.m.PinDan_pt;
import com.example.zhongchou.zc.ac_ptbb.m.PinTuan_pt;
import com.example.zhongchou.zc.ac_ptbb.m.TichengDetail;
import com.example.zhongchou.zc.ac_staffSend.m.Activity_pt;
import com.example.zhongchou.zc.ac_staffSend.m.StaffSend_pt;
import com.example.zhongchou.zc.ac_staffSend.m.Staff_pt;
import com.example.zhongchou.zc.ac_withdrawSetting.m.AccountDetail_pt;
import com.example.zhongchou.zc.ac_withdrawSetting.m.WithdrawSetting;
import com.example.zhongchou.zc.m.LoginData_pt;
import com.example.zhongchou.zc.m.PtReport_pt;

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
    Observable<Response<ArrayList<Activity_pt>>> getStaffDetail_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status, @Query("userId") String userId);

    //活动列表 状态 0全部 1可用 2暂停 3作废
    @GET("api/pt/ptActivities/list")
    Observable<Response<ArrayList<Activity_pt>>> getAc_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("status") int status);

    //活动详情
    @GET("api/pt/ptActivities/info")
    Observable<Response<Activity_pt>> getAcDetail(@Header("authorization") String authorization, @Query("actId") String actId);

    //员工列表
    @GET("api/pt/ptActivityStaff/staff/list")
    Observable<Response<ArrayList<Staff_pt>>> getStaffSelect_pt(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //提交员工发送
    @POST("api/pt/ptActivityStaff/submit")
    Observable<Response> saveStaffSend(@Header("authorization") String authorization, @Query("activityIds") String activityIds, @Query("userId") String userId);

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

    //拼单明细 扫二维码
    @GET("api/pt/ptOrderDetail/{detailId}")
    Observable<Response<PinDan_pt>> getPinDanDetail(@Header("authorization") String authorization, @Path("detailId") String detailId);

    //拼单兑换
    @POST("api/pt/ptOrderDetail/exchange")
    Observable<Response> PinDanExchange(@Header("authorization") String authorization, @Query("detailId") String detailId, @Query("redeemCode") String redeemCode);

    //拼单退款
    @POST("api/pt/ptGroupOrder/refund")
    Observable<Response> refundPinDan(@Header("authorization") String authorization, @Query("id") String id);

    //拼团按钮
    @POST("api/pt/ptGroupOrder/group")
    Observable<Response> pintuan(@Header("authorization") String authorization, @Query("id") String id);

    //拼团员工提成明细
    @GET("api/pt/ptStaffCommission/list")
    Observable<Response<ArrayList<TichengDetail>>> getTichengList(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //拼单明细 扫二维码
    @GET("api/pt/ptAppGroupOrder/detail")
    Observable<Response<PdDetail>> getPdDetail(@Header("authorization") String authorization, @Query("orderNumber") String orderNumber);

    //员工列表
    @GET("api/common/staff/list")
    Observable<Response<ArrayList<Staff_pt>>> getStaff_cj(@Header("authorization") String authorization, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("storeId") String storeId);

    //门店列表
    @GET("api/common/store/list")
    Observable<Response<ArrayList<Store_cj>>> getStore_cj(@Header("authorization") String authorization);

    //创建活动
    @POST("api/pt/ptActivities/save")
    Observable<Response> addAc(@Header("authorization") String authorization, @Query("goodName") String goodName, @Query("originalPrice") String originalPrice, @Query("price") String price, @Query("count") String count
            , @Query("videoUrl") String videoUrl, @Query("goodImgUrl") String goodImgUrl, @Query("activityName") String activityName, @Query("activityImgUrl") String activityImgUrl, @Query("beginTime") String beginTime
            , @Query("endTime") String endTime, @Query("num") String num, @Query("saleNum") String saleNum, @Query("storeId") String storeId, @Query("saleRemarks") String saleRemarks);

    //修改活动
    @POST("api/pt/ptActivities/update")
    Observable<Response> editAc(@Header("authorization") String authorization, @Query("actId") String actId, @Query("goodName") String goodName, @Query("originalPrice") String originalPrice, @Query("price") String price, @Query("count") String count
            , @Query("videoUrl") String videoUrl, @Query("goodImgUrl") String goodImgUrl, @Query("activityName") String activityName, @Query("activityImgUrl") String activityImgUrl, @Query("beginTime") String beginTime
            , @Query("endTime") String endTime, @Query("num") String num, @Query("saleNum") String saleNum, @Query("storeId") String storeId, @Query("saleRemarks") String saleRemarks);

    //商户配置
    @GET("api/common/merchantConfig")
    Observable<Response<WithdrawSetting>> getSetting(@Header("authorization") String authorization, @Query("model") String model);

    //保存商户配置
    @POST("api/common/merchantConfig/save")
    Observable<Response> saveSetting(@Header("authorization") String authorization, @Query("model") String model, @Query("type") int type, @Query("value") int value);

    //账户明细
    @GET("api/common/staffAccount/{staffId}")
    Observable<Response<ArrayList<AccountDetail_pt>>> getAccountDetail(@Header("authorization") String authorization, @Path("staffId") String staffId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    //员工账户提现
    @POST("api/common/staffAccount/withdrawals")
    Observable<Response> accountWithDraw(@Header("authorization") String authorization, @Query("staffId") String staffId, @Query("amount") String amount);


}
