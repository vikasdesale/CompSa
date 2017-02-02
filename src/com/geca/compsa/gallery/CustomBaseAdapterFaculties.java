package com.geca.compsa.gallery;



import java.util.List;

import com.geca.compsa.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapterFaculties extends BaseAdapter {
   Context context;
   List<RowItem> rowItems;
    
   public CustomBaseAdapterFaculties(Context context, List<RowItem> items) {
       this.context = context;
       this.rowItems = items;
   }
    
   /*private view holder class*/
   private class ViewHolder {

       TextView txtTitle;

   }

   public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
        
       LayoutInflater mInflater = (LayoutInflater) 
           context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
       if (convertView == null) {
           convertView = mInflater.inflate(R.layout.gallery_list_item, null);
           holder = new ViewHolder();
           holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
           convertView.setTag(holder);
       }
       else {
           holder = (ViewHolder) convertView.getTag();
       }
        
       RowItem rowItem = (RowItem) getItem(position);
        
       holder.txtTitle.setText(rowItem.getTitle());
        
       return convertView;
   }

   @Override
   public int getCount() {     
       return rowItems.size();
   }

   @Override
   public Object getItem(int position) {
       return rowItems.get(position);
   }

   @Override
   public long getItemId(int position) {
       return rowItems.indexOf(getItem(position));
   }
}