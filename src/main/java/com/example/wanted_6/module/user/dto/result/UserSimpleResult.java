package com.example.wanted_6.module.user.dto.result;

import com.example.wanted_6.module.user.entity.Users;
import lombok.Data;

@Data
public class UserSimpleResult {
    private Long id;
    private String nickname;

    public static UserSimpleResult of(Users entity) {
        UserSimpleResult result = new UserSimpleResult();
        result.id = entity.getId();
        result.nickname = entity.getNickname();
        return result;
    }
}
