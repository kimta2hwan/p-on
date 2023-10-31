package com.wanyviny.user.domain.user.service;

import com.wanyviny.user.domain.user.dto.UserSignUpDto;
import com.wanyviny.user.domain.user.entity.User;

public interface UserService {

    User getUserProfile(Long id);

    void signUp(UserSignUpDto userSignUpDto, Long id);

}