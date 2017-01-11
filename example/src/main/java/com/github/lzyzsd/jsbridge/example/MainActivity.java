package com.github.lzyzsd.jsbridge.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.github.lzyzsd.jsbridge.example.Bean.Bean;
import com.github.lzyzsd.jsbridge.example.Bean.OrderPostBean;
import com.github.lzyzsd.jsbridge.example.tools.VolleyListenerInterface;
import com.github.lzyzsd.jsbridge.example.tools.VolleyRequestTool;
import com.google.gson.Gson;
import com.youjiesi.JuHuiWanJia.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements OnClickListener {

    private final String TAG = "MainActivity";

    BridgeWebView webView;

    Button button;

    int RESULT_CODE = 0;
    private String order_post = "http://jhwj.chaosou.me/api/order/order_post";
    ValueCallback<Uri> mUploadMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (BridgeWebView) findViewById(R.id.webView);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(this);

        button.setVisibility(View.GONE);

        webView.setDefaultHandler(new DefaultHandler());

        webView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                pickFile();
            }
        });

        webView.registerHandler("getOrderParam", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                String json = "{\"uid\":\"14\",\"goods_info\":{\"shop_id\":\"3\",\"shop_name\":\"史磊的店铺\",\"goods_info\":[{\"goods_id\":\"7\",\"title\":\"手机\",\"num\":\"1\",\"price\":\"1000\"}]}}";
                Log.i(TAG, "handl" +
                        "er = submitFromWeb, data from web = " + data);
                function.onCallBack(json);

            }
        });


        webView.registerHandler("addOrder", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler: addOrder" + data);
                Gson gson = new Gson();
                Bean bean = gson.fromJson(data, Bean.class);

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", bean.getUid());
                map.put("name", bean.getName());
                map.put("tel", bean.getTel());
                map.put("location", bean.getLocation());
                map.put("location_x", bean.getLocation_x());
                map.put("location_y", bean.getLocation_y());
                map.put("sex", String.valueOf(bean.getSex()));
                map.put("number", bean.getNumber());
                map.put("pay_name", bean.getPay_name());
                map.put("goods_price", bean.getGoods_price());
                map.put("shipping_price", bean.getShipping_price());
                map.put("use_score", bean.getUse_score());
                map.put("order_amount", bean.getOrder_amount());
                map.put("total_amount", bean.getTotal_amount());
                map.put("pay_type", bean.getPay_type());
                map.put("type", bean.getType());
                map.put("type_linshi", bean.getType_linshi());
                map.put("user_note", bean.getUser_note());
                map.put("shop_id", bean.getShop_id());
                map.put("goods_info", bean.getGoods_info());


                VolleyRequestTool.RequestPost(
                        MainActivity.this,
                        order_post, order_post,
                        map,
                        new VolleyListenerInterface(MainActivity.this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
                            @Override
                            public void onMySuccess(String result) {
                                Log.i(TAG, "onMySuccess: " + result);

                                try {
                                    OrderPostBean orderPostBean = new Gson().fromJson(result, OrderPostBean.class);
                                    int res = Integer.parseInt(orderPostBean.getResult());
                                    switch (res) {
                                        case 0:
                                            new AlertDialog.Builder(MainActivity.this)
                                                    .setMessage(orderPostBean.getMsg())
                                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    }).show();

                                            break;
                                        case 1:
                                            new AlertDialog.Builder(MainActivity.this)
                                                    .setMessage("用微信或支付宝支付")
                                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    }).show();


                                            break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    new AlertDialog.Builder(MainActivity.this)
                                            .setMessage("用微信或支付宝支付")
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                } finally {

                                }


                            }

                            @Override
                            public void onMyError(VolleyError error) {

                            }
                        });


            }
        });


        webView.loadUrl("http://139.129.60.7:8080/www/v-QfURzy-zh_CN-/All/order_post.w?device=m");


//
//		User user = new User();
//		Location location = new Location();
//		location.address = "SDU";
//		user.location = location;
//		user.name = "大头鬼";
//
//		webView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
//			@Override
//			public void onCallBack(String data) {
//
//			}
//		});
//
//		webView.send("hello");

    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (button.equals(v)) {
            webView.callHandler("functionInJs", "data from Java", new CallBackFunction() {

                @Override
                public void onCallBack(String data) {
                    // TODO Auto-generated method stub
                    Log.i(TAG, "reponse data from js " + data);
                }

            });
        }

    }

}