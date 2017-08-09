package com.maotong.readhub.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.maotong.readhub.R;
import com.maotong.readhub.bean.readhub.tech.Datum;
import com.maotong.readhub.config.Config;
import com.maotong.readhub.utils.DBUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReadHubTechAdapter extends RecyclerView.Adapter<ReadHubTechAdapter.ReadHubViewHolder> {

    //解决item状态混乱问题
    private SparseBooleanArray mSparseBooleanArray = new SparseBooleanArray();

    private List<Datum> readHubNewsItems;
    private Context mContext;

    public ReadHubTechAdapter(ArrayList<Datum> readHubNewsItems, Context context) {
        this.readHubNewsItems = readHubNewsItems;
        this.mContext = context;
    }


    @Override
    public ReadHubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReadHubViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_readhub_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(final ReadHubViewHolder holder, final int position) {
        final Datum readHubHotItem = readHubNewsItems.get(holder.getAdapterPosition());
        if (DBUtils.getDB(mContext).isRead(Config.READHUB_NEWS, readHubHotItem.getId()+"", 1))
            holder.mTvTitle.setTextColor(Color.GRAY);
        else
            holder.mTvTitle.setTextColor(Color.BLACK);
        holder.mTvTitle.setText(readHubHotItem.getTitle());
        holder.mTvDescription.setText(readHubHotItem.getSummary());
        holder.mTvTime.setText(getTime(readHubHotItem.getPublishDate()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtils.getDB(mContext).insertHasRead(Config.READHUB_NEWS, readHubHotItem.getId()+"", 1);
                holder.mTvTitle.setTextColor(Color.GRAY);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(readHubHotItem.getUrl()));
                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(intent);
                }
            }
        });
        if (mSparseBooleanArray.get(readHubHotItem.getId())) {
            holder.btnDetail.setBackgroundResource(R.drawable.ic_expand_less_black_24px);
            holder.mTvDescription.setVisibility(View.VISIBLE);
        } else {
            holder.btnDetail.setBackgroundResource(R.drawable.ic_expand_more_black_24px);
            holder.mTvDescription.setVisibility(View.GONE);
        }
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mTvDescription.getVisibility() == View.GONE) {
                    holder.btnDetail.setBackgroundResource(R.drawable.ic_expand_less_black_24px);
                    holder.mTvDescription.setVisibility(View.VISIBLE);
                    mSparseBooleanArray.put(readHubHotItem.getId(), true);
                } else {
                    holder.btnDetail.setBackgroundResource(R.drawable.ic_expand_more_black_24px);
                    holder.mTvDescription.setVisibility(View.GONE);
                    mSparseBooleanArray.put(readHubHotItem.getId(), false);
                }
            }
        });


    }

    private String getTime(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = df.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);


            time = calendar.get(Calendar.MONTH) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日 " + calendar.get(Calendar.HOUR_OF_DAY) + "时" + calendar.get(Calendar.MINUTE) + "分 " + diffTime(date,new Date(System.currentTimeMillis())) + "小时前" ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    private int diffTime(Date date, Date currentDate) {
        long total = (currentDate.getTime() - date.getTime()) / 1000;
        return (int) (total / (60 * 60));
    }

    @Override
    public int getItemCount() {
        return readHubNewsItems.size();
    }

    class ReadHubViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_description)
        TextView mTvDescription;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.btn_detail)
        Button btnDetail;

        ReadHubViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
