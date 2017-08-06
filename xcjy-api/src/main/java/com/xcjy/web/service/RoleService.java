package com.xcjy.web.service;

import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.controller.res.RoleRes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by tupeng on 2017/7/22.
 */
@Service
public class RoleService {

    public Set<RoleEnum> getRoleByIds(List<String> roleIds) {
        return RoleEnum.getRoleList(roleIds);
    }

    public List<RoleRes> listAll() {
        List<RoleRes> result = new ArrayList<>();
        List<RoleEnum> roleEnums = RoleEnum.getAll();
        roleEnums.forEach(roleEnum -> result.add(new RoleRes(roleEnum.name(), roleEnum.getName())));
        return result;
    }
}
