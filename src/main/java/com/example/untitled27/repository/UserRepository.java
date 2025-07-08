package com.example.untitled27.repository;

import com.example.untitled27.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (resultSet, rowNum) -> {
        User user = User.builder()
                .id(resultSet.getInt("id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .build();

        return user;
    };

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, username);
        } catch (EmptyResultDataAccessException e) {
            return null; // 사용자의 입력오류를 방지하기위해 에러처리 를 한다.
        }
    }




    public int save(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    } // 원래 정석은 패스워드 그래도 받아오는것이 아니라 암호화해서 받아와야 한다.
}
