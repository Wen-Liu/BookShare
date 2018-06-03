package com.wenliu.bookshare.lent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.base.BaseFragment;
import com.wenliu.bookshare.object.LentBook;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LentFragment extends BaseFragment implements LentContract.View {
    //region "BindView"
    @BindView(R.id.rv_lent)
    RecyclerView mRvLent;
    Unbinder unbinder;
    @BindView(R.id.tv_lent_no_data)
    TextView mTvLentNoData;
    //endregion

    private LentContract.Presenter mPresenter;
    private LentAdapter mLentAdapter;
    private ArrayList<LentBook> mLentBooks = new ArrayList<>();

    public LentFragment() {
        // Required empty public constructor
    }

    public static LentFragment newInstance() {
        return new LentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Constants.TAG_LENT_FRAGMENT, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lent, container, false);
        unbinder = ButterKnife.bind(this, view);

        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        mLentAdapter = new LentAdapter(mPresenter, mLentBooks);
        mRvLent.setLayoutManager(new LinearLayoutManager(ShareBook.getAppContext()));
        mRvLent.setAdapter(mLentAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(Constants.TAG_LENT_FRAGMENT, "onViewCreated: ");
        mPresenter.start();
        mPresenter.getMyLentData();
    }

    @Override
    public void setPresenter(LentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        Log.d(Constants.TAG_LENT_FRAGMENT, "onDestroyView: ");
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLent(ArrayList<LentBook> lentBooks) {
        mLentAdapter.updateData(lentBooks);
    }

    @Override
    public void isNoLentData(boolean isNoLentData) {
        Log.d(Constants.TAG_LENT_FRAGMENT, "onDestroyView: " + isNoLentData);
        mTvLentNoData.setVisibility(isNoLentData ? View.VISIBLE : View.GONE);
        mRvLent.setVisibility(isNoLentData ? View.GONE : View.VISIBLE);
    }
}
