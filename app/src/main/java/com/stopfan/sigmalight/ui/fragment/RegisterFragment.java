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
public class RegisterFragment extends Fragment {

    private EditText mNameEdit;
    private EditText mSurnameEdit;
    private EditText mMaleEdit;
    private EditText mPhoneEdit;
    private EditText mEmailEdit;
    private EditText mPasswordEdit;

    private Button mRegisterButton;

    private CompositeSubscription subscription;
    private LoginService service;

    public static RegisterFragment getInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_fragment, container, false);

        mNameEdit = (EditText) rootView.findViewById(R.id.name_edit);
        mSurnameEdit = (EditText) rootView.findViewById(R.id.surname_edit);
        mMaleEdit = (EditText) rootView.findViewById(R.id.gender_edit);
        mPhoneEdit = (EditText) rootView.findViewById(R.id.phone_edit);
        mEmailEdit = (EditText) rootView.findViewById(R.id.email_edit);
        mPasswordEdit = (EditText) rootView.findViewById(R.id.password_edit);

        mRegisterButton = (Button) rootView.findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                if (mNameEdit.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Введите имя", Toast.LENGTH_SHORT).show();
                else
                    user.setName(mNameEdit.getText().toString());
                if (mSurnameEdit.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Введите фамилию", Toast.LENGTH_SHORT).show();
                else
                    user.setSurname(mSurnameEdit.getText().toString());
                if (mMaleEdit.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Введите пол", Toast.LENGTH_SHORT).show();
                else
                    user.setGender(mMaleEdit.getText().toString());
                if (mPhoneEdit.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Введите номер телефона", Toast.LENGTH_SHORT).show();
                else
                    user.setPhone(mPhoneEdit.getText().toString());
                if (mEmailEdit.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Введите e-mail", Toast.LENGTH_SHORT).show();
                else
                    user.setEmail(mEmailEdit.getText().toString());
                if (mPasswordEdit.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_SHORT).show();
                else
                    user.setPassword(mPasswordEdit.getText().toString());
                user.setPhoto("");

                register(user);
            }
        });

        return rootView;
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

    public void register(User user) {

        subscription.add(
                service.loginAction(new Request<>("users.register", user))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RequestResult>() {
                            @Override
                            public void call(RequestResult s) {
                                if (s.status.equals("success")) {
                                    FragmentManager manager = getActivity().getSupportFragmentManager();

                                    FragmentTransaction ft = manager.beginTransaction();
                                    ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.push_slide_in, R.anim.pop_slide_out);

                                    LogInFragment fragment = LogInFragment.getInstance();

                                    ft.replace(R.id.fragment_host, fragment, "loginFragment");
                                    manager.popBackStack("register", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    ft.addToBackStack("login");
                                    ft.commit();
                                } else
                                    Toast.makeText(getActivity(), "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Timber.d(throwable.toString());
                            }
                        })
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
