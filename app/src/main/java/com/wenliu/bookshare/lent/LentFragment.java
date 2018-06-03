package com.wenliu.bookshare.lent;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.base.BaseFragment;
import com.wenliu.bookshare.object.LentBook;

import java.util.ArrayList;
import java.util.Calendar;

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

    @Override
    public void showConfirmReject(final LentBook lentBook) {
        Log.d(Constants.TAG_LENT_FRAGMENT, "showConfirmReject: ");

        new AlertDialog.Builder(getActivity())
                .setMessage("確定要拒絕 " + lentBook.getBorrowerName() + " 的借閱要求嗎？")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.alert_dialog_delete_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.sendBorrowReject(lentBook);
                    }
                })
                .setNegativeButton(getString(R.string.alert_dialog_delete_negative), null)
                .create()
                .show();
    }

    @Override
    public void showConfirmAccept(final LentBook lentBook) {
        Log.d(Constants.TAG_LENT_FRAGMENT, "showConfirmAccept: ");

        final View returnDateView = View.inflate(getActivity(), R.layout.dialog_lend_accept, null);
        final EditText mEtReturnDate = ((EditText) returnDateView.findViewById(R.id.et_dialog_borrow_return_date));
        mEtReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mEtReturnDate, lentBook);
            }
        });

        new AlertDialog.Builder(getActivity())
                .setMessage("請確認希望歸還日期")
                .setView(returnDateView)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.alert_dialog_delete_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lentBook.setLendReturnDay(mEtReturnDate.getText().toString());
                        lentBook.setLentStatus(Constants.FIREBASE_LENT_APPROVE);
                        mPresenter.sendBorrowAccept(lentBook);
                    }
                })
                .setNegativeButton(getString(R.string.alert_dialog_delete_negative), null)
                .create()
                .show();
    }


    private void showDatePickerDialog(final EditText editText, LentBook lentBook) {
        Log.d(Constants.TAG_LENT_FRAGMENT, "showDatePickerDialog: ");

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of TimePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                // set lend return date
                editText.setText(year + "-" + month + "-" + dayOfMonth);
            }
        }, year, month, day);

        // set lend start date
        month += 1;
        lentBook.setLendStartDay(year + "-" + month + "-" + day);

        datePickerDialog.getDatePicker().setLayoutMode(1);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

}
