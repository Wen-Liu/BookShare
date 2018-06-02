package com.wenliu.bookshare.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.ShareBookActivity;
import com.wenliu.bookshare.api.callbacks.AlertDialogCallback;
import com.wenliu.bookshare.object.BookCustomInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements MainContract.View, AdapterView.OnItemSelectedListener {

    //region "BindView"
    @BindView(R.id.Recycview_main)
    RecyclerView mRecycviewMain;
    Unbinder unbinder;
    @BindView(R.id.spinner_main_filter)
    Spinner mSpinnerMainFilter;
    @BindView(R.id.btn_main_filter)
    Button mBtnMainFilter;
    @BindView(R.id.ll_main_no_data)
    LinearLayout mLlMainNoData;
    //endregion

    private MainContract.Presenter mPresenter;
    private MainAdapter mMainAdapter;
    private int[] mBookStatusInfo;
    private MaterialDialog mMaterialDialog;
    private ArrayList<BookCustomInfo> mBookCustomInfos = new ArrayList<>();
    private ArrayList<BookCustomInfo> mBookCustomInfosAll = new ArrayList<>();

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
        mMainAdapter = new MainAdapter(mBookCustomInfos, mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, layout);

        Log.d(Constants.TAG_MAIN_FRAGMENT, "onCreateView: ");
        mRecycviewMain.setLayoutManager(new LinearLayoutManager(ShareBook.getAppContext()));
//        mRecycviewMain.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                int space = ShareBook.getAppContext().getResources().getDimensionPixelSize(R.dimen.space_item);
//                outRect.top = space;
//            }
//        });
        mRecycviewMain.setAdapter(mMainAdapter);

        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
        mSpinnerMainFilter.setOnItemSelectedListener(this);
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
    public void showBooks(ArrayList<BookCustomInfo> bookCustomInfos) {
        Log.d(Constants.TAG_MAIN_FRAGMENT, "showBooks");
        mBookCustomInfos = bookCustomInfos;

        mBookCustomInfosAll = new ArrayList<>(bookCustomInfos);
//        mBookCustomInfosAll.clear();
//        for(BookCustomInfo bookCustomInfo: mBookCustomInfos){
//            mBookCustomInfosAll.add(bookCustomInfo);
//        }

        mMainAdapter.updateData(mBookCustomInfos);
        mSpinnerMainFilter.setSelection(0);
    }

    @Override
    public void showDetailUi(BookCustomInfo bookCustomInfo, ImageView imageView) {
        ((ShareBookActivity) getActivity()).transToDetail(bookCustomInfo, imageView);
    }

    @Override
    public void showProgressDialog(boolean show) {
        if (show) {
            if (mMaterialDialog == null) {
                mMaterialDialog = new MaterialDialog.Builder(getActivity())
                        .content(R.string.please_wait)
                        .progress(true, 0)
                        .show();
            } else {
                mMaterialDialog.show();
            }
        } else {
            mMaterialDialog.dismiss();
        }
    }

    @Override
    public void showAlertDialog(String title, final AlertDialogCallback callback) {

        new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.alert_dialog_delete_confirm) + title + getString(R.string.alert_dialog_delete_confirm_2))
                .setPositiveButton(getString(R.string.alert_dialog_delete_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCompleted();
                    }
                })
                .setNegativeButton(getString(R.string.alert_dialog_delete_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCancel();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void isNoBookData(boolean isNoBookData) {
        mLlMainNoData.setVisibility(isNoBookData ? View.VISIBLE : View.GONE);
        mRecycviewMain.setVisibility(isNoBookData ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showMyBookStatus(int[] bookStatusInfo) {
        mBookStatusInfo = bookStatusInfo;
    }

    public int[] getMyBookStatus() {
        return mBookStatusInfo;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(Constants.TAG_MAIN_FRAGMENT, "onDestroyView");
        unbinder.unbind();
    }

    @OnClick(R.id.btn_main_filter)
    public void onViewClicked() {
        mSpinnerMainFilter.performClick();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(Constants.TAG_MAIN_FRAGMENT, "onItemSelected: ");
        ArrayList<BookCustomInfo> test = mPresenter.DataFilter(mBookCustomInfosAll, position);
        mMainAdapter.updateData(test);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
