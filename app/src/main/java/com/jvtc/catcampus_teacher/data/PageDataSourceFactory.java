package com.jvtc.catcampus_teacher.data;

import androidx.paging.DataSource;
import androidx.paging.PositionalDataSource;

import com.google.gson.internal.LinkedTreeMap;
import com.jvtc.catcampus_teacher.data.model.MealCard;
import com.jvtc.catcampus_teacher.data.model.NetModel;

public class PageDataSourceFactory extends DataSource.Factory {

    public PositionalDataSource<LinkedTreeMap> mPositionalDataSource;

    public PageDataSourceFactory(PositionalDataSource<LinkedTreeMap> positionalDataSource) {
        this.mPositionalDataSource = positionalDataSource;
    }

    @Override
    public DataSource create() {
        return mPositionalDataSource;
    }
}