package com.lennon.cn.utill.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import lennon.com.utill.R;

/**
 * Created by dingyi on 2016/11/22.
 */

public class SearchView extends LinearLayout {

    EditText mInputEdt;
    ImageView mDeleteIgv;
    ListView mLv;
    TextView search;

    private Context mContext;
    private SearchViewListener mListener;

    /**
     * 提示adapter （推荐adapter）
     */
    private ArrayAdapter<String> mHistoryAdapter;

    public SearchView(Context context) {
        super(context);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.searchview_layout, this);
        inintView(view);
        try {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchView);
            mInputEdt.setHint(array.getString(R.styleable.SearchView_search_hint));
            if (array.getBoolean(R.styleable.SearchView_show_button, false)) {
                search.setVisibility(View.VISIBLE);
            } else {
                search.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mInputEdt.addTextChangedListener(new EditChangedListener());
        mInputEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    if (mListener != null) {
                        mListener.onSearchByButton(getText());
                    }
                    return true;
                }
                return false;
            }
        });
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                if (mListener != null) {
                    mListener.onSearchByButton(getText());
                }
            }
        });
        mDeleteIgv.setVisibility(GONE);
        mLv.setOnItemClickListener(mOnItemClickListener);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSearchViewListener(SearchViewListener listener) {
        this.mListener = listener;
    }

    public void setText(String text) {
        mInputEdt.setText(text);
    }

    public String getText() {
        return mInputEdt.getText().toString();
    }

    private void inintView(View v) {
        mInputEdt = (EditText) v.findViewById(R.id.searchview_input_edt);
        mDeleteIgv = (ImageView) v.findViewById(R.id.search_delete_igv);
        search = v.findViewById(R.id.search_search);
        mDeleteIgv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputEdt.setText("");
                mDeleteIgv.setVisibility(GONE);
            }
        });
        mLv = (ListView) v.findViewById(R.id.searchview_lv);
    }

    public void clearSearch() {
        mInputEdt.setText("");
        mDeleteIgv.setVisibility(GONE);
    }

    /**
     * 通知监听者 进行搜索操作
     *
     * @param text
     */
    private void notifyStartSearching(String text) {
        if (mListener != null) {
            mListener.onResult(text);
        }
        //隐藏软键盘
        mLv.setVisibility(GONE);
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 设置热搜版提示 adapter
     */
    public void setHistoryAdapter(ArrayAdapter<String> adapter) {
        this.mHistoryAdapter = adapter;
        if (mLv.getAdapter() == null) {
            mLv.setAdapter(mHistoryAdapter);
        }
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                mDeleteIgv.setVisibility(VISIBLE);
                mLv.setVisibility(GONE);
                //更新autoComplete数据
                if (mListener != null) {
                    mListener.onSearch(charSequence + "");
                }
            } else {
                mDeleteIgv.setVisibility(GONE);
                if (mHistoryAdapter != null) {
                    mLv.setAdapter(mHistoryAdapter);
                }
                if (null != mListener) {
                    mListener.onClear();
                }
                mLv.setVisibility(VISIBLE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i < mHistoryAdapter.getCount() - 1) {
                String key = mHistoryAdapter.getItem(i).toString();
                notifyStartSearching(key);
            }
        }
    };

    /**
     * search view回调方法
     */
    public interface SearchViewListener {

        /**
         * 清除搜索内容
         */
        void onClear();

        /**
         * 更新自动补全内容
         *
         * @param text
         */
        void onSearch(String text);

        /**
         * 搜索结果
         *
         * @param text
         */
        void onResult(String text);

        void onSearchByButton(String text);

    }

}
