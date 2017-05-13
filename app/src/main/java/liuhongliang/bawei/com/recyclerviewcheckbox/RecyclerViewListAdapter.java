package liuhongliang.bawei.com.recyclerviewcheckbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * date:2017/5/12.
 * author:刘宏亮.
 * function:
 */
public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.ListViewHolder> implements View.OnClickListener,View.OnLongClickListener{
    private Context mContext;
    private List<String>mData;
    //是否显示单选框,默认false
    private boolean isshowBox = false;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> map = new HashMap<>();
    private RecyclerViewOnItemClickListener onItemClickListener;

    public RecyclerViewListAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
        initMap();
    }
    //初始化map集合,默认为不选中
    private void initMap() {
        for (int i = 0; i < mData.size(); i++) {
            map.put(i, false);
        }
    }

    @Override
    public RecyclerViewListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewListAdapter.ListViewHolder holder, final int position) {
        holder.mTextView.setText(mData.get(position));
        //长按显示/隐藏
        if (isshowBox) {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        }
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.list_anim);
        if (isshowBox)
            holder.mCheckBox.startAnimation(animation);
            holder.root.setTag(position);
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //用map集合保存
                    map.put(position, isChecked);
                }
            });
            // 设置CheckBox的状态
            if (map.get(position) == null) {
                map.put(position, false);

            }
            holder.mCheckBox.setChecked(map.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener!=null){
            onItemClickListener.onItemClickListener(v,(Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        initMap();
        return onItemClickListener != null && onItemClickListener.onItemLongClickListener(v, (Integer) v.getTag());
    }
    //设置点击事件
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setShowBox() {
        //取反
        isshowBox = !isshowBox;
    }
    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        notifyItemChanged(position);
    }
    //返回集合给MainActivity
    public Map<Integer, Boolean> getMap() {
        return map;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;
        private final CheckBox mCheckBox;
        private View root;

        public ListViewHolder(View root) {
            super(root);
            this.root=root;
            mTextView = (TextView) itemView.findViewById(R.id.tv);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.cb);
        }
    }
    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);

        //长按事件
        boolean onItemLongClickListener(View view, int position);
    }
}
