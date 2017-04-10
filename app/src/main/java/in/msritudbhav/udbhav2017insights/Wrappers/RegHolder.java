package in.msritudbhav.udbhav2017insights.Wrappers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.msritudbhav.udbhav2017insights.R;

/**
 * Created by abhilash1in on 10/4/17.
 */

public class RegHolder extends RecyclerView.ViewHolder {

    private final TextView mNameField, mCollegeField, mEventNameField, mAmountField;

    public RegHolder(View view){
        super(view);
        mNameField = (TextView) view.findViewById(R.id.nameTV);
        mCollegeField = (TextView) view.findViewById(R.id.collegeTV);
        mEventNameField = (TextView) view.findViewById(R.id.eventNameTV);
        mAmountField = (TextView) view.findViewById(R.id.amountTV);
    }

    public void setName(String name){
        mNameField.setText(name);
    }

    public void setCollegeName(String college){
        mCollegeField.setText(college);
    }

    public void setEventName(String eventName){
        mEventNameField.setText(eventName);
    }

    public void setAmount(String amount){
        mAmountField.setText(amount);
    }
}
