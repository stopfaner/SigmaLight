package com.stopfan.sigmalight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stopfan.sigmalight.R;
import com.stopfan.sigmalight.core.models.User;
import com.stopfan.sigmalight.core.net.LoginService;
import com.stopfan.sigmalight.core.net.Request;
import com.stopfan.sigmalight.core.net.RequestResult;
import com.stopfan.sigmalight.core.utils.Data;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Denys on 11/8/2015.
 */
public class LogInFragment extends Fragment {

    private TextView registerText;
    private EditText mEmailEdit;
    private EditText mPasswordEdit;
    private Button mLogInButton;

    private LoginService service;
    private CompositeSubscription subscription;

    public LogInFragment() {}

    public static LogInFragment getInstance() {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);

        mEmailEdit = (EditText) rootView.findViewById(R.id.email_edit);
        mPasswordEdit = (EditText) rootView.findViewById(R.id.password_edit);
        registerText = (TextView) rootView.findViewById(R.id.register_text);
        mLogInButton = (Button) rootView.findViewById(R.id.login_button);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmailEdit.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Введите e-mail", Toast.LENGTH_SHORT).show();
                } else if (mPasswordEdit.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_SHORT).show();
                } else {
                    sendLogin(mEmailEdit.getText().toString(), mPasswordEdit.getText().toString());
                }
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initService();
    }

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

        service = restAdapter.create(LoginService.class);
        subscription = new CompositeSubscription();
    }

    public void sendLogin(String email, String password) {

        subscription.add(
                service.loginAction(new Request<>("users.login", User.createLoginInstance(email, password)))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RequestResult>() {
                            @Override
                            public void call(RequestResult s) {
                                if ("success".equals(s.status)) {
                                    Data.setAuthorized(getActivity());
                                    Timber.d("NAME: " + s.data.getName());
                                    Timber.d("NAME: " + s.data.getName());
                                    Timber.d("NAME: " + s.data.getName());
                                    Data.saveUserData(getActivity(), s.data);
                                    getProfile();
                                } else {
                                    Toast.makeText(getActivity(), "Неправильный логин или пароль", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Timber.d(throwable.toString());
                            }
                        })
        );
    }

    private void register() {
        FragmentManager manager = getActivity().getSupportFragmentManager();

        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.push_slide_in, R.anim.pop_slide_out);

        RegisterFragment fragment = RegisterFragment.getInstance();

        ft.replace(R.id.fragment_host, fragment, "loginFragment");
        manager.popBackStack("login", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.addToBackStack("register");
        ft.commit();
    }

    private void getProfile() {
        FragmentManager manager = getActivity().getSupportFragmentManager();

        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.push_slide_in, R.anim.pop_slide_out);

        ProfileFragment fragment = ProfileFragment.getInstance();

        ft.replace(R.id.fragment_host, fragment, "profileFragment");
        manager.popBackStack("login", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.addToBackStack("profile");
        ft.commit();
    }
}
