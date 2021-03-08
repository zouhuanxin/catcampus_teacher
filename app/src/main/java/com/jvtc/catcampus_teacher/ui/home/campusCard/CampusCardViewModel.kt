package com.jvtc.catcampus_teacher.ui.home.campusCard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.google.gson.internal.LinkedTreeMap
import com.jvtc.catcampus_teacher.data.PageDataSourceFactory
import com.jvtc.catcampus_teacher.data.model.MealCard
import com.jvtc.catcampus_teacher.data.model.NetModel2
import com.jvtc.catcampus_teacher.http.OkHttps
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class CampusCardViewModel : AndroidViewModel {
    val CONTENT_LENGTH = 20
    val FIRST_PAGE = 0
    private var mPage = FIRST_PAGE
    private var mMoney: MutableLiveData<String>? = null
    private var mLiveData: LiveData<PagedList<LinkedTreeMap<*, *>>>? = null

    constructor(application: Application) : super(application) {
        if (mMoney == null) {
            mMoney = MutableLiveData<String>()
        }
    }

    private fun initPageList() {
        val positionalDataSource: PositionalDataSource<LinkedTreeMap<*, *>> = object : PositionalDataSource<LinkedTreeMap<*, *>>() {
            private fun computeCount(): Int {
                //这里的实际计数代码
                return CONTENT_LENGTH
            }

            /**
             * 网络请求耗时操作
             * @param observer 网络请求成功的回调
             * @param page 加载到第几页
             */
            private fun loadRangeInternal(observer: Observer<NetModel2<*>>, userId: String, page: Int) {
                val gitHubService = OkHttps.getData()
                //执行请求
                gitHubService.yktxf(userId, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer)
            }

            override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<LinkedTreeMap<*, *>>) {
                // 计算一页显示的条目
                val totalCount = computeCount()
                // 计算显示到第几条数据
                val position = computeInitialLoadPosition(params, totalCount)
                val loadSize = computeInitialLoadSize(params, position, totalCount)
                //初次初始化
                loadRangeInternal(object : Observer<NetModel2<*>> {
                    override fun onCompleted() {}
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onNext(o: NetModel2<*>) {
                        if (o.code == 200 || o.code == 201) {
                            mMoney!!.value = (o.data as LinkedTreeMap<String, *>).get("KYE").toString()
                            val array = (o.data as LinkedTreeMap<String, *>).get("detailList") as List<LinkedTreeMap<*, *>>
                            callback.onResult(array, position, totalCount)
                        }
                    }
                }, "11041", mPage)
            }

            override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<LinkedTreeMap<*, *>>) {
                mPage++
                loadRangeInternal(object : Observer<NetModel2<*>> {
                    override fun onCompleted() {}
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onNext(o: NetModel2<*>) {
                        if (o.code == 200 || o.code == 201) {
                            val array = (o.data as LinkedTreeMap<String, *>).get("detailList") as List<LinkedTreeMap<*, *>>
                            callback.onResult(array)
                        }
                    }
                }, "11041", mPage)
            }

        }
        // 构建LiveData
        LivePagedListBuilder(PageDataSourceFactory(positionalDataSource) as DataSource.Factory<*, *>, PagedList.Config.Builder().setPageSize(CONTENT_LENGTH)
                .setPrefetchDistance(CONTENT_LENGTH)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(CONTENT_LENGTH)
                .build()).build().also {
            mLiveData = it as LiveData<PagedList<LinkedTreeMap<*, *>>>
        }

    }

    fun getmLiveData(): LiveData<PagedList<LinkedTreeMap<*, *>>>? {
        initPageList()
        return mLiveData
    }

    fun getMoney(): MutableLiveData<String>? {
        return mMoney
    }

}