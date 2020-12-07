package com.jvtc.catcampus_teacher.ui.roster;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jvtc.catcampus_teacher.data.Result;
import com.jvtc.catcampus_teacher.data.RosterRepoistory;
import com.jvtc.catcampus_teacher.data.model.RosterDetailsInItem;
import com.jvtc.catcampus_teacher.data.model.RosterInItem;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RosterViewModel extends ViewModel {
    private MutableLiveData<RosterResult> rosterInItemMutableLiveData;
    private MutableLiveData<RosterDetailsResult> rosterDetailsResultMutableLiveData;

    public RosterViewModel(){
        rosterInItemMutableLiveData = new MutableLiveData<>();
        rosterDetailsResultMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RosterResult> getRosterInItemMutableLiveData() {
        return rosterInItemMutableLiveData;
    }

    public MutableLiveData<RosterDetailsResult> getRosterDetailsResultMutableLiveData() {
        return rosterDetailsResultMutableLiveData;
    }

    public void initRosterData(){
        RosterRepoistory.getInstance().getTeachClassInfo(new RxHttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Result.Error data) {
                rosterInItemMutableLiveData.setValue(new RosterResult(data.getMessage(),null));
            }

            @Override
            public void onSuccess(Result.Success data) {
                JSONArray array = (JSONArray) data.getData();
                List<RosterInItem> list = new ArrayList<>();
                for (int i=0;i<array.length();i++){
                    RosterInItem rosterInItem = new RosterInItem();
                    try {
                        rosterInItem.setBj(array.getJSONObject(i).getString("bj"));
                        rosterInItem.setKc(array.getJSONObject(i).getString("kc"));
                        rosterInItem.setHmc(array.getJSONObject(i).getString("hmc"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    list.add(rosterInItem);
                }
                rosterInItemMutableLiveData.setValue(new RosterResult(null,list));
            }
        });
    }

    public void initRosterDetailsData(String jx0404id){
        RosterRepoistory.getInstance().getTeachDetailsInfo(jx0404id, new RxHttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Result.Error data) {
                rosterDetailsResultMutableLiveData.setValue(new RosterDetailsResult(data.getMessage(),null));
            }

            @Override
            public void onSuccess(Result.Success data) {
                JSONArray array = (JSONArray) data.getData();
                List<RosterDetailsInItem> list = new ArrayList<>();
                for (int i=0;i<array.length();i++){
                    RosterDetailsInItem rosterDetailsInItem = new RosterDetailsInItem();
                    try {
                        rosterDetailsInItem.setId(array.getJSONArray(i).getJSONObject(0).getString("t1"));
                        rosterDetailsInItem.setCollege(array.getJSONArray(i).getJSONObject(1).getString("t2"));
                        rosterDetailsInItem.setProfessional(array.getJSONArray(i).getJSONObject(2).getString("t3"));
                        rosterDetailsInItem.setGrade(array.getJSONArray(i).getJSONObject(3).getString("t4"));
                        rosterDetailsInItem.setBj(array.getJSONArray(i).getJSONObject(4).getString("t5"));
                        rosterDetailsInItem.setStudentid(array.getJSONArray(i).getJSONObject(5).getString("t6"));
                        rosterDetailsInItem.setName(array.getJSONArray(i).getJSONObject(6).getString("t7"));
                        rosterDetailsInItem.setSex(array.getJSONArray(i).getJSONObject(7).getString("t8"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    list.add(rosterDetailsInItem);
                }
                rosterDetailsResultMutableLiveData.setValue(new RosterDetailsResult(null,list));
            }
        });
    }

    class RosterResult{
        public String error;
        public List<RosterInItem> list;

        public RosterResult(String error, List<RosterInItem> list) {
            this.error = error;
            this.list = list;
        }
    }

    class RosterDetailsResult{
        public String error;
        public List<RosterDetailsInItem> list;

        public RosterDetailsResult(String error, List<RosterDetailsInItem> list) {
            this.error = error;
            this.list = list;
        }
    }
}
