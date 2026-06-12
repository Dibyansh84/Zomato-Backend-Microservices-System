package com.restaurant_service.repository;

import com.restaurant_service.DTO.FoodItemResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class FoodItemCustomRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /* Get food items by menu id*/
    public List<FoodItemResponseDTO> getFoodItems(Long menuId)
    {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = " SELECT id, menu_id, item_name, description, category, is_veg, price, available, rating, " +
                " created_on, updated_on FROM public.food_items AS fi" +
                " WHERE fi.menu_id = :menuId " +
                " ORDER BY id DESC " ;
        params.addValue("menuId", menuId);

        log.info(" Fetching food items for menu:{} ", menuId);
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(FoodItemResponseDTO.class));
    }
}
