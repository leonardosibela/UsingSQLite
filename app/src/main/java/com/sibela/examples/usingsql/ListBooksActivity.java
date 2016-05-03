package com.sibela.examples.usingsql;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.sibela.examples.usingsql.adapter.TitleDescriptionAdapter;
import com.sibela.examples.usingsql.model.Book;
import com.sibela.examples.usingsql.presenter.ListBooksPresenter;
import com.sibela.examples.usingsql.presenter.ListBooksPresenterImpl;
import com.sibela.examples.usingsql.util.GsonUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class ListBooksActivity extends AppCompatActivity {

    private TitleDescriptionAdapter<Book> mBookAdapter;
    private ListBooksPresenter mPresenter;

    public static final String BOOKS = "books";
    public static final String TITLE = "title";
    public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus urna ex, sagittis quis luctus a, iaculis a orci. Quisque in mi ipsum. Pellentesque a pellentesque nibh. Vestibulum dictum elementum dignissim.";

    @Bind(R.id.book_recycler_view)
    RecyclerView mBookRecycler;

    @Bind(R.id.title_input)
    EditText mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);
        ButterKnife.bind(this);

        mPresenter = new ListBooksPresenterImpl(this);

        mBookRecycler.setLayoutManager(new LinearLayoutManager(this));
        mBookAdapter = new TitleDescriptionAdapter<>(mPresenter.getAllBooks());
        mBookRecycler.setAdapter(mBookAdapter);
    }

    @OnClick(R.id.add_button)
    protected void addBookToList() {
        String bookTitle = mTitle.getText().toString();
        if (!bookTitle.isEmpty()) {
            Book book = new Book();
            book.setTitle(bookTitle);
            book.setDescription(LOREM_IPSUM);
            mPresenter.saveBook(book);
        }
    }

    public void setContent(Book book) {
        mBookAdapter.setContent(book);
        mTitle.setText("");
    }

    @OnEditorAction(R.id.title_input)
    public boolean titleEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            addBookToList();
            return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String title = mTitle.getText().toString();
        String strBooks = GsonUtil.toJsonAsString(mBookAdapter.getItens());

        outState.putString(TITLE, title);
        outState.putString(BOOKS, strBooks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        List<Book> books = GsonUtil.fromJsonList(savedInstanceState.getString(BOOKS), Book.class);
        mBookAdapter.setContent(books);
        mTitle.setText(savedInstanceState.getString(TITLE));
    }

    public void setBookNotSavedMessage() {
        Toast.makeText(getBaseContext(), R.string.book_could_not_be_saved, Toast.LENGTH_SHORT).show();
    }
}
