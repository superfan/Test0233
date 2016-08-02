package com.sh3h.water.test0233;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangwei on 2016/8/2.
 */
public class Test2Activity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "Test2Activity";
    private SwipeRefreshLayout mSwipeRefreshWidget;
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private boolean isRefresh = false;//是否刷新中
    private int dataSetCount;
    private FloatingActionButton floatingActionButton;

    public Test2Activity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        dataSetCount = 50;
        mAdapter = new CustomAdapter(getDataSet(dataSetCount));
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //Log.i(TAG, "-----------onScrollStateChanged-----------");
                //Log.i(TAG, "newState: " + newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        //callback.onScrollToBottom();
                        Log.i(TAG, "-----------onScrollToBottom-----------");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Test2Activity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dataSetCount += 50;
                                        mAdapter.setmDataSet(getDataSet(dataSetCount));
                                    }
                                });
                            }
                        }).start();
                    }

                    if (!recyclerView.canScrollVertically(-1)) {
                        //callback.onScrollToTop();
                        Log.i(TAG, "-----------onScrollToTop-----------");
                    }

                    Log.i(TAG, "newState: " + newState);
                    Log.i(TAG, "current position: " + mAdapter.getCurrentPosition());
                    if (mAdapter.getCurrentPosition() > 20) {
                        floatingActionButton.setVisibility(View.VISIBLE);
                    } else {
                        floatingActionButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                Log.i(TAG, "-----------onScrolled-----------");
//                Log.i(TAG, "dx: " + dx);
//                Log.i(TAG, "dy: " + dy);
//                Log.i(TAG, "CHECK_SCROLL_UP: " + recyclerView.canScrollVertically(1));
//                Log.i(TAG, "CHECK_SCROLL_DOWN: " + recyclerView.canScrollVertically(-1));

                if (dy > 0) {
                    //callback.onScrollDown(ScrollRecycler.this, dy);
                } else {
                    //callback.onScrollUp(ScrollRecycler.this, dy);
                }
            }
        });

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Test2Activity.this, "up", Toast.LENGTH_SHORT).show();
                    mRecyclerView.smoothScrollToPosition(0);
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        if (!isRefresh) {
            isRefresh = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mSwipeRefreshWidget.setRefreshing(false);
                }
            }, 3000);
        }
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private List<String> getDataSet(int count) {
        List<String> dataSet = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dataSet.add("This is element #" + i);
        }

        dataSet.add("load more");
        return dataSet;
    }

//    private Handler mHandle = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//
//        }
//    };
}
