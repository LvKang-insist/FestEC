package com.diabin.latte.ec.main.cart;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.diabin.latte.deleggate.bottom.BottomItemDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ec.main.cart.pay.AuthResult;
import com.diabin.latte.ec.main.cart.pay.OrderInfoUtil2_0;
import com.diabin.latte.ec.main.cart.pay.PayResult;
import com.diabin.latte.ec.pay.FastPay;
import com.diabin.latte.ec.pay.IAIPayResultListener;
import com.diabin.latte.net.RestClient;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.util.dimen.SetToolBar;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C)
 *
 * @file: ShopCartDelegate
 * @author: 345
 * @Time: 2019/5/5 20:23
 * @description: ${DESCRIPTION}
 */
public class ShopCartDelegate extends BottomItemDelegate
        implements ICartItemListener, ISuccess, IAIPayResultListener {
    private ShopCartAdapter mAdapter = null;
    /**
     * 总价钱
     */
    private int mTotalCount = 0;

    private boolean stub_flag = false;

    @BindView(R2.id.shop_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTotalPrice = null;

    /**
     * 全选
     */
    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll() {
        final int tag = (int) mIconSelectAll.getTag();
        //tag 为0 表示选中状态，
        if (tag == 0) {
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            mIconSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新RecyclerView 的显示状态
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
        mAdapter.createPrice();
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        //要删除的数据
        List<MultipleItemEntity> deleteEntity = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                deleteEntity.add(entity);
            }
        }
        for (MultipleItemEntity entity : deleteEntity) {
            int removePosition;
            //拿到要删除条目的 position
            final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
            if (entityPosition <= mAdapter.getItemCount()) {
                mAdapter.remove(entityPosition);
                //更新数据
                mAdapter.notifyItemRangeChanged(entityPosition, mAdapter.getItemCount());
                final int size = mAdapter.getData().size();
                //重新设置 position，让他和 item 的下标对应
                for (int i = 0; i < size; i++) {
                    data.get(i).setField(ShopCartItemFields.POSITION, i);
                }
            }
        }
        // 更新价格
        mAdapter.createPrice();
        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        //更新价格
        mAdapter.createPrice();
        checkItemCount();
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPlay() {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        FastPay.create(this).beginPayDialog(this);
        createOrder();
    }

    final int SDK_PAY_FLAG = 1;
    final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressLint("NewApi")
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(getProxyActivity(), getString(R.string.pay_success) + payResult, null);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(getProxyActivity(), getString(R.string.pay_failed) + payResult, null);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(getProxyActivity(), getString(R.string.auth_success) + authResult, null);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(getProxyActivity(), getString(R.string.auth_failed) + authResult, null);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void payV2() {
        /*
         * 用于支付宝支付业务的入参 app_id。
         */
        final String APPID = "2016092800617815";

        /*
         * 用于支付宝账户登录授权业务的入参 pid。
         */
        final String PID = "";

        /*
         * 用于支付宝账户登录授权业务的入参 target_id。
         */
        final String TARGET_ID = "";
        final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRgYDJvJLgc55g01lzmYwFb3qCFII0yQkteXs2R0TEDqAVxmawB1anf1jB3Rc9RIEaV0v4dN5419m8Vz3W66WUnXcXggp+D1MUC7dwDCtLkRKVFQwqF8DjPLQsHwJZ5KmqK2nWNNf6RZadx3XQZn61b/R6yXcMFX07pP09LqFAoLaKxSwervtl3Wwht5MKCMuOtkYsnJiCbbxMhNaIuKTO8rAk3A3sa3vZEVxuBkHi0hlNZygg7D3EyP8OPJu8+LTsCduE3VVXcPLX7fzbe82j+HA+HbGYWsFHuq0C6Fw9dYX0xIFgfmYyHkhdgpY9a4KD9NwgdDxkUQkoMjeXOVvVAgMBAAECggEAYU4chUyhYI6RRKkyMddmRyBE6gazCH/Erm+GEUdZ2k75iXZTp7ZQodTM02B6MRPFzorAmFf3ixK2XnhtN3QBGECxqrvhLmorIcWwF7NbpL5QkfgwWVnnOU+YIcVe+iSrvcwojAJsyMaOsdb4l7RjWgyTjyV5glBAnol4fNjZJ3EP4bQOGu0v/HdIj3GokjvuPk4AxVe7OJVGCAZhKB6nkBIih7yCVb7ofk1BsH84jYKKInXru6hWMbfRAaOybQjNyk0bCH0BFqLXxKMh8Up0QJh+0Cptx1d7o7JF2MXS0IzDe0YKJ+PNS6rl0+oS0m68euaIveWoRazX+N90x4r0JQKBgQDNPYkvZrFlTXJerZ/FbgJ6suVp7sfOlNt3KAuuxoWgzyOI0LJVoxrulLq0JhMtwqxbsA67cPhv6pIcK6TJeu0URwpaRR41oTN3yZnsz5KtfhY2eolPWvnAWU0PASDs2leaBRe9hzF06dr7/avHvW2iDqWXpS1Hn9x3grdB74FW0wKBgQC1ffkl5h9LzEn2WPWmXogvZjDz3fCi1lOYyTVxk3ud+resrtQ7973ZmQxdJFeoNMKi0MDGic8rVvO58px4cjFSS+1QR3llDGRDeANJs7f7F2kqW2U++mQfd8QKJ9GK7o2rH2TdJDOsMMBKSMpd1TvyAdX9ZensVLIr9yGXkOGptwKBgQDINf4sUlY8xxcJiv+vvYgHn5oyA2/Acu/ARj1XSmuymj5Eyakd5xVvpYCYGghdfGlcq9tDOInxyvarKlv++75oKIDN3F0C3fTyJqTUIQ0p1zTeAh0OX2YBIFwOhgBTbQ9FvTASf95b+YDYjvXSzFQ1YWpiCwynsSeFIeuZE4WtZwKBgHLHi5UbVzmRpkkE875W1JvdbiIcf34MO+xJe+IrWqeLkzPDkXenc0q1Tytsu38mdt2PVgt5xiproFI+DBTkayrX1+rIeQ6d6p78vF+VKErFAH6INJzCsCVykem2SwApZDEP9rMgnQlN/547MLhtDa881/+cv3b2DYRek8EOIJk7AoGBAJd/uYR0+E8DJIiA5HOCa4MOm5CvcGbgtUIyiidtlnRDzetr9e6ol6WURypFsIZmOq/rTS+M96Sxx97QtMln+F2/M5p5we/I2VLKQIH0TBUaLu0iSOriezyFw5ZZvVWlvHZjLBYkSgkSiekBQhGfAukmZ6RaStxoRbz0gFQ/iDzC";
        final String RSA_PRIVATE = "";

        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(getProxyActivity(), getString(R.string.error_missing_appid_rsa_private), null);
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getProxyActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    /**
     * 创建订单，注意，和支付是没有关系的
     */
    private void createOrder() {
        final String orderUrl = "";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        orderParams.put("userid", "");
        orderParams.put("amoutn", 0.01);
        orderParams.put("type", 1);
        orderParams.put("ordertype", 1);
        orderParams.put("isanonymous", true);
        orderParams.put("followeduser", 0);

        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体支付
                        final int orderId = JSON.parseObject(response).getInteger(response);
                        FastPay.create(ShopCartDelegate.this)
                                .setPayResultListener(ShopCartDelegate.this)
                                .setOrderId(orderId)
                                .beginPayDialog(ShopCartDelegate.this);
                    }
                })
                .build()
                .post();
    }

    private void checkItemCount() {
        if (stub_flag) {
            return;
        }
        stub_flag = true;
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            //ViewStub 的inflate 方法就是将自己冲父view中移除，然后就会加载layout指定的布局了
            //注意在ViewStub 控件中，要使用layout 属性指定 待加载的布局
            @SuppressLint("RestrictedApi") View stubView = mStubNoItem.inflate();
            final AppCompatTextView tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "购物了", Toast.LENGTH_SHORT).show();
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shopcart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);
        mIconSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart_data.json")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data = new ShopCartDataConverter()
                .setJsonData(response)
                .convert();
        mAdapter = new ShopCartAdapter(data);
        //设置监听价格的回调
        mAdapter.setCartItemListener(ShopCartDelegate.this);
        mAdapter.createPrice();
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        checkItemCount();
    }

    @Override
    public void onItemClick(double totalPrice) {
        mTotalPrice.setText(String.valueOf(totalPrice));
    }


    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
