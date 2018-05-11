package com.ruixiangtong.xzc.share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xurj on 2016/7/3.
 */
public class ShareTypeAdapter extends BaseAdapter {
    private ArrayList<ShareTypeInfo> shareTypeList ;
    private ShareCommand command;
    private LayoutInflater inflater;
    public ShareTypeAdapter(ArrayList<ShareTypeInfo> shareTypeList, ShareCommand command, Context context){
        this.shareTypeList = shareTypeList;
        this.command = command;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(shareTypeList != null){
            return  shareTypeList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(shareTypeList != null && shareTypeList.size() > position){
            return shareTypeList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.share_type_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.shareTypeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command.shareByType(shareTypeList.get(position).getShareType());
            }
        });
        holder.showName.setText(shareTypeList.get(position).getShowName());
        holder.icon.setBackgroundResource(shareTypeList.get(position).getIconId());

        return convertView;
    }

    private class ViewHolder{
        public RelativeLayout shareTypeLayout;
        public ImageView icon;
        public TextView showName;

        public ViewHolder(View v){
            shareTypeLayout = (RelativeLayout)v.findViewById(R.id.shareType);
            icon = (ImageView)v.findViewById(R.id.icon);
            showName = (TextView)v.findViewById(R.id.showName);
        }
    }
}
