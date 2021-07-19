package com.api.dependencyInjector;

import com.api.common.KpBffUtil;
import com.api.common.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class UserProvider implements Provider<User> {

    @Inject
    private KpBffUtil bffUtil;

    @Inject
    @Named("envUrl")
    private String  envUrl;

    @Inject
    @Named("username")
    private String  username;

    @Inject
    @Named("password")
    private String  password;

    @Inject
    @Named("plateform")
    private String  plateform;

    @Inject
    @Named("plateformVersion")
    private String  plateformVersion;


    @Override
    public User get() {
        User user = bffUtil.signIn(plateform,username,password,false);
        return user;

    }
}
