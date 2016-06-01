package tfg.accelbikeapp.GUI.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import tfg.accelbikeapp.R;

/**
 * Created by David on 30/05/2016.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private HashMap<String,List<String>> Categoria_Mediciones;
    private List<String> Lista_mediciones;

    //Constructor Expandable
    public ExpandableAdapter(Context ctx, HashMap<String,List<String>> Categoria_Mediciones, List<String> Lista_mediciones ){
        this.context = ctx;
        this.Categoria_Mediciones = Categoria_Mediciones;
        this.Lista_mediciones = Lista_mediciones;
    }


    @Override
    public int getGroupCount() {
        return Lista_mediciones.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Categoria_Mediciones.get(Lista_mediciones.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Lista_mediciones.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Categoria_Mediciones.get(Lista_mediciones.get(groupPosition)).get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String group_title = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.list_group,parent,false);
        }
        TextView parent_textview = (TextView) convertView.findViewById(R.id.lblListHeader);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String titulo_hijo = (String)getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.list_item,parent,false);
        }
        TextView child_textview = (TextView) convertView.findViewById(R.id.lblListItem);
        child_textview.setText(titulo_hijo);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
