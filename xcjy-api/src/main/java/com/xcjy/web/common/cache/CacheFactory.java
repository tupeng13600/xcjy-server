package com.xcjy.web.common.cache;

import com.xcjy.web.bean.School;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import com.xcjy.web.service.SchoolService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/7/25.
 */
@Component
public class CacheFactory {

    public static Map<String, School> schools;

    public static Map<String, UserModel> users = new ConcurrentHashMap<>();

    public static List<RoleEnum> backMoneyAuditRoleChain = new ArrayList<>(); //退费审核链  userId_num

    @Autowired
    private SchoolService schoolService;

    @PostConstruct
    public void init(){
        List<School> schoolList = schoolService.list();
        schools = schoolList.stream().collect(Collectors.toMap(School::getName, school -> school));
        backMoneyAuditRoleChain.add(RoleEnum.SCHOOLMASTER_BOSS);
    }

    public static void cache(User user) {
        UserModel userModel = new UserModel();
        if(null == user) {
            throw new EducationException("缓存的用户不可为空");
        }
        BeanUtils.copyProperties(user, userModel);
        users.put(userModel.getUsername(), userModel);
    }

}
