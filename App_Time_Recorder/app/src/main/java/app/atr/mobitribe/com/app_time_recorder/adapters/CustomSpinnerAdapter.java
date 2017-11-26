package app.atr.mobitribe.com.app_time_recorder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import app.atr.mobitribe.com.app_time_recorder.R;
import app.atr.mobitribe.com.app_time_recorder.model.responses.Companies;

/**
 * Author: Uzair Qureshi
 * Date:  8/30/17.
 * Description:
 */

public class CustomSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Companies.Subadmin> company_List;
    private LayoutInflater inflater;



    public CustomSpinnerAdapter(Context mContext, ArrayList<Companies.Subadmin> companyList) {
        this.context = mContext;
        this.company_List = companyList;
       // inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = (LayoutInflater.from(mContext));


    }

    @Override
    public int getCount() {
        return company_List.size();
    }

    @Override
    public Object getItem(int position) {
        return company_List.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        return createSpinnerView(position,convertView,parent);
//    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        return createSpinnerView(position,view,parent);
    }
    private View createSpinnerView(int position, View view, ViewGroup parent){
         view=inflater.inflate(R.layout.custom_spinner_row,null);
        Companies.Subadmin admins=company_List.get(position);
        TextView company_id= (TextView) view.findViewById(R.id.company_name);
        if(admins!=null) {
            company_id.setText(""+admins.getName());
        }
        return view;

    }
}
