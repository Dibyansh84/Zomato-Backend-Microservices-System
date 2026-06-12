package com.restaurant_service.repository;

import com.restaurant_service.DTO.RestaurantDocumentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantDocumentCustomRepository
{
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<RestaurantDocumentResponseDTO> getDocuments(Long restaurantId)
    {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = " SELECT rd.id, r.restaurant_name, rd.restaurant_id, rd.document_type, rd.document_url, rd.verified\n" +
                " FROM public.restaurant_documents AS rd\n" +
                " LEFT JOIN restaurants AS r ON rd.restaurant_id = r.id "+
                " WHERE rd.restaurant_id =:restaurantId " +
                " ORDER BY id DESC ";
        params.addValue("restaurantId", restaurantId);
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(RestaurantDocumentResponseDTO.class));
    }
}
