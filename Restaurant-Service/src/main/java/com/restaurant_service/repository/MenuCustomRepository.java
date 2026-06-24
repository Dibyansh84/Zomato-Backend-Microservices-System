package com.restaurant_service.repository;

import com.restaurant_service.DTO.MenuResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class MenuCustomRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /* Get menus by restaurant*/
    public List<MenuResponseDTO> getMenusByRestaurantId(Long restaurantId) {
        MapSqlParameterSource params = new MapSqlParameterSource();


        String sql = " SELECT id, restaurant_id, menu_name, active " +
                " FROM public.menus AS m WHERE m.restaurant_id =:restaurantId  " +
                " ORDER BY id DESC ";
        params.addValue("restaurantId", restaurantId);
        log.info(" Fetching menus for restaurant: {} ", restaurantId);

        try {
            List<MenuResponseDTO> menuResponseDTOList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(MenuResponseDTO.class));
            log.debug("Found {} menus for restaurant: {}", menuResponseDTOList.size(), restaurantId);
            return menuResponseDTOList;
        } catch (DataAccessException e) {
            log.error("Error fetching menus for restaurant: {}", restaurantId, e);
            throw new RuntimeException("Failed to fetch menus for restaurant: " + restaurantId, e);
        }
    }
}
