package com.aegis.testtopguide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SFS on 2017/2/8.
 * Description :
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ComingBean> comingslist;
    private final LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<ComingBean> comingslist) {

        this.context = context;
        this.comingslist = comingslist;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.data_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return comingslist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mv_name;
        private TextView mv_dec;
        private TextView mv_date;
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mv_name = (TextView) itemView.findViewById(R.id.mv_name);
            mv_dec = (TextView) itemView.findViewById(R.id.mv_dec);
            mv_date = (TextView) itemView.findViewById(R.id.mv_date);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        public void setData(int position) {
            ComingBean comingBean = comingslist.get(position);
            mv_name.setText(comingBean.nm);
            mv_date.setText(comingBean.showInfo);
            mv_dec.setText(comingBean.scm);
            //注：当你发下图片无法打开是，做个字符串替换即可
            String imagUrl =comingBean.img;
            String newImagUrl = imagUrl.replaceAll("w.h", "50.80");
            //加载图片

            Picasso
                    .with(context)
                    .load(newImagUrl)
                    .into(imageView);
        }
    }
}
