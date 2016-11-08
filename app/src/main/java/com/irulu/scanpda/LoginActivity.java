package com.irulu.scanpda;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.irulu.scanpda.Data.API;
import com.irulu.scanpda.Data.NetWorkDataIO.ServerData;
import com.irulu.scanpda.Data.LocalDateIO.SharedPreferencesIO;
import com.irulu.scanpda.Data.UniqueKey;
import com.irulu.scanpda.Tools.LoginKey;
import com.irulu.scanpda.Tools.UrlParameter;
import com.irulu.scanpda.Model.JsonModel.UserInfo;
import com.irulu.scanpda.Model.JsonModel.Version;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dtw on 16/10/14.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    RadioGroup radioGroup_location;
    EditText edittext_username;
    EditText editText_password;
    Button button_login, button_exit, button_help;
    TextView textViewVersion;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewID(R.layout.activity_login);
        setTitle(R.string.login_text);

        //初始化界面元素
        radioGroup_location = (RadioGroup) findViewById(R.id.radio_location);
        edittext_username = (EditText) findViewById(R.id.edittext_username);
        editText_password = (EditText) findViewById(R.id.edittext_password);
        button_login = (Button) findViewById(R.id.btn_login);
        button_exit = (Button) findViewById(R.id.btn_exit);
        button_help = (Button) findViewById(R.id.btn_exit);
        textViewVersion = (TextView) findViewById(R.id.textView_version);

        button_login.setOnClickListener(this);
        button_exit.setOnClickListener(this);
        button_help.setOnClickListener(this);

        //测试数据
//        SharedPreferencesIO sharedPreferencesIO = new SharedPreferencesIO(this);
//        sharedPreferencesIO.addUser("hello");
//        sharedPreferencesIO.addUser("nihao");
//        sharedPreferencesIO.addUser("nihao");
//        sharedPreferencesIO.addUser("love");
        //。。。。。

        //选择服务器位置
        radioGroup_location.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == radioGroup.getChildAt(0).getId()) {
                    API.getInstance().setHostLocation(0);
                } else if (i == radioGroup.getChildAt(1).getId()) {
                    API.getInstance().setHostLocation(1);
                } else if (i == radioGroup.getChildAt(2).getId()) {
                    API.getInstance().setHostLocation(2);
                }
            }
        });

        //设置默认location
        //test
        radioGroup_location.check(radioGroup_location.getChildAt(2).getId());


        //选择已保存的用户名
        edittext_username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP && edittext_username.getCompoundDrawables()[2] != null &&
                        motionEvent.getX() >= edittext_username.getWidth() - edittext_username.getPaddingLeft() - edittext_username.getPaddingRight() - edittext_username.getCompoundDrawables()[2].getBounds().width() - 12
                        ) {
                    final String[] stringsUserName = new SharedPreferencesIO(LoginActivity.this).getUserName().toArray(new String[0]);
                    final ListPopupWindow listPopupWindow = new ListPopupWindow(LoginActivity.this);
                    listPopupWindow.setAdapter(new ArrayAdapter(LoginActivity.this, android.R.layout.simple_list_item_1, stringsUserName));
                    listPopupWindow.setAnchorView(edittext_username);
                    listPopupWindow.setModal(true);
                    listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            edittext_username.setText(stringsUserName[i]);
                            editText_password.requestFocus();
                            listPopupWindow.dismiss();
                        }
                    });
                    listPopupWindow.show();
                    return true;
                }
                return false;
            }

        });

        //回车键登陆
        editText_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE||event.getKeyCode()== KeyEvent.KEYCODE_ENTER){
                    if(button_login.isClickable()) {
                        button_login.callOnClick();
                    }
                    return true;
                }
                return false;
            }
        });

        //获取版本信息 并显示
        ServerData.getJsonModel(API.getInstance().getUrlVersion(), Version.class, new ServerData.CallBackScan<Version>() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(final Version model) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewVersion.setText("V" + model.getVersion());
                    }
                });
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                showProgressBar();
                button_login.setClickable(false);
                Map<String, String> loginfo = new HashMap<>();
                loginfo.put("warehCode", "z");
                loginfo.put("user", edittext_username.getText().toString());
                loginfo.put("pwd", editText_password.getText().toString());
                try {
                    loginfo.put("key", URLEncoder.encode(LoginKey.get(editText_password.getText().toString()), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String loginUrl = UrlParameter.get(loginfo);
                loginUrl = API.getInstance().getUrlLogin() + loginUrl;

                ServerData.getJsonModel(loginUrl, UserInfo.class, new ServerData.CallBackScan<UserInfo>() {

                    @Override
                    public void onFailure(IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                                button_login.setClickable(true);
                                hideProgressBar();
                            }
                        });
                    }

                    @Override
                    public void onResponse(final UserInfo model) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (model.getSuccess() == 1) {
                                    new SharedPreferencesIO(LoginActivity.this).addUser(model.getAdminName());
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    intent.setClass(LoginActivity.this, MainActivity.class);
                                    bundle.putParcelable(UniqueKey.getUserInfoKey(), model);
                                    intent.putExtra(UniqueKey.getUserInfoKey(), bundle);
                                    startActivity(intent);
                                    editText_password.setText("");
                                    button_login.setClickable(true);
                                    hideProgressBar();
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.login_data_error, Toast.LENGTH_SHORT).show();
                                    button_login.setClickable(true);
                                    hideProgressBar();
                                }
                            }
                        });
                    }
                });
                break;
            case R.id.btn_exit:
                finish();
                break;
            case R.id.btn_help:
                break;

        }
    }

    @Override
    public void onBackPressed() {
    }
}
