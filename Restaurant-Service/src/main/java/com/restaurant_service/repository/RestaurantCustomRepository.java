package com.restaurant_service.repository;

import com.restaurant_service.DTO.RestaurantResponseDTO;
import com.restaurant_service.DTO.RestaurantSearchRequestDTO;
import com.restaurant_service.entity.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class RestaurantCustomRepository
{
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public RestaurantResponseDTO getRestaurantById(Long restaurantId)
    {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "SELECT id, restaurant_name, owner_name, email, phone, city, address, cuisine_type, rating, active, created_on, \n" +
                "updated_on, onboarding_completed\n" +
                "FROM public.restaurants\n" +
                "WHERE id = :restaurantId ";
        params.addValue("restaurantId", restaurantId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(RestaurantResponseDTO.class));
    }

    public Page<RestaurantResponseDTO> getRestaurantList(int page, int size)
    {
        //Calculate how many rows to skip based on the current page
        int offset = page * size;

        //Fetch restaurants for the current page
        String sql =" SELECT id, restaurant_name,owner_name, email, phone, city, address,\n" +
                " cuisine_type, rating, active, created_on, updated_on, onboarding_completed\n" +
                " FROM restaurants ORDER BY id " +
                " LIMIT :size OFFSET :offset ";

        //Get total count for pagination
        String countSql = " SELECT COUNT(*) FROM restaurants ";

        // Set query parameters
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("size", size);
        params.addValue("offset", offset);

        //Run query and map results to DTO
        List<RestaurantResponseDTO> restaurantList = namedParameterJdbcTemplate.query(sql, params,
                new BeanPropertyRowMapper<>(RestaurantResponseDTO.class));

        //Get total count
        Long totalRecords = namedParameterJdbcTemplate.queryForObject(countSql, params, Long.class);

        //Build page request
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        //Return results with pagination info
        return new PageImpl<>(restaurantList, pageable, totalRecords);
    }

    public List<Restaurant> searchRestaurants(RestaurantSearchRequestDTO request)
    {
        MapSqlParameterSource params = new MapSqlParameterSource();

        /* When to use StringBuilder and WHERE 1=1 in query :
        Use 'WHERE 1=1' and 'StringBuilder' when filters are optional and unpredictable
        The classic example — a search page with multiple filters. The user might fill none, some or all of them.
        You don't know in advance which conditions to add.*/
        /* WHERE 1=1 is a dummy condition that's always true, so it never affects results.
        * Its only job is to make every real condition start with AND:
        * sql.append(" AND city = :city");
        * sql.append(" AND cuisine = :cuisine");*/
        StringBuilder sql = new StringBuilder(" SELECT * FROM restaurants WHERE 1=1 ");

        //Search by city
        if(request.getCity()!=null &&  !request.getCity().isBlank())
        {
            sql.append( " AND city ILIKE :city " );
            params.addValue("city", "%" + request.getCity()+ "%");
        }

        //Search by cuisine
        if(request.getCuisineType() !=null && !request.getCuisineType().isBlank())
        {
            sql.append(" AND cuisine_type ILIKE :cuisine ");
            params.addValue("cuisine", "%"+ request.getCuisineType()+"%");
        }

        //Search by rating
        if(request.getMinRating() !=null)
        {
            sql.append(" AND rating >= :rating ");
            params.addValue("rating", request.getMinRating());
        }

        log.info(" Search Query: {} ",sql);
        return namedParameterJdbcTemplate.query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Restaurant.class));
    }

    public List<RestaurantResponseDTO> getAllRestaurants()
    {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = " SELECT id, restaurant_name, owner_name, city, "+
                " cuisine_type, rating, active "+
                " FROM restaurants "+
                " ORDER BY id ";
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(RestaurantResponseDTO.class));
    }
}
