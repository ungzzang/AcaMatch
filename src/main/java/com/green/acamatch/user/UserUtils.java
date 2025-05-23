package com.green.acamatch.user;

import com.green.acamatch.config.CookieUtils;
import com.green.acamatch.config.constant.JwtConst;
import com.green.acamatch.config.exception.CustomException;
import com.green.acamatch.config.exception.ManagerErrorCode;
import com.green.acamatch.config.exception.UserErrorCode;
import com.green.acamatch.config.jwt.JwtTokenProvider;
import com.green.acamatch.config.jwt.JwtUser;
import com.green.acamatch.entity.user.User;
import com.green.acamatch.user.model.UserReportProjection;
import com.green.acamatch.user.model.UserSignInRes;
import com.green.acamatch.user.model.UserSignUpReq;
import com.green.acamatch.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final JwtConst jwtConst;

    public int checkDuplicate(String text, String type) {
        switch (type) {
            case "nick-name":
                if (userRepository.existsByNickName(text)) {
                    throw new CustomException(UserErrorCode.DUPLICATE_USER_NICKNAME);
                }
                break;
            case "email":
                if (userRepository.existsByEmail(text)) {
                    throw new CustomException(UserErrorCode.DUPLICATE_USER_EMAIL);
                }
                break;
            default:
                throw new CustomException(UserErrorCode.INCORRECT_DUPLICATE_CHECK_TYPE);
        }
        return 1;
    }

    public User generateUserByUserSignUpReq(UserSignUpReq req) {
        // 새로운 일반 로그인 계정 생성
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setUpw(passwordEncoder.encode(req.getUpw()));
        user.setBirth(req.getBirth());
        user.setPhone(req.getPhone());
        user.setNickName(req.getNickName());
        user.setUserRole(req.getUserRole());

        return user;
    }


    public UserSignInRes generateUserSignInResByUser(User user, HttpServletResponse response) {
        List<String > roles = new ArrayList<>();
        roles.add("ROLE_" + user.getUserRole().toString());
        JwtUser jwtUser = new JwtUser(user.getUserId(), roles);

        String accessToken = jwtTokenProvider.generateAccessToken(jwtUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(jwtUser);

        cookieUtils.setCookieToken(response, jwtConst.getRefreshTokenCookieName(), refreshToken, jwtConst.getRefreshTokenCookieExpiry());

        return UserSignInRes.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .userRole(user.getUserRole())
                .accessToken(accessToken)
                .build();
    }

    public User findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }

    public boolean isAllowedUser(long userId) {
        User user = findUserById(userId);
        return user.getUserRole().isAdminOrTeacherOrAcademy();
    }

    public void validateUserPermission(long userId) {
        if (!isAllowedUser(userId)) {
            throw new CustomException(ManagerErrorCode.PERMISSION_DENIED);
        }
    }

}
