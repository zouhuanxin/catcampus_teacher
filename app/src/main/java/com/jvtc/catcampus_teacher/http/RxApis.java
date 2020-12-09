package com.jvtc.catcampus_teacher.http;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RxApis {

    @POST("/test/jwlogin")
    @Headers("source:jiu-shi")
    Observable<JSONObject> login(@Body RequestBody req);

    //{"rq":"2020-12-01","cookie":"EE78A08EC6A32D1E1E038E3342456D66"}
    @POST("/test/kcinfo")
    @Headers("source:jiu-shi")
    Observable<JSONObject> getCourses(@Body RequestBody req);

    //https://jvtc.notbucai.com/jwxt/course/week
    @GET("/jwxt/course/week")
    @Headers("User-Agent:jiu-shi")
    Observable<JSONObject> getWeek();

    @GET("/jvtc/data")
    @Headers("User-Agent:jiu-shi")
    Observable<JSONObject> getData();

    @POST("/test/teach_teach_info")
    @Headers("source:jiu-shi")
    Observable<JSONObject> teach_teach_info(@Body RequestBody req);

    @POST("/test/teach_hmc_info")
    @Headers("source:jiu-shi")
    Observable<JSONObject> teach_hmc_info(@Body RequestBody req);

    @POST("/test/teach_classcourse_info")
    @Headers("source:jiu-shi")
    Observable<JSONObject> teach_classcourse_info(@Body RequestBody req);

    @POST("/test/teach_uploadpass_info")
    @Headers("source:jiu-shi")
    Observable<JSONObject> teach_uploadpass_info(@Body RequestBody req);

    @POST("/jvtc/login")
    @Headers("User-Agent:jiu-shi")
    Observable<JSONObject> xglogin(@Body RequestBody req);

    //{'Authorization':'Bearer ' + token}
    @GET("/jvtc/TeacherReSetpass")
    @Headers({"User-Agent:jiu-shi"})
    Observable<JSONObject> teacherReSetpass(@Header("Authorization") String token, @Query("StudentNo") String StudentNo);

    @POST("/jvtc/TeacherChangePass")
    @Headers({"User-Agent:jiu-shi","Content-Type:application/json"})
    Observable<JSONObject> teacherChangePass(@Header("Authorization") String token,@Body RequestBody req);

    /**
     * 获取学生请假信息
     * TermNo 学期
     * Status 状态
     * {"TermNo": "'+TermNo+'","ClassNo": "","StudentNo": "","StudentName": "","Status": "'+Status+'"}
     * @param token
     * @param req
     * @return
     */
    @POST("/jvtc/FDYAllLeaveExam")
    @Headers({"User-Agent:jiu-shi","Content-Type:application/json"})
    Observable<JSONObject> FDYAllLeaveExam(@Header("Authorization") String token,@Body RequestBody req);

    @GET("/jvtc/FDYAllLeaveExam_Edit")
    @Headers({"User-Agent:jiu-shi","Content-Type:application/json"})
    Observable<JSONObject> FDYAllLeaveExam_Edit(@Header("Authorization") String token,@Query("id") String id,@Query("type") String type);

    /**
     * 获取所有销假信息
     * {"TermNo": "'+TermNo+'","ClassNo": "","StudentNo": "","StudentName": "","LeaveType":""}
     * {"TermNo": "","ClassNo": "","StudentNo": "","StudentName": "","LeaveType":"","ids":["'+ids+'"]}
     * @param token
     * @param req
     * @return
     */
    @POST("/jvtc/FDYDisAllLeave")
    @Headers({"User-Agent:jiu-shi","Content-Type:application/json"})
    Observable<JSONObject> FDYDisAllLeave(@Header("Authorization") String token,@Body RequestBody req);

    @GET("/getAllSystemNotice")
    @Headers("source:jiu-shi")
    Observable<JSONObject> getAllSystemNotice();

    @GET("/getAllShufflingFigure")
    @Headers("source:jiu-shi")
    Observable<JSONObject> getAllShufflingFigure();
}
