package liuhongliang.bawei.com.recyclerviewcheckbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<String> mList;
    private RecyclerView mRecyclerView;
    private Button commit;
    private RecyclerViewListAdapter mRecyclerViewListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();

    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            mList.add("我是刘宏亮"+i);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerViewListAdapter = new RecyclerViewListAdapter(this,mList);
        mRecyclerView.setAdapter(mRecyclerViewListAdapter);
        //添加分割线
        mRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        mRecyclerViewListAdapter.setRecyclerViewOnItemClickListener(new RecyclerViewListAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                mRecyclerViewListAdapter.setSelectItem(position);
            }

            @Override
            public boolean onItemLongClickListener(View view, int position) {
                mRecyclerViewListAdapter.setShowBox();
                //设置选中的项
                mRecyclerViewListAdapter.setSelectItem(position);
                mRecyclerViewListAdapter.notifyDataSetChanged();
                return true;
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //全选
            case R.id.all:
                Map<Integer, Boolean> map = mRecyclerViewListAdapter.getMap();
                for (int i = 0; i < map.size(); i++) {
                    map.put(i, true);
                    mRecyclerViewListAdapter.notifyDataSetChanged();
                }
                break;
            //全不选
            case R.id.no_all:
                Map<Integer, Boolean> m = mRecyclerViewListAdapter.getMap();
                for (int i = 0; i < m.size(); i++) {
                    m.put(i, false);
                    mRecyclerViewListAdapter.notifyDataSetChanged();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

            //获取你选中的item
            Map<Integer, Boolean> map = mRecyclerViewListAdapter.getMap();
            for (int i = 0; i < map.size(); i++) {
                if (map.get(i)) {
                    Log.d("TAG", "你选了第：" + i + "项");
                }
            }

    }
}
