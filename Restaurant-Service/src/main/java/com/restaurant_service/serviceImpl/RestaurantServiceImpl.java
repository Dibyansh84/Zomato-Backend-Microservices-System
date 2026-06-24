package com.restaurant_service.serviceImpl;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.restaurant_service.DTO.RestaurantRequestDTO;
import com.restaurant_service.DTO.RestaurantResponseDTO;
import com.restaurant_service.DTO.RestaurantSearchRequestDTO;
import com.restaurant_service.entity.Restaurant;
import com.restaurant_service.exception.DuplicateResourceException;
import com.restaurant_service.exception.ResourceNotFoundException;
import com.restaurant_service.repository.RestaurantCustomRepository;
import com.restaurant_service.repository.RestaurantJpaRepository;
import com.restaurant_service.service.RestaurantService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class RestaurantServiceImpl implements RestaurantService
{

    //Injecting JPA repository
    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private RestaurantCustomRepository restaurantCustomRepository;

    @Autowired
    private TemplateEngine templateEngine;


    /* Very important: Whenever data changes, clear cache */
    /* Similarly use @CacheEvict for: updateRestaurant(), deleteRestaurant().
    * Otherwise old cached data will continue coming from Redis. */
    @Override
    @Transactional
    @CacheEvict(
            value = "restaurantListCache",
            allEntries = true
    )
    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO restaurantRequest)
    {
        log.info("Creating restaurant : {} ", restaurantRequest.getRestaurantName());

        /*Check duplicate email*/
        if(restaurantJpaRepository.existsByEmail(restaurantRequest.getEmail()))
        {
            //If duplicate email exists then throw DuplicateResourceException
            throw new DuplicateResourceException("Restaurant email already exists");
        }

        //Creating restaurant entity object
        Restaurant restaurant = new Restaurant();

        //Copying data from Request DTO to entity
        BeanUtils.copyProperties(restaurantRequest, restaurant);

        //Saving restaurant data into the database
        Restaurant savedData = restaurantJpaRepository.save(restaurant);

        //Creating ResponseDTO object
        RestaurantResponseDTO responseDTO = new RestaurantResponseDTO();

        //Copying saved entity data into Response DTO
        BeanUtils.copyProperties(savedData, responseDTO);

        //returning response
        return responseDTO;

    }

    @Override
    @Cacheable(value = "restaurant", key = "#restaurantId")
    public RestaurantResponseDTO getRestaurantById(Long restaurantId)
    {
        log.info(" Fetching restaurant by id: {} ", restaurantId);
        RestaurantResponseDTO response = restaurantCustomRepository.getRestaurantById(restaurantId);

        if(response == null)
        {
            throw new ResourceNotFoundException("Restaurant not found");
        }
        return response;
    }
    /**
     * Returns a paginated list of restaurants.
     *
     * @param page page number (starts at 0)
     * @param size number of items per page
     * @return paginated restaurant list
     */

    @Override
    @Cacheable(value= "restaurantListCache", key="#page + '-'+ #size")
    public Page<RestaurantResponseDTO> getRestaurantList(int page, int size)
    {
        return restaurantCustomRepository.getRestaurantList(page, size);
    }

    @Override
    public List<RestaurantResponseDTO> searchRestaurants(RestaurantSearchRequestDTO request)
    {
        log.info(
                "Searching restaurants with filters : {}",
                request
        );

        //Fetch matching restaurants from the DB using search filters
        List<Restaurant> restaurants = restaurantCustomRepository.searchRestaurants(request);

        //Convert each Restaurant entity into a RestaurantResponseDTO using Stream.
        return restaurants.stream()
                .map(restaurant ->
                {
                    // Create an empty response object to hold the data
                    RestaurantResponseDTO responseDTO = new RestaurantResponseDTO();

                    // Copy fields from the current restaurant entity into the response object i.e., the object of RestuarantResponseDTO.
                    BeanUtils.copyProperties(restaurant, responseDTO);


                    // Return the filled response object for this restaurant
                    return responseDTO;
                }).toList(); // Collect all response objects into a final list

    }

    @Override
    public void generateRestaurantListPdf(HttpServletResponse response) throws Exception
    {
        try {
            log.info("Generating restaurant PDF report");
            //List of restaurants
            List<RestaurantResponseDTO> restaurantList = restaurantCustomRepository.getAllRestaurants();

            //Thymeleaf context
            Context context = new Context();
            //restaurants → List passed from backend
            context.setVariable("restaurants", restaurantList);

            String htmlContent = templateEngine.process("restaurant-list", context);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=restaurant-report.pdf");

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(response.getOutputStream());

            builder.run();
        }
        catch(Exception e)
        {
            log.error("Error generating PDF: {} ");
            e.getMessage();
            throw e;
        }

    }
}
