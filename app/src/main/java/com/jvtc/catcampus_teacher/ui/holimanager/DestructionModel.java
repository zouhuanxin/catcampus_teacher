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
public class DestructionModel extends ViewModel {
    private MutableLiveData<DestructionResult> reViewResultMutableLiveData;
    private MutableLiveData<OperationResult> operationResultMutableLiveData;
    public DestructionModel() {
        reViewResultMutableLiveData = new MutableLiveData<>();
        operationResultMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<DestructionResult> getReViewResultMutableLiveData() {
        return reViewResultMutableLiveData;
    }

    public MutableLiveData<OperationResult> getOperationResultMutableLiveData() {
        return operationResultMutableLiveData;
    }

    public void queryData() {
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
            req.put("LeaveType", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.ncgameUrl).create(RxApis.class).FDYDisAllLeave(
                "Bearer " + LoginRepository.getInstance().getLoggedInUser().getCookie2(), requestBody), new HttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                reViewResultMutableLiveData.setValue(new DestructionResult("请检查网络链接", null));
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        List<DestructionItem> list = new ArrayList<>();
                        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("list");
                        for (int i = 0; i < array.length(); i++) {
                            DestructionItem destructionItem = new DestructionItem();
                            destructionItem.id = array.getJSONObject(i).getString("id");
                            destructionItem.stu_id = array.getJSONObject(i).getString("stu_id");
                            destructionItem.name = array.getJSONObject(i).getString("name");
                            destructionItem.sex = array.getJSONObject(i).getString("sex");
                            destructionItem.date = array.getJSONObject(i).getString("date");
                            destructionItem.x_date = array.getJSONObject(i).getString("x_date");
                            destructionItem.type = array.getJSONObject(i).getString("type");
                            destructionItem.location = array.getJSONObject(i).getString("location");
                            destructionItem.classbj = array.getJSONObject(i).getString("class");
                            destructionItem.term = array.getJSONObject(i).getString("term");
                            list.add(destructionItem);
                        }
                        reViewResultMutableLiveData.setValue(new DestructionResult(null, list));
                    } else {
                        reViewResultMutableLiveData.setValue(new DestructionResult("没有查询到请假信息", null));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Operation(JSONArray arrary) {
        JSONObject req = new JSONObject();
        try {
            req.put("TermNo", "");
            req.put("ClassNo", "");
            req.put("StudentNo", "");
            req.put("StudentName", "");
            req.put("LeaveType", "");
            req.put("ids", arrary);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.ncgameUrl).create(RxApis.class).FDYDisAllLeave(
                "Bearer " + LoginRepository.getInstance().getLoggedInUser().getCookie2(), requestBody), new HttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                operationResultMutableLiveData.setValue(new OperationResult("请检查网络链接", null));
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    try {
                        if (jsonObject.getInt("code") == 0) {
                            operationResultMutableLiveData.setValue(new OperationResult(null, "销假成功"));
                        } else {
                            operationResultMutableLiveData.setValue(new OperationResult("销假失败", null));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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

    class DestructionItem {
        public String id;
        public String stu_id;
        public String name;
        public String sex;
        public String date;
        public String x_date;
        public String type;
        public String location;
        public String classbj;
        public String term;
    }

    class DestructionResult {
        public String error;
        public List<DestructionItem> list;

        public DestructionResult(String error, List<DestructionItem> list) {
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
