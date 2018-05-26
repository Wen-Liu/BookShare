package com.wenliu.bookshare.detial;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.ImageManager;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.ShareBookActivity;
import com.wenliu.bookshare.main.MainAdapter;
import com.wenliu.bookshare.object.BookCustomInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements DetailContract.View {

    //region "BindView"
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
    @BindView(R.id.tv_detail_book_borrow_status)
    TextView mTvDetailBookBorrowStatus;
    @BindView(R.id.tv_detail_book_subtitle)
    TextView mTvDetailBookSubtitle;
    @BindView(R.id.btn_detail_edit)
    Button mBtnDetailEdit;
    @BindView(R.id.llayout_detail_purchase)
    LinearLayout mLlayoutDetailPurchase;
    //endregion
    private DetailContract.Presenter mPresenter;
    private ImageManager mImageManager;
    private BookCustomInfo mBookCustomInfo;

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

        mBookCustomInfo = bookCustomInfo;
        mImageManager = new ImageManager(getActivity());
        mImageManager.loadUrlImage(bookCustomInfo.getImage(), mIvDetailBookCover);
        mTvDetailBookTitle.setText(mBookCustomInfo.getTitle());

        if (mBookCustomInfo.getSubtitle() != null && mBookCustomInfo.getSubtitle().length() > 1) {
            mTvDetailBookSubtitle.setText(mBookCustomInfo.getSubtitle() + " ");
        } else {
            mTvDetailBookSubtitle.setVisibility(View.GONE);
        }
        if (mBookCustomInfo.getAuthor() != null && mBookCustomInfo.getAuthor().size() > 0) {
            String author = mBookCustomInfo.getAuthor().get(0);
            for (int i = 1; i < mBookCustomInfo.getAuthor().size(); i++) {
                author += ", " + mBookCustomInfo.getAuthor().get(i);
            }
            mTvDetailBookAuthor.setText(author);
        } else {
            mTvDetailBookAuthor.setText("");
        }
        if (mBookCustomInfo.getPublisher() != null) {
            mTvDetailBookPublisher.setText(mBookCustomInfo.getPublisher());
        } else {
            mTvDetailBookPublisher.setText("");
        }
        if (mBookCustomInfo.getPublishDate() != null) {
            mTvDetailBookPublishDate.setText(mBookCustomInfo.getPublishDate());
        } else {
            mTvDetailBookPublishDate.setText("");
        }
        if (mBookCustomInfo.getLanguage() != null) {
            mTvDetailBookLanguage.setText(mBookCustomInfo.getLanguage());
        } else {
            mTvDetailBookLanguage.setText("");
        }
        mTvDetailBookIsbn.setText(mBookCustomInfo.getIsbn13());

        setBookStatusView(mBookCustomInfo.getBookReadStatus());
        setBookBorrowView(mBookCustomInfo.isHaveBook());
        setBookPurchaseView(mBookCustomInfo);

        Log.d(Constants.TAG_DETAIL_FRAGMENT, "showBook: end ");
    }

    private void setBookBorrowView(boolean haveBook) {
        if (haveBook) {
            mTvDetailBookBorrowStatus.setText(getString(R.string.book_status_lendable));
            mTvDetailBookBorrowStatus.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_book_status_green));
            mTvDetailBookBorrowStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.Green_600));

        } else {
            mTvDetailBookBorrowStatus.setText(getString(R.string.book_status_lend_out));
            mTvDetailBookBorrowStatus.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_book_status_red));
            mTvDetailBookBorrowStatus.setTextColor(ContextCompat.getColor(ShareBook.getAppContext(), R.color.Red_600));
        }
    }

    private void setBookStatusView(int bookStatus) {
        String statusString = "";
        int statusBackgroundColor = 0;
        int statusTextColor = 0;

        switch (bookStatus) {
            case Constants.READING:
                statusString = getString(R.string.book_status_reading);
                statusBackgroundColor = R.drawable.shape_book_status_yellow;
                statusTextColor = R.color.Yellow_600;
                break;
            case Constants.READ:
                statusString = getString(R.string.book_status_read);
                statusBackgroundColor = R.drawable.shape_book_status_green;
                statusTextColor = R.color.Green_600;
                break;
            case Constants.UNREAD:
                statusString = getString(R.string.book_status_unread);
                statusBackgroundColor = R.drawable.shape_book_status_red;
                statusTextColor = R.color.Red_600;
                break;
        }
        mTvDetailBookStatus.setText(statusString);
        mTvDetailBookStatus.setBackground(ContextCompat.getDrawable(getActivity(), statusBackgroundColor));
        mTvDetailBookStatus.setTextColor(ContextCompat.getColor(getActivity(), statusTextColor));

    }

    private void setBookPurchaseView(BookCustomInfo bookCustomInfo) {

        mLlayoutDetailPurchase.setVisibility(bookCustomInfo.isHaveBook() ? View.VISIBLE : View.GONE);

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

    @OnClick({R.id.btn_detail_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_detail_edit:
                mPresenter.showBookDataEditDialog(mBookCustomInfo);
                break;
        }
    }


}
