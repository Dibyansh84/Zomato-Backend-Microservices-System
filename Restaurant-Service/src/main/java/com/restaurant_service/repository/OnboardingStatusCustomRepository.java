package com.restaurant_service.repository;

import com.restaurant_service.DTO.OnboardingStatusResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class OnboardingStatusCustomRepository
{
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /* Fetch onboarding status*/
    public List<OnboardingStatusResponseDTO> getOnboardingStatus(Long restaurantId)
    {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = null;
        try
        {
            sql = " SELECT os.id, os.restaurant_id, r.restaurant_name, os.status, \n" +
                    "os.remarks, os.approved_by, os.approved_on, os.created_on, os.updated_on\n" +
                    "FROM public.onboarding_status as os\n" +
                    "LEFT JOIN public.restaurants r ON r.id = os.restaurant_id\n" +
                    "WHERE os.restaurant_id =:restaurantId ";
            params.addValue("restaurantId", restaurantId);
        }
        catch(Exception e)
        {
            e.getMessage();
        }
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(OnboardingStatusResponseDTO.class));
    }
}
