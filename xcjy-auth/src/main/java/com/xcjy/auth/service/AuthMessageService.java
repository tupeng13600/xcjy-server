package com.xcjy.auth.service;



import com.xcjy.auth.model.UpcLoginSuccessModel;
import com.xcjy.auth.model.UpcUser;
import com.xcjy.auth.token.UpcToken;

import java.util.Set;

/**
 * Created by tupeng on 2017/7/17.
 */
public interface AuthMessageService {

    UpcUser getUser(String username);

    Set<String> getRole(String username);

    void saveUserMessage(UpcLoginSuccessModel model);

}
