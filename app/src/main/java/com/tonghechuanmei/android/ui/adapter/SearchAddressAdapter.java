package com.tonghechuanmei.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.amap.api.services.core.PoiItem;
import com.tonghechuanmei.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shandirong on 2017/12/8.
 */

public class SearchAddressAdapter extends BaseAdapter {
    private Context mContext;
    private List<PoiItem> mList;

    public SearchAddressAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void setList(List<PoiItem> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_search_address, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PoiItem item = mList.get(position);
        viewHolder.mAddress.setText(item.getTitle());
        viewHolder.mName.setText(item.getSnippet());
        return convertView;
    }

    class ViewHolder {
        TextView mAddress;
        TextView mName;

        ViewHolder(View view) {
            mAddress = view.findViewById(R.id.item_address_address);
            mName = view.findViewById(R.id.item_address_name);
        }
    }
}
