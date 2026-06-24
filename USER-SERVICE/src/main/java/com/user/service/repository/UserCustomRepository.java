package com.user.service.repository;

import com.user.service.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class UserCustomRepository
{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public User getUserByUserId(Long userId)
    {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = " SELECT id, name, email, phone, address " +
                " FROM public.users WHERE id = :userId ";
        params.addValue("userId", userId);
        return jdbcTemplate.queryForObject(sql, params,
                (rs, rowNum) ->{
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            return user;
                });
    }

    //Method to fetch list of users
    public List<User> getUsers()
    {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = " SELECT id, name, email, phone, address\n" +
                "FROM public.users ";
        return jdbcTemplate.query(sql, params, (rs, rowNum) ->{
            //Creating user object
            User user = new User();

            //Setting database values into entity
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));

            return user;
        });
    }
}
