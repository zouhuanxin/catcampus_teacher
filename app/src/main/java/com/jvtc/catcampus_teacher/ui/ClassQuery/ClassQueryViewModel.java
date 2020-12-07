package com.jvtc.catcampus_teacher.ui.ClassQuery;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jvtc.catcampus_teacher.data.ClassQueryRepsitory;
import com.jvtc.catcampus_teacher.data.Result;
import com.jvtc.catcampus_teacher.data.model.ClassQueryInItem;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ClassQueryViewModel extends ViewModel {
    private MutableLiveData<List<String>> xnxqhlivedata;
    private MutableLiveData<List<String>> skyxlivedata;
    private MutableLiveData<List<String>> xqidlivedata;
    private MutableLiveData<List<String>> jzwidlivedata;
    private Map skyxmap, xqidmap, jzwidmap;
    private Iterator iter;
    private MutableLiveData<ClassQueryRestult> expandlivedata;
    private MutableLiveData<String> titlelivedata;

    public ClassQueryViewModel() {
        List<String> xnxqhlist = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR) - 1;
        for (int i = 0; i < 3; i++) {
            xnxqhlist.add((year + i) + "-" + (year + i + 1) + "-1");
            xnxqhlist.add((year + i) + "-" + (year + i + 1) + "-2");
        }
        xnxqhlivedata = new MutableLiveData<>();
        xnxqhlivedata.setValue(xnxqhlist);
        //学院数据
        skyxmap = new HashMap<>();
        skyxmap.put("所有", "");
        skyxmap.put("教务处", "00");
        skyxmap.put("机械工程学院", "01");
        skyxmap.put("电气工程学院", "02");
        skyxmap.put("船舶工程学院", "03");
        skyxmap.put("汽车工程学院", "04");
        skyxmap.put("信息工程学院", "05");
        skyxmap.put("建筑工程学院", "06");
        skyxmap.put("财会金融学院", "07");
        skyxmap.put("经济管理学院", "08");
        skyxmap.put("继续教育学院", "09");
        skyxmap.put("马克思主义学院", "23");
        skyxmap.put("创新创业学院", "24");
        skyxmap.put("工程训练中心", "25");
        skyxmap.put("学生工作处", "27");
        List<String> skyxlist = new ArrayList<>();
        iter = skyxmap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            skyxlist.add(key.toString());
        }
        skyxlivedata = new MutableLiveData<>();
        skyxlivedata.setValue(skyxlist);
        //校区数据
        xqidmap = new HashMap<>();
        xqidmap.put("所有", "");
        xqidmap.put("十里校区", "1");
        xqidmap.put("继教部", "2");
        xqidmap.put("濂溪校区", "3");
        List<String> xqidlist = new ArrayList<>();
        iter = xqidmap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            xqidlist.add(key.toString());
        }
        xqidlivedata = new MutableLiveData<>();
        xqidlivedata.setValue(xqidlist);
        //楼栋数据
        jzwidmap = new HashMap<>();
        jzwidmap.put("所有", "");
        jzwidmap.put("濂溪实训楼", "301");
        jzwidmap.put("濂溪教学楼", "303");
        jzwidmap.put("濂溪综合楼B区", "302");
        jzwidmap.put("濂溪图书馆", "304");
        jzwidmap.put("十里一号教学楼", "101");
        jzwidmap.put("十里二号教学楼", "102");
        jzwidmap.put("十里三号教学楼", "103");
        jzwidmap.put("十里四号教学楼", "104");
        jzwidmap.put("十里一号实验楼", "105");
        jzwidmap.put("十里二号实验楼", "106");
        jzwidmap.put("十里工业工程中心", "107");
        jzwidmap.put("十里仪表厂厂房", "108");
        jzwidmap.put("十里工业训练中心", "109");
        jzwidmap.put("十里老综合楼", "110");
        jzwidmap.put("十里焊接实训中心", "111");
        jzwidmap.put("十里汽车专业实训楼", "112");
        List<String> jzwidlist = new ArrayList<>();
        iter = jzwidmap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            jzwidlist.add(key.toString());
        }
        jzwidlivedata = new MutableLiveData<>();
        jzwidlivedata.setValue(jzwidlist);

        expandlivedata = new MutableLiveData<>();
        //标题
        String title = "教室课表查询";
        titlelivedata = new MutableLiveData<>();
        titlelivedata.setValue(title);
    }

    public MutableLiveData<List<String>> getXnxqhlivedata() {
        return xnxqhlivedata;
    }

    public MutableLiveData<List<String>> getSkyxlivedata() {
        return skyxlivedata;
    }

    public MutableLiveData<List<String>> getXqidlivedata() {
        return xqidlivedata;
    }

    public MutableLiveData<List<String>> getJzwidlivedata() {
        return jzwidlivedata;
    }

    public MutableLiveData<ClassQueryRestult> getExpandlivedata() {
        return expandlivedata;
    }

    public MutableLiveData<String> getTitlelivedata() {
        return titlelivedata;
    }

    public Map getSkyxmap() {
        return skyxmap;
    }

    public Map getXqidmap() {
        return xqidmap;
    }

    public Map getJzwidmap() {
        return jzwidmap;
    }

    public void QueryData(String xnxqh, String skyx, String xqid, String jzwid, String skjs) {
        ClassQueryRepsitory.getInstance().getData(xnxqh, skyx, xqid, jzwid, skjs, new RxHttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Result.Error data) {
                if (data.getError() == null) {
                    expandlivedata.setValue(new ClassQueryRestult("请检查网络链接", null));
                    return;
                }
                expandlivedata.setValue(new ClassQueryRestult(data.getMessage(), null));
            }

            @Override
            public void onSuccess(Result.Success data) {
                List<ClassQueryInItem> list = new ArrayList<>();
                JSONArray array = (JSONArray) data.getData();
                for (int i = 0; i < array.length(); i++) {
                    ClassQueryInItem inItem = new ClassQueryInItem();
                    try {
                        List<ClassQueryInItem.Details> detailsList = new ArrayList<>();
                        JSONArray array2 = array.getJSONObject(i).getJSONArray("details");
                        for (int j = 0; j < array2.length(); j++) {
                            ClassQueryInItem.Details details = new ClassQueryInItem.Details(
                                    array2.getJSONObject(j).getString("node"),
                                    array2.getJSONObject(j).getString("text")
                            );
                            detailsList.add(details);
                        }
                        inItem.setWeek(array.getJSONObject(i).getString("week"));
                        inItem.setDetails(detailsList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    list.add(inItem);
                }
                titlelivedata.setValue(list.get(0).getDetails().get(0).getText());
                //移除标题栏
                list.remove(0);
                expandlivedata.setValue(new ClassQueryRestult(null, list));
            }
        });
    }

    class ClassQueryRestult {
        public String error;
        public List<ClassQueryInItem> classQueryInItemList;

        public ClassQueryRestult(String error, List<ClassQueryInItem> classQueryInItemList) {
            this.error = error;
            this.classQueryInItemList = classQueryInItemList;
        }
    }
}
