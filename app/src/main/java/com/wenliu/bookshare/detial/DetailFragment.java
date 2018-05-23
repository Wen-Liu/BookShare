package com.wenliu.bookshare.detial;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.ShareBookActivity;
import com.wenliu.bookshare.object.BookCustomInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements DetailContract.View {

    @BindView(R.id.iv_detail_book_cover)
    ImageView mIvDetailBookCover;
    @BindView(R.id.tv_detail_book_title)
    TextView mTvDetailBookTitle;
    @BindView(R.id.tv_detail_book_author)
    TextView mTvDetailBookAuthor;
    @BindView(R.id.tv_detail_book_publisher)
    TextView mTvDetailBookPublisher;
    @BindView(R.id.tv_detail_book_publish_date)
    TextView mTvDetailBookPublishDate;
    @BindView(R.id.tv_detail_book_language)
    TextView mTvDetailBookLanguage;
    @BindView(R.id.tv_detail_book_isbn)
    TextView mTvDetailBookIsbn;
    @BindView(R.id.tv_detail_book_purchase_date)
    TextView mTvDetailBookPurchaseDate;
    @BindView(R.id.tv_detail_book_purchase_price)
    TextView mTvDetailBookPurchasePrice;
    Unbinder unbinder;
    @BindView(R.id.tv_detail_book_status)
    TextView mTvDetailBookStatus;
    @BindView(R.id.llayout_detail_purchase_date)
    LinearLayout mLlayoutDetailPurchaseDate;
    @BindView(R.id.llayout_detail_purchase_Price)
    LinearLayout mLlayoutDetailPurchasePrice;
    @BindView(R.id.tv_detail_book_borrow_status)
    TextView mTvDetailBookBorrowStatus;
    private DetailContract.Presenter mPresenter;
    private ImageManager mImageManager;


    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.hideToolbar();
        mPresenter.hideFab();
        mImageManager = new ImageManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        Log.d(Constants.TAG_DETAIL_FRAGMENT, "onCreateView: ");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        if (presenter == null) {
            ShareBook.makeShortToast("DetailContract.Presenter is null!");
        } else {
            mPresenter = presenter;
        }
    }

    @Override
    public void showBook(BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_DETAIL_FRAGMENT, "showBook: start");

        mImageManager.loadUrlImage(bookCustomInfo.getImage(), mIvDetailBookCover);
        mTvDetailBookTitle.setText(bookCustomInfo.getTitle());

        if (bookCustomInfo.getAuthor() != null && bookCustomInfo.getAuthor().size() > 0) {
            mTvDetailBookAuthor.setText(bookCustomInfo.getAuthor().get(0));
        } else {
            mTvDetailBookAuthor.setText("");
        }
        if (bookCustomInfo.getPublisher() != null) {
            mTvDetailBookPublisher.setText(bookCustomInfo.getPublisher());
        } else {
            mTvDetailBookPublisher.setText("");
        }
        if (bookCustomInfo.getPublishDate() != null) {
            mTvDetailBookPublishDate.setText(bookCustomInfo.getPublishDate());
        } else {
            mTvDetailBookPublishDate.setText("");
        }
        if (bookCustomInfo.getLanguage() != null) {
            mTvDetailBookLanguage.setText(bookCustomInfo.getLanguage());
        } else {
            mTvDetailBookLanguage.setText("");
        }
        mTvDetailBookIsbn.setText(bookCustomInfo.getIsbn13());

        setBookStatusView(bookCustomInfo.getBookReadStatus());
        setBookBorrowView(bookCustomInfo.isHaveBook());
        setBookPurchaseView(bookCustomInfo);

        Log.d(Constants.TAG_DETAIL_FRAGMENT, "showBook: end ");
    }

    private void setBookBorrowView(boolean haveBook) {
        if (haveBook) {
            mTvDetailBookBorrowStatus.setText(getString(R.string.book_status_lendable));
            mTvDetailBookBorrowStatus.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_book_status_green));
        } else {
            mTvDetailBookBorrowStatus.setText(getString(R.string.book_status_lend_out));
            mTvDetailBookBorrowStatus.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_book_status_red));
        }
    }

    private void setBookStatusView(int bookStatus) {
        String statusString = "";
        int statusBackgroundColor = 0;

        switch (bookStatus) {
            case Constants.READING:
                statusString = getString(R.string.book_status_reading);
                statusBackgroundColor = R.drawable.shape_book_status_yellow;
                break;
            case Constants.READ:
                statusString = getString(R.string.book_status_read);
                statusBackgroundColor = R.drawable.shape_book_status_green;
                break;
            case Constants.UNREAD:
                statusString = getString(R.string.book_status_unread);
                statusBackgroundColor = R.drawable.shape_book_status_red;
                break;
        }


        mTvDetailBookStatus.setText(statusString);
        mTvDetailBookStatus.setBackground(ContextCompat.getDrawable(getActivity(), statusBackgroundColor));
    }

    private void setBookPurchaseView(BookCustomInfo bookCustomInfo) {

        mLlayoutDetailPurchaseDate.setVisibility(bookCustomInfo.isHaveBook() ? View.VISIBLE : View.GONE);
        mLlayoutDetailPurchasePrice.setVisibility(bookCustomInfo.isHaveBook() ? View.VISIBLE : View.GONE);

        if (bookCustomInfo.isHaveBook()) {
            if (bookCustomInfo.getPurchaseDate() != null) {
                mTvDetailBookPurchaseDate.setText(bookCustomInfo.getPurchaseDate());
            }
            if (bookCustomInfo.getPurchasePrice() != null) {
                mTvDetailBookPurchasePrice.setText(bookCustomInfo.getPurchasePrice());
            }
        }
    }

    @Override
    public void setToolbarVisibility(boolean visible) {
        ((ShareBookActivity) getActivity()).setToolbarVisibility(visible);
    }

    @Override
    public void setFabVisibility(boolean visible) {
        ((ShareBookActivity) getActivity()).setFabVisibility(visible);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.showToolbar();
        mPresenter.showFab();
    }

//    @OnClick({R.id.tv_detail_book_title})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tv_detail_book_title:
//                break;
//        }
//    }

}
