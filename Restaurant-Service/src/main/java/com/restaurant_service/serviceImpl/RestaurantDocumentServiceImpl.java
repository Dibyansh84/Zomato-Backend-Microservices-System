package com.restaurant_service.serviceImpl;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.restaurant_service.DTO.RestaurantDocumentRequestDTO;
import com.restaurant_service.DTO.RestaurantDocumentResponseDTO;
import com.restaurant_service.entity.RestaurantDocument;
import com.restaurant_service.exception.ResourceNotFoundException;
import com.restaurant_service.repository.RestaurantDocumentCustomRepository;
import com.restaurant_service.repository.RestaurantDocumentJpaRepository;
import com.restaurant_service.repository.RestaurantJpaRepository;
import com.restaurant_service.service.RestaurantDocumentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@Slf4j
public class RestaurantDocumentServiceImpl implements RestaurantDocumentService
{
    //Restaurant JPA repository
    private final RestaurantJpaRepository restaurantJpaRepository;

    //Restaurant document JPA repository
    private final RestaurantDocumentJpaRepository restaurantDocumentJpaRepository;

    //Restaurant document custom repository
    private final RestaurantDocumentCustomRepository restaurantDocumentCustomRepository;
    public RestaurantDocumentServiceImpl(RestaurantJpaRepository restaurantJpaRepository,
                                         RestaurantDocumentJpaRepository restaurantDocumentJpaRepository, RestaurantDocumentCustomRepository restaurantDocumentCustomRepository)
    {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.restaurantDocumentJpaRepository = restaurantDocumentJpaRepository;
        this.restaurantDocumentCustomRepository = restaurantDocumentCustomRepository;
    }

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    @Transactional
    @CacheEvict(value = "restaurantDocumentCache", allEntries = true)
    public RestaurantDocumentResponseDTO uploadDocument(RestaurantDocumentRequestDTO request)
    {
        /* Validate restaurant*/
        if(!restaurantJpaRepository.existsById(request.getRestaurantId()))
        {
            throw new ResourceNotFoundException(" Restaurant not found ");
        }

        RestaurantDocument document = new RestaurantDocument();
        BeanUtils.copyProperties(request, document);

        RestaurantDocument savedDocument = restaurantDocumentJpaRepository.save(document);
        RestaurantDocumentResponseDTO responseDTO = new RestaurantDocumentResponseDTO();

        BeanUtils.copyProperties(savedDocument, responseDTO);

        return responseDTO;
    }

    @Override
    public List<RestaurantDocumentResponseDTO> getDocuments(Long restaurantId)
    {
        return restaurantDocumentCustomRepository.getDocuments(restaurantId);
    }

    @Override
    public void generateRestaurantDocumentListPdf(HttpServletResponse response, Long restaurantId) throws Exception
    {
        try
        {
            //Get documents info by restaurant id
            List<RestaurantDocumentResponseDTO> documentList = restaurantDocumentCustomRepository.getDocuments(restaurantId);

            //Thymeleaf Context
            Context context = new Context();
            //restaurant documents ---> Passed from backend
            context.setVariable("restaurantDoc", documentList);

            String htmlContent = templateEngine.process("restaurant-documents", context);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=restaurantDocReport.pdf");

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(response.getOutputStream());
            builder.run();

        }
        catch (Exception e)
        {
            log.error("Error generating PDF: {} ");
            e.getMessage();
            throw e;
        }
    }
}
