package com.jvtc.catcampus_teacher.data;

import com.jvtc.catcampus_teacher.http.HttpCallBack;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * skyx
 * 所有 ""
 * 教务处 00
 * 机械工程学院 01
 * 电气工程学院 02
 * 船舶工程学院 03
 * 汽车工程学院 04
 * 信息工程学院 05
 * 建筑工程学院 06
 * 财会金融学院 07
 * 经济管理学院 08
 * 继续教育学院 09
 * 马克思主义学院 23
 * 创新创业学院 24
 * 工程训练中心 25
 * 学生工作处 27
 *
 * xqid
 * 所有 ""
 * 十里校区 1
 * 继教部 2
 * 濂溪校区 3
 *
 * jzwid
 * 所有 ""
 * 濂溪实训楼 301
 * 濂溪教学楼 303
 * 濂溪综合楼B区 302
 * 濂溪图书馆 304
 * 十里一号教学楼 101
 * 十里二号教学楼 102
 * 十里三号教学楼 103
 * 十里四号教学楼 104
 * 十里一号实验楼 105
 * 十里二号实验楼 106
 * 十里工业工程中心 107
 * 十里仪表厂厂房 108
 * 十里工业训练中心 109
 * 十里老综合楼 110
 * 十里焊接实训中心 111
 * 十里汽车专业实训楼 112
 */
public class ClassQueryRepsitory {
    private static volatile ClassQueryRepsitory instance;

    public static ClassQueryRepsitory getInstance() {
        if (instance == null) {
            instance = new ClassQueryRepsitory();
        }
        return instance;
    }


    public void getData(String xnxqh,String skyx,String xqid,String jzwid,String skjs,RxHttpCallBack httpCallBack){
        JSONObject req = new JSONObject();
        try {
            req.put("xnxqh", xnxqh); //学期
            req.put("skyx", skyx); //学院
            req.put("xqid", xqid); //校区
            req.put("jzwid", jzwid); //楼栋
            req.put("skjs", skjs); //教室
            req.put("zc1", "");
            req.put("zc2", "");
            req.put("skxq1", "");
            req.put("skxq2", "");
            req.put("jc1", "");
            req.put("jc2", "");
            req.put("cookie", LoginRepository.getInstance().getLoggedInUser().getCookie());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.baseUrl).create(RxApis.class).teach_classcourse_info(requestBody), new HttpCallBack() {
            @Override
            public void onCompleted() {
                httpCallBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                httpCallBack.onError(new Result.Error(e,null));
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        httpCallBack.onSuccess(new Result.Success(array));
                    } else {
                        httpCallBack.onError(new Result.Error(null, "没有查询到这个教室课表"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
