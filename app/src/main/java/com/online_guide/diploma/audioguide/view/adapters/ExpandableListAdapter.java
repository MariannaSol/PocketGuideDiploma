package com.online_guide.diploma.audioguide.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.online_guide.diploma.audioguide.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<String>> mGroups;

    private Context mContext;

    private Intent intent;

    List<String> mRegions;


    public ExpandableListAdapter(Context mContext, ArrayList<ArrayList<String>> mGroups, List<String> mRegions) {
        this.mContext = mContext;
        this.mGroups = mGroups;
        this.mRegions = mRegions;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        // textGroup.setText("Group " + Integer.toString(groupPosition));

        textGroup.setText(mRegions.get(groupPosition));

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        textChild.setText(mGroups.get(groupPosition).get(childPosition));

//        Button button = (Button)convertView.findViewById(R.id.buttonChild);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             //   Toast.makeText(MenuActivity.class.this, city.toString(), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(mContext, MapActivity.class);
//                intent.putExtra(CITY_NAME, city.getCityName());
//                intent.putExtra(COORD_X, city.getCoordX());
//                intent.putExtra(COORD_Y, city.getCoordY());
//                intent.putExtra(INFO, city.getInfo());
//                mContext.startActivity(intent);
//            }
//        });

        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

}
