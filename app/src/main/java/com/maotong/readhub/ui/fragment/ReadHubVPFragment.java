package com.maotong.readhub.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maotong.readhub.R;
import com.maotong.readhub.bean.readhub.hot.Datum;
import com.maotong.readhub.presenter.IReadHubVPPresenter;
import com.maotong.readhub.presenter.impl.ReadHubVPPresenterImpl;
import com.maotong.readhub.ui.activity.ReadHubDetailActivity;
import com.maotong.readhub.ui.iView.IReadHubVPFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReadHubVPFragment extends BaseFragment implements IReadHubVPFragment {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.detail)
    TextView detail;

    @BindView(R.id.newarray)
    LinearLayout mNewArrayLayout;

    private static final String DATA = "datum";

    private Unbinder mUnbinder;

    private Datum mDatum ;

    //private ReadHubAdapter readHubAdapter;
    private IReadHubVPPresenter mReadHubPresenter;


    public ReadHubVPFragment() {
    }

    public static ReadHubVPFragment newInstance(Datum datum) {
        ReadHubVPFragment fragment = new ReadHubVPFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DATA, datum);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_readhub_vp, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mReadHubPresenter = new ReadHubVPPresenterImpl(this, getActivity());
        mDatum = getArguments() != null ? (Datum) getArguments().getParcelable(DATA) :new Datum();
        title.setText(mDatum.getTitle());
        detail.setText(mDatum.getSummary());
        loadNewArray();
    }

    private void loadNewArray() {
        if (mDatum.getNewsArray() == null)
            return;
        for (int i = 0; i < mDatum.getNewsArray().size(); i++){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            final LayoutInflater inflater = LayoutInflater.from(getActivity());
            final View view = inflater.inflate(R.layout.layout_vp_title, null);
            view.setLayoutParams(lp);
            TextView textView = (TextView) view.findViewById(R.id.id_vp_tv);
            textView.setText(mDatum.getNewsArray().get(i).getSiteName());
            textView.setTag(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tagI = (int) v.getTag();
                    Intent intent = new Intent(getActivity(), ReadHubDetailActivity.class);
                    intent.putExtra("id", mDatum.getNewsArray().get(tagI).getMobileUrl());
                    intent.putExtra("title", mDatum.getNewsArray().get(tagI).getSiteName());
                    startActivity(intent);
                }
            });
            mNewArrayLayout.addView(textView);
        }
    }

    private void initView() {
        hidProgressDialog();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mReadHubPresenter.unSubscribe();
        mUnbinder.unbind();
    }

    @Override
    public void showProgressDialog() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {

    }

}
