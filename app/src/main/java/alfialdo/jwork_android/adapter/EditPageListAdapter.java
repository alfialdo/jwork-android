package alfialdo.jwork_android.adapter;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import alfialdo.jwork_android.object.Bonus;
import alfialdo.jwork_android.object.Job;
import alfialdo.jwork_android.R;
import alfialdo.jwork_android.object.Recruiter;

/**
 * Acitivity untuk list adapter admin page
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class EditPageListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final ArrayList<String> listDataHeader;
    private final HashMap<String, ArrayList<Object>> listDataChild;

    public EditPageListAdapter(Context context, ArrayList<String> listDataHeader,
                           HashMap<String, ArrayList<Object>> listDataChild) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        Object childText;
        String temp;

        if(getChild(groupPosition, childPosition) instanceof Bonus) {
            childText = (Bonus) getChild(groupPosition, childPosition);
        } else {
            childText = (Recruiter) getChild(groupPosition, childPosition);
        }


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_sub_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        if(childText instanceof Bonus) {
            temp = "#"+((Bonus) childText).getReferralCode();
        } else {
            temp = ((Recruiter) childText).getName() + " - " +((Recruiter) childText).getEmail();
        }

        txtListChild.setText(temp);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}