package com.hl.htk_customer.greendao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl.htk_customer.entity.ProductEntity;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/9/20.
 */

@Entity
public class FunctionTypeEntity{

    @Convert(converter = FunctionTypeConverter.class , columnType = String.class)
    public ArrayList<ProductEntity.DataBean> functionList;

    @Generated(hash = 1120735025)
    public FunctionTypeEntity(ArrayList<ProductEntity.DataBean> functionList) {
        this.functionList = functionList;
    }

    @Generated(hash = 700125366)
    public FunctionTypeEntity() {
    }

    public ArrayList<ProductEntity.DataBean> getFunctionList() {
        return this.functionList;
    }

    public void setFunctionList(ArrayList<ProductEntity.DataBean> functionList) {
        this.functionList = functionList;
    }

    public static class FunctionTypeConverter implements PropertyConverter<ArrayList<ProductEntity.DataBean> , String >{

        private final Gson mGson;

        public FunctionTypeConverter() {
            mGson = new Gson();
        }

        @Override
        public ArrayList<ProductEntity.DataBean> convertToEntityProperty(String databaseValue) {
            if (databaseValue == null){
                return null;
            }
            ArrayList<ProductEntity.DataBean> list =
                    mGson.fromJson(databaseValue, new TypeToken<ArrayList<ProductEntity.DataBean>>() {
            }.getType());
            return list;
        }

        @Override
        public String convertToDatabaseValue(ArrayList<ProductEntity.DataBean> entityProperty) {
            if (entityProperty == null)
                return null;
            return mGson.toJson(entityProperty);
        }
    }

}
