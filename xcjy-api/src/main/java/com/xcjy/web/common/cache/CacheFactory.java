package com.xcjy.web.common.cache;

import com.xcjy.web.bean.School;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import com.xcjy.web.service.SchoolService;
import com.xcjy.web.service.UserService;
import org.apache.commons.collections.CollectionUtils;
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

    public static Map<String, School> idSchools;

    public static Map<String, School> nameSchools;

    public static Map<String, UserModel> usernameUsers = new ConcurrentHashMap<>();

    public static Map<String, UserModel> userIdUsers = new ConcurrentHashMap<>();

    public static Map<String, UserModel> empIdUsers = new ConcurrentHashMap<>();

    public static List<RoleEnum> backMoneyAuditRoleChain = new ArrayList<>(); //退费审核链

    public static List<RoleEnum> changeSchoolAuditRoleChain = new ArrayList<>(); //转校审核链

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        List<School> schoolList = schoolService.list();
        nameSchools = schoolList.stream().collect(Collectors.toMap(School::getName, school -> school));
        idSchools = schoolList.stream().collect(Collectors.toMap(School::getId, school -> school));
        backMoneyAuditRoleChain.add(RoleEnum.SCHOOLMASTER_BOSS);
        List<User> users = userService.getAll();
        if(CollectionUtils.isNotEmpty(users)) {
            users.forEach(user -> {
                cacheIdUsers(user);
                cacheUsernameUsers(user);
                cacheEmpIdUsers(user);
            });
        }
    }

    /**
     * 更新用户相关缓存
     * @param user
     */
    public static void updateUserCache(User user) {
        cacheEmpIdUsers(user);
        cacheUsernameUsers(user);
        cacheIdUsers(user);
    }

    public static void removeUserCache(User user) {
        empIdUsers.remove(user.getEntityId());
        userIdUsers.remove(user.getId());
        usernameUsers.remove(user.getUsername());
    }

    public static void cacheUsernameUsers(User user) {
        UserModel userModel = new UserModel();
        if (null == user) {
            throw new EducationException("缓存的用户不可为空");
        }
        BeanUtils.copyProperties(user, userModel);
        usernameUsers.put(userModel.getUsername(), userModel);
    }

    public static void cacheIdUsers(User user) {
        UserModel userModel = new UserModel();
        if (null == user) {
            throw new EducationException("缓存的用户不可为空");
        }
        BeanUtils.copyProperties(user, userModel);
        userIdUsers.put(userModel.getId(), userModel);
    }

    public static void cacheEmpIdUsers(User user) {
        UserModel userModel = new UserModel();
        if (null == user) {
            throw new EducationException("缓存的用户不可为空");
        }
        BeanUtils.copyProperties(user, userModel);
        empIdUsers.put(userModel.getEntityId(), userModel);
    }

    public static RoleEnum getNextBackMoneyProcess(Integer index) {
        try {
            return null == index ? backMoneyAuditRoleChain.get(0) : backMoneyAuditRoleChain.get(index + 1);
        } catch (Exception e) {
            return null;
        }
    }

    public static RoleEnum getNextChangeSchoolProcess(Integer index) {
        try {
            return null == index ? changeSchoolAuditRoleChain.get(0) : changeSchoolAuditRoleChain.get(index + 1);
        } catch (Exception e) {
            return null;
        }
    }

}
