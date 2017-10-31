package com.hdmes.handingv2017;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hdmes.handingv2017.eventmessage.EventMessage;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView count_me;
    @Bind(R.id.btnGoinfo)
    Button mBtnGoinfo;
    @Bind(R.id.btnGocraneinfo)
    Button mBtnGocraneinfo;
    // TODO: Rename and change types of parameters

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_me, container, false);
        count_me=(TextView)view.findViewById(R.id.textView5);
        ButterKnife.bind(this, view);
        String str=((HomeActivity)getActivity()).getData();
        count_me.setText(count_me.getText()+str);
        return view;
    }
    @OnClick({R.id.btnGoinfo, R.id.btnGocraneinfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGoinfo:
                goSelect(EventMessage.EventMessageAction.TAG_GO_INFO);
                break;
            case R.id.btnGocraneinfo:
                goSelect(EventMessage.EventMessageAction.TAG_GO_CRANEINFO);
                break;
        }
    }
    private void goSelect(int tag) {
        EventMessage eventMessage = new EventMessage();
        eventMessage.setTag(tag);
        EventBus.getDefault().post(eventMessage);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
