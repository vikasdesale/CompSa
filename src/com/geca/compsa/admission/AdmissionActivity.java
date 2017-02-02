package com.geca.compsa.admission;

import java.util.ArrayList;
import java.util.List;

import com.geca.compsa.R;
import com.geca.compsa.admission.AnimatedExpandableListView;
import com.geca.compsa.admission.AnimatedExpandableListView.AnimatedExpandableListAdapter;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

/**
 * This is an example usage of the AnimatedExpandableListView class.
 * 
 * It is an activity that holds a listview which is populated with 100 groups
 * where each group has from 1 to 100 children (so the first group will have one
 * child, the second will have two children and so on...).
 */
@SuppressLint("NewApi") public class AdmissionActivity extends Activity implements OnMenuItemClickListener {
    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admission_activity_main);
        ImageView iv1=(ImageView) findViewById(R.id.imageView1);
        ImageView iv2=(ImageView) findViewById(R.id.imageView3);
        ImageView iv3=(ImageView) findViewById(R.id.imageView2);
        iv1.setBackgroundResource(R.anim.animation1);
        iv2.setBackgroundResource(R.anim.animation);
        iv3.setBackgroundResource(R.anim.animation2);
        AnimationDrawable anim1=(AnimationDrawable) iv1.getBackground();
        AnimationDrawable anim2=(AnimationDrawable) iv2.getBackground();
        AnimationDrawable anim3=(AnimationDrawable) iv3.getBackground();
            anim1.start();
            anim2.start();    
            anim3.start();
        List<GroupItem> items = new ArrayList<GroupItem>();
        
       

			
            GroupItem item = new GroupItem();
            
            item.title = "First Year Computer Sci. & Engg.(FE CSE)";
                ChildItem child = new ChildItem();
                child.title = "The entrance examination for the selection to Engineering/Technology in State of Maharashtra is conducted by the Competent Authority appointed by Government of Maharashtra. The Selection Process shall however be separate for degree courses in  Engineering/Technology. The respective Competent Authorities shall devise the system of Counseling/Allotment. This procedure shall be governed by the regulations and directions as specified by the Government of Maharashtra from time to time.";
                child.hint = "http://www.dtemaharashtra.gov.in";   child.seats="Intake Capcity : 60";
                item.items.add(child);
            items.add(item);
            GroupItem item1 = new GroupItem();
            item1.title = "Second Year Computer Sci. & Engg.(DSE CSE)";
            ChildItem child1 = new ChildItem();
            child1.title = "The Candidate must be passed Post-SSC or Post-HSC Diploma Course in Engineering/Technology.The Candidate must be B.Sc. Degree from a UGC / Association of Indian Universities recognized University." +
            		"A Candidate is considered Eligible for Admission to Direct Second Year of Degree Courses in Engineering / Technology through CAP Which is taken by  Directorate of Technical Education, Mumbai";
            child1.hint = "http://www.dtemaharashtra.gov.in";
            child1.seats="Intake Capcity : 12";
            item1.items.add(child1);
        items.add(item1);
        GroupItem item11 = new GroupItem();
        item11.title = "Master Of Computer Sci. & Engg.(DSE CSE)";
        ChildItem child11 = new ChildItem();
        child11.title = "A Candidate is considered Eligible for Admission to First Year Full Time Graduate Degree Course in Master of Engineering/Technology (M.E. /M.Tech.) by CAP Round Which is taken by  Directorate of Technical Education, Mumbai";
        child11.hint = "http://www.dtemaharashtra.gov.in";
        child11.seats="Intake Capcity : ME Full Time: 20 ME Part Time:20";
        item11.items.add(child11);
    items.add(item11);
        
        adapter = new ExampleAdapter(this);
        adapter.setData(items);
        
        listView = (AnimatedExpandableListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        
        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        listView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group 
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
            
        });
    }
    
    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }
    
    private static class ChildItem {
        String title;
        String hint;     String seats;
    }
    
    private static class ChildHolder {
        TextView title;
        TextView hint;      TextView seats;
    }
    
    private static class GroupHolder {
        TextView title;
    }
    
    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class ExampleAdapter extends AnimatedExpandableListAdapter {
        private LayoutInflater inflater;
        
        private List<GroupItem> items;
        
        public ExampleAdapter(Context context) {
             inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.admission_list_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                holder.hint = (TextView) convertView.findViewById(R.id.textHint);
                holder.seats = (TextView) convertView.findViewById(R.id.textSeats);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }
            
            holder.title.setText(item.title);
            holder.hint.setText(item.hint);
            holder.seats.setText(item.seats);
            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.admission_group_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }
            
            holder.title.setText(item.title);
            
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }
        
    }
    public boolean onMenuItemClick(MenuItem item) {
  	  switch(item.getItemId()){
 

        case R.id.item_about:
      	  Intent j11 =new Intent(AdmissionActivity.this, com.geca.compsa.about.AboutUsDev.class);
            startActivity(j11);
            break;

    }
    return true;
	}
    
}
