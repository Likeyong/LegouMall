package com.example.maxcion_home.jdmall.controls;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.maxcion_home.jdmall.bean.ProductSendArg;
import com.example.maxcion_home.jdmall.bean.RProductList;
import com.example.maxcion_home.jdmall.bean.RResult;
import com.example.maxcion_home.jdmall.bean.Rbrand;
import com.example.maxcion_home.jdmall.bean.RcategoryItem;
import com.example.maxcion_home.jdmall.bean.RsubCategory;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.util.NetWorkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maxcion_home on 2017/9/14.
 */

public class CategoryController extends BaseController {
    public CategoryController(Context mContext) {
        super(mContext);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action) {
            case IdiyMessage.TOPCATEGORY_ACTION:
                List<RcategoryItem> rcategoryItems = handleCategory();
                listener.onModeChanged(IdiyMessage.TOPCATEGORY_ACTION_RESULT, rcategoryItems);
                break;
            case IdiyMessage.SUBCATEGORY_ACTION:
                List<RsubCategory> rsubCategories = handleSubCategory((int) values[0]);
                listener.onModeChanged(IdiyMessage.SUBCATEGORY_ACTION_RESULT, rsubCategories);
                break;

            case IdiyMessage.BRAND_ACTION:
                List<Rbrand> rbrandList = handleBrand((int) values[0]);
                listener.onModeChanged(IdiyMessage.BRAND_ACTION_RESULT, rbrandList);
                break;

            case IdiyMessage.PRODUCT_LIST_ACTION:
                List<RProductList> rProductLists = handleProductList((ProductSendArg) values[0]);
                listener.onModeChanged(IdiyMessage.PRODUCT_LIST_ACTION_RESULT, rProductLists);
                break;
        }
    }

    private List<RProductList> handleProductList(ProductSendArg value) {
        HashMap<String, String> initProductSendArg = initProductSendArg(value);
        String jsonString = NetWorkUtil.doPost(NetWorkCons.PRODUCTLIST_URL, initProductSendArg);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            try {
                JSONObject jsonObject = new JSONObject(rResult.result);
                String rows = jsonObject.getString("rows");
                return JSON.parseArray(rows, RProductList.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<RProductList>();
    }

    private HashMap<String, String> initProductSendArg(ProductSendArg arg) {
        HashMap<String, String> map = new HashMap<>();
        map.put("categoryId", arg.categoryId + "");
        map.put("filterType", arg.filterType + "");
        if (arg.sortType != 0) {
            map.put("sortType", arg.sortType + "");
        }
        map.put("deliverChoose", arg.deliverChoose + "");
        if (arg.maxPrice != 0 && arg.minPrice != 0) {
            map.put("minPrice", arg.minPrice + "");
            map.put("maxPrice", arg.maxPrice + "");
        }
        if (arg.brandId != 0) {
            map.put("brandId", arg.brandId + "");

        }
        map.put("keyword", arg.keyword);
        return map;
    }

    private List<Rbrand> handleBrand(int categoryId) {
        String jsonString = NetWorkUtil.doGet(NetWorkCons.BRAND_URL + "?categoryId=" + categoryId);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            return JSON.parseArray(rResult.result, Rbrand.class);
        }
        return null;
    }

    private List<RsubCategory> handleSubCategory(int value) {
        String jsonString = NetWorkUtil.doGet(NetWorkCons.CATEGORY_URL + "?parentId=" + value);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            return JSON.parseArray(rResult.result, RsubCategory.class);
        }
        return new ArrayList<RsubCategory>();
    }

    private List<RcategoryItem> handleCategory() {
        List<RcategoryItem> categorys = new ArrayList<>();
        String jsonString = NetWorkUtil.doGet(NetWorkCons.CATEGORY_URL);
        RResult rResult = JSON.parseObject(jsonString, RResult.class);
        if (rResult.success) {
            return JSON.parseArray(rResult.result, RcategoryItem.class);
        } else {
            return categorys;
        }
    }
}
