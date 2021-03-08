package com.jvtc.catcampus_teacher.ui.home.campusCard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.internal.LinkedTreeMap
import com.jvtc.catcampus_teacher.R
import com.jvtc.catcampus_teacher.data.model.MealCard
import com.jvtc.catcampus_teacher.data.model.NetModel
import com.jvtc.catcampus_teacher.util.NavigationManager
import kotlinx.android.synthetic.main.activity_campus_card.*
import kotlinx.android.synthetic.main.layout_head.*

class CampusCardActivity : AppCompatActivity() {
    private var campusCardViewModel: CampusCardViewModel? = null
    private var adapter: CampusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationManager.setStatusBarColor(this)
        setContentView(R.layout.activity_campus_card)
        initView()
        initData()
    }

    fun initView() {
        head_lefticon.setImageResource(R.mipmap.arrowleft_back)
        head_lefticon.setOnClickListener(View.OnClickListener {
            finish()
        })
        head_text.text = "校园卡流水"
    }

    fun initData() {
        initAdater()
        campusCardViewModel = ViewModelProvider(this).get(CampusCardViewModel::class.java)
        campusCardViewModel!!.getmLiveData()!!.observe(this, Observer<PagedList<LinkedTreeMap<*,*>>> {
            adapter!!.submitList(it)
        })
        campusCardViewModel!!.getMoney()!!.observe(this, Observer<String> {
            balance.text = "余额:"+it + "元"
        })
    }

    fun initAdater(){
        adapter = CampusAdapter(object : DiffUtil.ItemCallback<LinkedTreeMap<*, *>>() {
            override fun areItemsTheSame(oldItem: LinkedTreeMap<*, *>, newItem: LinkedTreeMap<*, *>): Boolean {
                println("areItemsTheSame")
                return oldItem.get("JYSJ").toString().equals(newItem.get("JYSJ").toString())
            }

            override fun areContentsTheSame(oldItem: LinkedTreeMap<*, *>, newItem: LinkedTreeMap<*, *>): Boolean {
                println("areContentsTheSame")
                return oldItem.equals(newItem)
            }

        })
        card_details.adapter = adapter
        card_details.layoutManager = LinearLayoutManager(this)
    }
}