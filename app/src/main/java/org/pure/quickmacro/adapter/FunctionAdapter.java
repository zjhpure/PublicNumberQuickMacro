package org.pure.quickmacro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import org.pure.quickmacro.R;
import org.pure.quickmacro.model.FunctionItem;

/**
 * @author pure
 * @date 18-6-19 下午5:35
 * @description 功能适配器
 */
public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FuncHolder> {
    private Context mContext;
    private List<FunctionItem> mFunctionItemList;
    private OnItemClickListener mOnItemClickLitener;

    public FunctionAdapter(Context context, List<FunctionItem> functionItemList) {
        mContext = context;
        mFunctionItemList = functionItemList;
    }

    @NonNull
    @Override
    public FuncHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final FuncHolder holder = new FuncHolder(LayoutInflater.from(mContext).inflate(R.layout.item_function, parent, false));
        if (mOnItemClickLitener != null) {
            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onItemClick(pos);
                    }
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FuncHolder holder, int position) {
        FunctionItem item = mFunctionItemList.get(position);
        holder.icon.setImageDrawable(mContext.getResources().getDrawable(item.resId));
        holder.funcName.setText(item.funcName);
        holder.subFuncName.setText(item.subFuncName);
    }

    @Override
    public int getItemCount() {
        return mFunctionItemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class FuncHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView icon;
        @BindView(R.id.tv_func_name)
        TextView funcName;
        @BindView(R.id.tv_sub_func_name)
        TextView subFuncName;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        private FuncHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}