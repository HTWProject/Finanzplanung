package com.htw.finanzplanung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Mitglied> _listDataHeader; // header titles
    private String startDatum, endDatum;
    private Integer gruppenID;

    private Data_Access dataAccess;

    // child data in format of header title, child title
    private HashMap<Mitglied, ArrayList<Geldausgabe>> _listDataChild;

    public ExpandableListAdapter(Context context, List<Mitglied> listDataHeader,HashMap<Mitglied, ArrayList<Geldausgabe>> listChildData, String startDatum, String endDatum, Integer gruppenID) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.startDatum = startDatum;
        this.endDatum = endDatum;
        this.dataAccess = new Data_Access(context);
        this.gruppenID = gruppenID;
    }

    @Override
    public Geldausgabe getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childTextDatum = getChild(groupPosition, childPosition).getDatum();
        final String childTextWas = getChild(groupPosition, childPosition).getWas();
        final String childTextBetrag = String.valueOf(getChild(groupPosition, childPosition).getGeldbetrag());

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChildDatum = (TextView) convertView.findViewById(R.id.txt_listDatum);
        TextView txtListChildWas = (TextView) convertView.findViewById(R.id.txt_listWas);
        TextView txtListChildBetrag = (TextView) convertView.findViewById(R.id.txt_listBetrag);

        txtListChildDatum.setText(childTextDatum);
        txtListChildWas.setText(childTextWas);
        txtListChildBetrag.setText(childTextBetrag + " €");
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Mitglied getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition).getName();
        String userGesamtbetrag = String.valueOf(dataAccess.getUserGesamtbetrag(startDatum,endDatum,gruppenID,getGroup(groupPosition).getDbId()));
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeaderName = (TextView) convertView.findViewById(R.id.txt_ListUserName);
        TextView lblListHeaderBetrag = (TextView) convertView.findViewById(R.id.txt_LiestUserGesamtbetrag);
        //lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeaderName.setText(headerTitle);
        lblListHeaderBetrag.setText(userGesamtbetrag + " €");
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