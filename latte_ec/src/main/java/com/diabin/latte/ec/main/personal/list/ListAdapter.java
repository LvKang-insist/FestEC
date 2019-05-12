package com.diabin.latte.ec.main.personal.list;

import android.support.v7.widget.SwitchCompat;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.diabin.latte.ec.R;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.List;

import okhttp3.internal.http.HttpHeaders;

/**
 * Copyright (C)
 *
 * @file: ListAdapter
 * @author: 345
 * @Time: 2019/5/8 10:35
 * @description: ${DESCRIPTION}
 */
public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();

    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.ITEM_NORMAL, R.layout.arrow_item_layout);
        addItemType(ListItemType.ITEM_AVATAR, R.layout.arrow_item_avatar);
        addItemType(ListItemType.ITEM_SWTCH,R.layout.arrow_switch_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (helper.getItemViewType()) {
            case ListItemType.ITEM_NORMAL:
                helper.setText(R.id.tv_arrow_text, item.getText());
                helper.setText(R.id.tv_arrow_value, item.getValue());
                break;
            case ListItemType.ITEM_AVATAR:
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .apply(OPTIONS)
                        .into((ImageView) helper.getView(R.id.img_arrow_avatar));
                break;
                case ListItemType.ITEM_SWTCH:
                    helper.setText(R.id.tv_arrow_switch_text,item.getText());
                    final SwitchCompat switchCompat = helper.getView(R.id.list_item_switch);
                    switchCompat.setChecked(true);
                    switchCompat.setOnCheckedChangeListener(item.getmOnCheckedChangeListener());
                    break;
            default:
                break;
        }
    }

}
