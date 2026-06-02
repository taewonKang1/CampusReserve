package com.campusreserve.service;

import java.sql.SQLException;

import com.campusreserve.dao.UserDAO;
import com.campusreserve.dto.UserDTO;
import com.campusreserve.util.PasswordUtil;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public void register(String loginId, String password, String name, String phone) throws SQLException {
        validateLoginId(loginId);
        validatePassword(password);
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력하세요.");
        }
        if (userDAO.existsByLoginId(loginId)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        userDAO.insert(loginId, PasswordUtil.hash(password), name.trim(), normalize(phone), "MEMBER");
    }

    public UserDTO login(String loginId, String password) throws SQLException {
        UserDTO user = userDAO.findByLoginId(loginId);
        if (user == null || !PasswordUtil.verify(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return user;
    }

    private void validateLoginId(String loginId) {
        if (loginId == null || !loginId.matches("^[A-Za-z0-9_]{4,20}$")) {
            throw new IllegalArgumentException("아이디는 영문, 숫자, 밑줄 4~20자로 입력하세요.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 72) {
            throw new IllegalArgumentException("비밀번호는 8~72자로 입력하세요.");
        }
    }

    private String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
