package com.wenliu.bookshare.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.object.Book;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements MainContract.View {

    @BindView(R.id.Recycview_main)
    RecyclerView mRecycviewMain;
    Unbinder unbinder;
    private MainContract.Presenter mPresenter;
    private MainAdapter mMainAdapter;
    private ArrayList<Book> mBooks = new ArrayList<>();

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.TAG_MAIN_FRAGMENT, "onCreate");
        mMainAdapter = new MainAdapter(mBooks, mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, layout);

        Log.d(Constants.TAG_MAIN_FRAGMENT, "onCreateView");
        mRecycviewMain.setLayoutManager(new LinearLayoutManager(ShareBook.getAppContext()));
        mRecycviewMain.addItemDecoration(new DividerItemDecoration(ShareBook.getAppContext(), DividerItemDecoration.VERTICAL));
        mRecycviewMain.setAdapter(mMainAdapter);

        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        if (presenter == null) {
            ShareBook.makeShortToast("MainContract.Presenter is null!");
        } else {
            mPresenter = presenter;
        }
    }

    @Override
    public void showBooks(ArrayList<Book> books) {
        Log.d(Constants.TAG_MAIN_FRAGMENT, "showBooks");
        mMainAdapter.updateData(books);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(Constants.TAG_MAIN_FRAGMENT, "onDestroyView");
        unbinder.unbind();
    }
}