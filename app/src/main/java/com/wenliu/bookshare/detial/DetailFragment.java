package com.wenliu.bookshare.detial;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenliu.bookshare.Constants;
import com.wenliu.bookshare.R;
import com.wenliu.bookshare.ShareBook;
import com.wenliu.bookshare.ShareBookActivity;
import com.wenliu.bookshare.object.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements DetailContract.View {

    @BindView(R.id.iv_detail_book_cover)
    ImageView mIvDetailBookCover;
    @BindView(R.id.tv_detail_book_title)
    TextView mTvDetailBookTitle;
    @BindView(R.id.spinner_detail)
    Spinner mSpinnerDetail;
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
    private DetailContract.Presenter mPresenter;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
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
    public void showBook(Book book) {
        Log.d(Constants.TAG_DETAIL_FRAGMENT, "showBook: ");

        Picasso.get().load(book.getImage()).into(mIvDetailBookCover);
        mTvDetailBookTitle.setText(book.getTitle());

        if (book.getAuthor() != null && book.getAuthor().size() > 0) {
            mTvDetailBookAuthor.setText(book.getAuthor().get(0));
        } else {
            mTvDetailBookAuthor.setText("");
        }
        if (book.getPublisher() != null) {
            mTvDetailBookPublisher.setText(book.getPublisher());
        } else {
            mTvDetailBookPublisher.setText("");
        }
        if (book.getPublishDate() != null) {
            mTvDetailBookPublishDate.setText(book.getPublishDate());
        } else {
            mTvDetailBookPublishDate.setText("");
        }
        if (book.getLanguage() != null) {
            mTvDetailBookLanguage.setText(book.getLanguage());
        } else {
            mTvDetailBookLanguage.setText("");
        }

        mTvDetailBookIsbn.setText(book.getIsbn13());
    }

    @Override
    public void setToolbarVisibility(boolean visible) {
        ((ShareBookActivity) getActivity()).setToolbarVisibility(visible);
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
    }

    @OnClick({R.id.tv_detail_book_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_detail_book_title:
                break;
        }
    }
}
