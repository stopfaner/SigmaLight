package com.stopfan.sigmalight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stopfan.sigmalight.R;
import com.stopfan.sigmalight.core.models.Category;
import com.stopfan.sigmalight.core.models.SubCategory;
import com.stopfan.sigmalight.core.net.CategoryService;
import com.stopfan.sigmalight.core.net.LoginService;
import com.stopfan.sigmalight.core.net.Request;
import com.stopfan.sigmalight.core.net.RequestResult;
import com.stopfan.sigmalight.core.net.SubResult;
import com.stopfan.sigmalight.core.utils.RxUtils;
import com.stopfan.sigmalight.ui.adapter.SubCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Denys on 11/11/2015.
 */
public class SubCategoryFragment extends Fragment{

    private RecyclerView mSubRecycler;
    private SubCategoryAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private CompositeSubscription subscription;
    private CategoryService service;

    private List<SubCategory> categories;

    private int id;

    public SubCategoryFragment newInstance(Bundle bundle) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        id = bundle.getInt("id");
        return fragment;
    }

    public SubCategoryFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        RxUtils.getNewCompositeSubIfUnsubscribed(subscription);
    }

    @Override
    public void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribeIfNotNull(subscription);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sub_category, container, false);

        mSubRecycler = (RecyclerView) rootView.findViewById(R.id.sub_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSubRecycler.setLayoutManager(mLayoutManager);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initService();
        fetchCategories();
    }

    /**
     * Method initializes service for downloading subcategories
     */
    private void initService() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(RequestResult.class, new RequestResult.ResultDeserializer())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LoginService.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("LOGIN"))
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(CategoryService.class);
        subscription = new CompositeSubscription();
    }

    private void fetchCategories() {
        subscription
                .add(service.getSubCategories(new Request<SubCategory>("users.get-sub-category", new SubCategory(new Long(1))))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SubResult>() {
                    @Override
                    public void call(SubResult requestResult) {
                        categories = new ArrayList<>(requestResult.data);
                        mAdapter = new SubCategoryAdapter(getActivity(), categories);
                        mSubRecycler.setAdapter(mAdapter);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }));
    }
}
