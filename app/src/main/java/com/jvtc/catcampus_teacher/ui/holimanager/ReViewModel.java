package com.jvtc.catcampus_teacher.ui.holimanager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jvtc.catcampus_teacher.data.LoginRepository;
import com.jvtc.catcampus_teacher.http.HttpCallBack;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Status
 * 1 等待班主任审批
 * 2 等待院系审核
 * 4 班主任审核拒绝
 * 5 院系审核通过
 * 7 院系审核拒绝
 * 9 学校审核拒绝
 */
public class ReViewModel extends ViewModel {
    private MutableLiveData<ReViewResult> reViewResultMutableLiveData;
    private MutableLiveData<OperationResult> operationResultMutableLiveData;
    private MutableLiveData<Integer> selectMutableLiveData;

    public ReViewModel() {
        reViewResultMutableLiveData = new MutableLiveData<>();
        operationResultMutableLiveData = new MutableLiveData<>();
        selectMutableLiveData = new MutableLiveData<>();
        selectMutableLiveData.setValue(1);
    }

    public MutableLiveData<ReViewResult> getReViewResultMutableLiveData() {
        return reViewResultMutableLiveData;
    }

    public MutableLiveData<OperationResult> getOperationResultMutableLiveData() {
        return operationResultMutableLiveData;
    }

    public MutableLiveData<Integer> getSelectMutableLiveData() {
        return selectMutableLiveData;
    }

    public void queryData(int Status) {
        JSONObject req = new JSONObject();
        try {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            //一个基准
            BasicTime basicTime = new BasicTime();
            basicTime.month = 7;
            basicTime.year = year;
            basicTime.time = year + "-" + (year + 1) + "-1";
            String TermNo = null;
            if (month < basicTime.month) {
                //上学期
                TermNo = (year - 1) + "-" + year + "-2";
            } else {
                //下学期
                TermNo = year + "-" + (year + 1) + "-1";
            }
            req.put("TermNo", TermNo);
            req.put("ClassNo", "");
            req.put("StudentNo", "");
            req.put("StudentName", "");
            req.put("Status", Status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.ncgameUrl).create(RxApis.class).FDYAllLeaveExam(
                "Bearer " + LoginRepository.getInstance().getLoggedInUser().getCookie2(), requestBody), new HttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                reViewResultMutableLiveData.setValue(new ReViewResult("请检查网络链接", null));
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        List<ReViewItem> list = new ArrayList<>();
                        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("list");
                        for (int i = 0; i < array.length(); i++) {
                            ReViewItem reViewItem = new ReViewItem();
                            reViewItem.id = array.getJSONObject(i).getString("id");
                            reViewItem.stu_id = array.getJSONObject(i).getString("stu_id");
                            reViewItem.name = array.getJSONObject(i).getString("name");
                            reViewItem.sex = array.getJSONObject(i).getString("sex");
                            reViewItem.date = array.getJSONObject(i).getString("date");
                            reViewItem.x_date = array.getJSONObject(i).getString("x_date");
                            reViewItem.reason = array.getJSONObject(i).getString("reason");
                            reViewItem.location = array.getJSONObject(i).getString("location");
                            reViewItem.classbj = array.getJSONObject(i).getString("class");
                            reViewItem.period = array.getJSONObject(i).getString("period");
                            reViewItem.stat = array.getJSONObject(i).getString("stat");
                            list.add(reViewItem);
                        }
                        reViewResultMutableLiveData.setValue(new ReViewResult(null, list));
                    } else {
                        reViewResultMutableLiveData.setValue(new ReViewResult("没有查询到请假信息", null));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Operation(int type, String id) {
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.ncgameUrl).create(RxApis.class).FDYAllLeaveExam_Edit(
                "Bearer " + LoginRepository.getInstance().getLoggedInUser().getCookie2(), id,String.valueOf(type)
        ), new HttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                operationResultMutableLiveData.setValue(new OperationResult("请检查网络", null));
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 0 && jsonObject.getJSONObject("data").getInt("stat") == 1) {
                        operationResultMutableLiveData.setValue(new OperationResult(null, jsonObject.getJSONObject("data").getString("msg")));
                        return;
                    } else {
                        operationResultMutableLiveData.setValue(new OperationResult(jsonObject.getString("message"),null));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class BasicTime {
        public String time;
        public int year;
        public int month;
    }

    class ReViewItem {
        public String id;
        public String stu_id;
        public String name;
        public String sex;
        public String date;
        public String x_date;
        public String reason;
        public String location;
        public String classbj;
        public String period;
        public String stat;
    }

    class ReViewResult {
        public String error;
        public List<ReViewItem> list;

        public ReViewResult(String error, List<ReViewItem> list) {
            this.error = error;
            this.list = list;
        }
    }

    class OperationResult {
        public String error;
        public String success;

        public OperationResult(String error, String success) {
            this.error = error;
            this.success = success;
        }
    }
}
