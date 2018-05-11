package com.sososeen09.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sososeen09.sample.bean.AttributeWithType;
import com.sososeen09.sample.bean.ListInfoWithType;
import com.sososeen09.sample.bean.Name;
import com.sososeen09.sample.http.RetrofitManager;
import com.sososeen09.sample.itembinder.AddressBinder;
import com.sososeen09.sample.itembinder.NameBinder;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

public class AdapterActivity extends AppCompatActivity {

    private static final String TAG = "AdapterActivity";

    RecyclerView rv;
    private MultiTypeAdapter mMultiTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mMultiTypeAdapter = new MultiTypeAdapter();
        mMultiTypeAdapter.register(AttributeWithType.class).to(new NameBinder(), new AddressBinder()).withClassLinker(new ClassLinker<AttributeWithType>() {
            @NonNull
            @Override
            public Class<? extends ItemViewBinder<AttributeWithType, ?>> index(int position, @NonNull AttributeWithType attributeWithType) {
                return attributeWithType.getAttributes() instanceof Name ? NameBinder.class : AddressBinder.class;
            }
        });
        rv.setAdapter(mMultiTypeAdapter);

        loadData();
    }

    void loadData() {
        RetrofitManager.getHttpService()
                .getUserAttrs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListInfoWithType>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ListInfoWithType listInfoWithType) {
                        Log.d(TAG, "useWithRetrofit: " + listInfoWithType);
                        mMultiTypeAdapter.setItems(listInfoWithType.getList());
                        mMultiTypeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
