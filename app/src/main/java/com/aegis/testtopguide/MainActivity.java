package com.aegis.testtopguide;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.parseObject;

public class MainActivity extends AppCompatActivity {
    /**
     * 联网请求所需的url
     */
    public String url = "http://api.meituan.com/mmdb/movie/v2/list/rt/order/coming.json?ci=1&limit=12&token=&__vhost=api.maoyan.com&utm_campaign=AmovieBmovieCD-1&movieBundleVersion=6801&utm_source=xiaomi&utm_medium=android&utm_term=6.8.0&utm_content=868030022327462&net=255&dModel=MI%205&uuid=0894DE03C76F6045D55977B6D4E32B7F3C6AAB02F9CEA042987B380EC5687C43&lat=40.100673&lng=116.378619&__skck=6a375bce8c66a0dc293860dfa83833ef&__skts=1463704714271&__skua=7e01cf8dd30a179800a7a93979b430b2&__skno=1a0b4a9b-44ec-42fc-b110-ead68bcc2824&__skcy=sXcDKbGi20CGXQPPZvhCU3%2FkzdE%3D";
    private RecyclerView recyclerView;
    private List<NameBean> dataList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mContext = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);

        getData();

    }

    /**
     * 联网获取数据
     */
    private void getData() {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.d("MainActivity", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, String s) {
                        Log.d("MainActivity", "联网成功" + s);

                        parserData(s);
                    }
                });
    }

    /**
     * 解析数据
     *
     * @param s
     */
    private void parserData(String s) {
        //这里使用GsonFormat生成对应的bean类
        JSONObject jsonObject = parseObject(s);

        String data = jsonObject.getString("data");
        JSONObject dataObj = parseObject(data);

        String coming = dataObj.getString("coming");
        List<ComingBean> comingslist = parseArray(coming, ComingBean.class);

        //测试是否解析数据成功
        String strTest = comingslist.get(0).cat;
        Log.e("TAG", strTest + "222");
        // 解析成功  设置适配器
        setPullAction(comingslist);
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, comingslist);
//  设置RecycleView的布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);


//为RecyclerView添加ItemDecoration:
        recyclerView.addItemDecoration(new SectionDecoration(dataList, mContext, new DecorationCallback() {
            @Override
            public String getGroupId(int position) {
              if (dataList.get(position).name!=null){
                  return  dataList.get(position).name;
              }
                return  "-1";
            }

            @Override
            public String getGroupFirstLine(int position) {
            if (dataList.get(position).name!=null){
                return dataList.get(position).name;
            }
                return "";
            }
        }));

        recyclerView.setAdapter(adapter);

    }

    /**
     * 在向list集合中先把每一个item的 起“标记”作用的字符串都加进去
     *
     * @param comingslist
     */
    private void setPullAction(List<ComingBean> comingslist) {
        dataList = new ArrayList<>();

        for (int i = 0; i < comingslist.size(); i++) {
            NameBean nameBean = new NameBean();
            String name0 = comingslist.get(i).comingTitle;
            nameBean.name = name0;
            dataList.add(nameBean);
        }
    }
}
