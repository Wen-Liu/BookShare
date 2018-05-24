package com.wenliu.bookshare.main;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.ShareBookActivity;
import com.wenliu.bookshare.api.callbacks.AlertDialogCallback;
import com.wenliu.bookshare.object.Book;
import com.wenliu.bookshare.object.BookCustomInfo;

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
    private int[] mBookStatusInfo;
    private MaterialDialog mMaterialDialog;
    private ArrayList<BookCustomInfo> mBookCustomInfos = new ArrayList<>();

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

        Log.d(Constants.TAG_MAIN_FRAGMENT, "onCreateView");
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
        mMainAdapter.updateData(bookCustomInfos);
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
}
