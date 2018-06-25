package com.wenliu.bookshare.detial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    @BindView(R.id.tv_detail_book_subtitle)
    TextView mTvDetailBookSubtitle;
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
    @BindView(R.id.tv_detail_book_reading_page)
    TextView mTvDetailBookReadingPage;
    @BindView(R.id.tv_detail_book_status)
    TextView mTvDetailBookStatus;
    @BindView(R.id.tv_detail_book_borrow_status)
    TextView mTvDetailBookBorrowStatus;
    @BindView(R.id.divide_line_detail)
    View mDivideLineDetail;
    @BindView(R.id.llayout_detail_book_purchase_date)
    LinearLayout mLlayoutDetailBookPurchaseDate;
    @BindView(R.id.llayout_detail_book_purchase_price)
    LinearLayout mLlayoutDetailBookPurchasePrice;
    @BindView(R.id.llayout_detail_book_reading_page)
    LinearLayout mLlayoutDetailBookReadingPage;
    @BindView(R.id.btn_detail_edit)
    Button mBtnDetailEdit;
    Unbinder unbinder;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Constants.TAG_DETAIL_FRAGMENT, "onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        mImageManager = new ImageManager(getActivity());
        setToolbarVisibility(false);
        setFabVisibility(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(Constants.TAG_DETAIL_FRAGMENT, "onViewCreated: ");
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(Constants.TAG_DETAIL_FRAGMENT, "onDestroyView: ");
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG_DETAIL_FRAGMENT, "onDestroy: ");
        setToolbarVisibility(true);
        setFabVisibility(true);
    }

    @OnClick({R.id.btn_detail_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_detail_edit:
                mPresenter.showBookDataEditDialog(mBookCustomInfo);
                break;

            default:
                break;
        }
    }

    //region "Contract"
    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showBook(BookCustomInfo bookCustomInfo) {
        Log.d(Constants.TAG_DETAIL_FRAGMENT, "showBook: ");
        mBookCustomInfo = bookCustomInfo;

        mImageManager.loadImageUrl(bookCustomInfo.getImage(), mIvDetailBookCover);
        setTextView(mBookCustomInfo);
        setBookBorrowView(mBookCustomInfo.isHaveBook());
        setBookStatusView(mBookCustomInfo.getBookReadStatus());
        setBookCustomInfoView(mBookCustomInfo);
    }
    //endregion

    private void setTextView(BookCustomInfo bookCustomInfo) {
        mTvDetailBookSubtitle.setVisibility(View.GONE);
        mTvDetailBookAuthor.setText("");
        mTvDetailBookPublisher.setText("");
        mTvDetailBookPublishDate.setText("");
        mTvDetailBookLanguage.setText("");

        mTvDetailBookTitle.setText(bookCustomInfo.getTitle());

        if (bookCustomInfo.getSubtitle() != null && bookCustomInfo.getSubtitle().length() > 1) {
            mTvDetailBookSubtitle.setVisibility(View.VISIBLE);
            mTvDetailBookSubtitle.setText(bookCustomInfo.getSubtitle() + " ");
        }

        if (bookCustomInfo.getAuthor() != null && bookCustomInfo.getAuthor().size() > 0) {
            String author = bookCustomInfo.getAuthor().get(0);
            for (int i = 1; i < bookCustomInfo.getAuthor().size(); i++) {
                author += ", " + bookCustomInfo.getAuthor().get(i);
            }
            mTvDetailBookAuthor.setText(author);
        }

        if (bookCustomInfo.getPublisher() != null) {
            mTvDetailBookPublisher.setText(bookCustomInfo.getPublisher());
        }

        if (bookCustomInfo.getPublishDate() != null) {
            mTvDetailBookPublishDate.setText(bookCustomInfo.getPublishDate());
        }

        if (bookCustomInfo.getLanguage() != null) {
            mTvDetailBookLanguage.setText(bookCustomInfo.getLanguage());
        }

        mTvDetailBookIsbn.setText(mBookCustomInfo.getIsbn13());

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

            default:
                break;
        }
        mTvDetailBookStatus.setText(statusString);
        mTvDetailBookStatus.setBackground(ContextCompat.getDrawable(getActivity(), statusBackgroundColor));
        mTvDetailBookStatus.setTextColor(ContextCompat.getColor(getActivity(), statusTextColor));

    }

    private void setBookCustomInfoView(BookCustomInfo bookCustomInfo) {

        if (!bookCustomInfo.isHaveBook() && bookCustomInfo.getBookReadStatus() != Constants.READING) {
            mDivideLineDetail.setVisibility(View.GONE);
        }

        mLlayoutDetailBookPurchaseDate.setVisibility(bookCustomInfo.isHaveBook() ? View.VISIBLE : View.GONE);
        mLlayoutDetailBookPurchasePrice.setVisibility(bookCustomInfo.isHaveBook() ? View.VISIBLE : View.GONE);

        if (bookCustomInfo.isHaveBook()) {
            if (bookCustomInfo.getPurchaseDate() != null && bookCustomInfo.getPurchaseDate().length() > 0) {
                mTvDetailBookPurchaseDate.setText(bookCustomInfo.getPurchaseDate());
            } else {
                mLlayoutDetailBookPurchaseDate.setVisibility(View.GONE);
            }

            if (bookCustomInfo.getPurchasePrice() != null && bookCustomInfo.getPurchasePrice().length() > 0) {
                mTvDetailBookPurchasePrice.setText(bookCustomInfo.getPurchasePrice());
            } else {
                mLlayoutDetailBookPurchasePrice.setVisibility(View.GONE);
            }
        }

        if (bookCustomInfo.getBookReadStatus() == Constants.READING) {
            if (bookCustomInfo.getReadingPage() != -1) {
                mTvDetailBookReadingPage.setText(String.valueOf(bookCustomInfo.getReadingPage()));
            }
        } else {
            mLlayoutDetailBookReadingPage.setVisibility(View.GONE);
        }
    }

    private void setToolbarVisibility(boolean visible) {
        ((ShareBookActivity) getActivity()).setToolbarVisibility(visible);
    }

    private void setFabVisibility(boolean visible) {
        ((ShareBookActivity) getActivity()).setFabVisibility(visible);
    }

}
