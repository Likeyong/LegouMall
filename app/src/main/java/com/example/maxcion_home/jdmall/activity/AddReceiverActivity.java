package com.example.maxcion_home.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxcion_home.jdmall.JDApplication;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.Raddress;
import com.example.maxcion_home.jdmall.bean.Rarea;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.controls.ShopcarController;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddReceiverActivity extends BaseActivity {

    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.choose_province_tv)
    TextView chooseProvinceTv;
    @BindView(R.id.address_details_et)
    EditText addressDetailsEt;
    @BindView(R.id.default_cbx)
    CheckBox defaultCbx;
    @BindView(R.id.parent_view)
    LinearLayout parentView;
    private ArrayList<Rarea> mProvinces;
    private ArrayList<Rarea> mCity;
    private String[] citys;
    private String[] provinces;
    private ListView cityListView;
    private ArrayList<Rarea> mArea;
    private String[] areas;
    private ListView areaListView;
    private ArrayAdapter<String> mAreaAdapter;
    private String provinceName;
    private String cityName;
    private String areaName;
    private AlertDialog dialog;
    private Rarea mRpeovince;
    private Rarea mRcity;
    private Rarea mRarea;

    public static final int ADD_RECEIVER_ADDRESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receiver);
        ButterKnife.bind(this);
        initController();
        mController.sendAsyncMessage(IdiyMessage.PROVINCE_ACTION);
    }

    private void initController() {
        mController = new ShopcarController(this);
        mController.setIModeChangeListener(this);
    }


    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.PROVINCE_ACTION_RESULT:
                handleProvinceResult(msg.obj);
                break;
            case IdiyMessage.CITY_ACTION_RESULT:
                handleCityResult(msg.obj);
                break;
            case IdiyMessage.AREA_ACTION_RESULT:
                handleAreaResult(msg.obj);
                break;
        }
    }

    private void handleAreaResult(Object obj) {
        mArea = (ArrayList<Rarea>) obj;
        areas = list2Array(mArea);
        mAreaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                areas);
        areaListView.setAdapter(mAreaAdapter);
    }

    private void handleCityResult(Object obj) {
        mCity = (ArrayList<Rarea>) obj;
        citys = list2Array(mCity);
        cityListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                citys));
    }

    private void handleProvinceResult(Object obj) {
        mProvinces = (ArrayList<Rarea>) obj;
        provinces = list2Array(mProvinces);

    }

    private String[] list2Array(ArrayList<Rarea> rareas) {
        String[] strings = new String[rareas.size()];
        for (int i = 0; i < rareas.size(); i++) {
            strings[i] = rareas.get(i).name;
        }
        return strings;
    }

    @OnClick(R.id.choose_province_tv)
    public void onViewClicked() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_are_choice, null);
        cityListView = (ListView) view.findViewById(R.id.city_lv);
        areaListView = (ListView) view.findViewById(R.id.are_lv);
        ListView proListView = (ListView) view.findViewById(R.id.province_lv);
        builder.setView(view);
        dialog = builder.show();
        view.findViewById(R.id.bt_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provinceName = "";
                cityName = "";
                areaName = "";
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.bt_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAndSaveAddress();
            }
        });


        proListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                provinces));

        proListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provinceName = provinces[position];
                mRpeovince = mProvinces.get(position);
                mController.sendAsyncMessage(IdiyMessage.CITY_ACTION, mProvinces.get(position).code);
                handleAreaResult(new ArrayList<>());
            }
        });


        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityName = citys[position];
                mRcity = mCity.get(position);
                mController.sendAsyncMessage(IdiyMessage.AREA_ACTION, mCity.get(position).code);
            }
        });

        areaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaName = areas[position];
                mRarea = mArea.get(position);
                closeAndSaveAddress();
            }
        });


    }

    private void closeAndSaveAddress() {
        chooseProvinceTv.setText(provinceName + cityName + areaName);
        dialog.dismiss();
    }


    public void saveAddress(View v){
        String name = nameEt.getText().toString();
        String phone = phoneEt.getText().toString();
        String address = addressDetailsEt.getText().toString();
        String provinceAndCity = chooseProvinceTv.getText().toString();
        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(address)||
                TextUtils.isEmpty(provinceAndCity)){
            Toast.makeText(this,"请输入完整信息",Toast.LENGTH_SHORT).show();
            return;
        }
        JDApplication application = (JDApplication) getApplication();

        Raddress raddress = new Raddress();
        raddress.addressDetails=address;
        raddress.cityCode=mRcity.code;
        raddress.provinceCode= mRpeovince.code;
        raddress.distCode = mRarea.code;
        raddress.name = name;
        raddress.phone = phone;
        raddress.userId = application.mRLoginResult.id;
        raddress.isDefault=defaultCbx.isChecked();
        Intent intent = new Intent();
        intent.putExtra("ADDRESS_BEAN",raddress);
        setResult(RESULT_OK,intent);
        finish();

    }
}
