package com.example.excursionPlanning.services.implementations;

import com.example.excursionPlanning.dao.MonumentRepository;
import com.example.excursionPlanning.dao.UserRepository;
import com.example.excursionPlanning.dto.ImageModelDTO;
import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.entity.ImageModel;
import com.example.excursionPlanning.entity.Monument;
import com.example.excursionPlanning.paginationandsorting.PageSettings;
import com.example.excursionPlanning.services.FileLoader;
import com.example.excursionPlanning.services.interfaces.ImageModelService;
import com.example.excursionPlanning.services.interfaces.MonumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MonumentServiceImpl implements MonumentService {

    private static final Logger LOG = LoggerFactory.getLogger(MonumentServiceImpl.class);

    private final MonumentRepository monumentRepository;

    private final ImageModelService imageModelService;

    @Autowired
    public MonumentServiceImpl(MonumentRepository monumentRepository, UserRepository userRepository, ImageModelService imageModelService) {
        this.monumentRepository = monumentRepository;
        this.imageModelService = imageModelService;
    }

    @Override
    public Optional<Monument> createMonument(MonumentDTO monumentDTO, Principal principal) {
        Monument monument = new Monument();

        monument.setTitle(monumentDTO.getTitle());
        monument.setDescription(monumentDTO.getDescription());
        monument.setPrice(monumentDTO.getPrice());
        monument.setCity(monumentDTO.getCity());

        Monument savedMonument = null;

        try {
            savedMonument = monumentRepository.save(monument);

            ImageModelDTO imageModel = new ImageModelDTO();
            imageModel.setMonumentId(savedMonument.getId());
            imageModel.setImageBytes(new FileLoader().loadFileAsBytes("image-icon.jpg"));

            imageModelService.createImageModel(imageModel,principal);
        } catch (Exception e) {
            LOG.error("Monument {} cannot be created!", e.getMessage());
        }
        LOG.info("New monument {} was added", savedMonument);
        return Optional.of(savedMonument);


    }

    @Override
    public void deleteMonument(Long id, Principal principal) {
        Monument monument = monumentRepository.getMonumentById(id)
                .orElseThrow(() -> new RuntimeException("Monument not exist"));
        try {
            monumentRepository.delete(monument);
        } catch (Exception e) {
            LOG.error("Monument {} cannot be deleted!", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Monument> getMonumentById(Long id, Principal principal) {
        return monumentRepository.getMonumentById(id);

    }

    @Override
    public Optional<Monument> putMonument(MonumentDTO monumentDTO, Principal principal) {

        Optional<Monument> monument = monumentRepository.getMonumentById(monumentDTO.getId());

        if (monument.isPresent()) {
            monument.get().setTitle(monumentDTO.getTitle());
            monument.get().setDescription(monumentDTO.getDescription());
            monument.get().setPrice(monumentDTO.getPrice());
            monument.get().setCity(monumentDTO.getCity());

            Monument savedMonument = null;
            try {
                savedMonument = monumentRepository.save(monument.get());
                return Optional.of(savedMonument);
            } catch (Exception e) {
                LOG.error("Monument {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    public Optional<Monument> patchMonument(MonumentDTO monumentDTO, Principal principal) {

        Optional<Monument> monument = monumentRepository.getMonumentById(monumentDTO.getId());

        if (monument.isPresent()) {
            if (monumentDTO.getTitle() != null)
                monument.get().setTitle(monumentDTO.getTitle());
            if (monumentDTO.getDescription() != null)
                monument.get().setDescription(monumentDTO.getDescription());
            if (monumentDTO.getPrice() != null)
                monument.get().setPrice(monumentDTO.getPrice());
            if (monumentDTO.getCity() != null)
                monument.get().setCity(monumentDTO.getCity());

            try {

                return Optional.of(monumentRepository.save(monument.get()));

            } catch (Exception e) {
                LOG.error("Monument {} cannot be updated!", e.getMessage());
            }

        }

        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getAllMonuments() {
        return monumentRepository.getAllMonuments();
    }

    @Override
    public List<Monument> getMonumentsByCity(String city) {
        return monumentRepository.getMonumentsByCity(city);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentByTitle(String title) {
        return monumentRepository.getMonumentByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentsByPrice(Long minPrice, Long maxPrice) {
        return monumentRepository.getMonumentsByPrice(minPrice, maxPrice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentsByAvgGrade(Integer avgGrade) {
        return monumentRepository.getMonumentsByAvgGrade(avgGrade);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentsByExcursionId(Long excursionId) {
        return monumentRepository.getMonumentsByExcursionId(excursionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getAllMonuments(PageSettings pageSetting) {

        Pageable page = PageRequest.of(pageSetting.getPage(),
                pageSetting.getSize());

        Page<Monument> resultPage = monumentRepository.getAllMonuments(page);
        return resultPage.getContent();
    }

    @Override
    public List<Monument> getMonumentsByCity(String city, PageSettings pageSetting) {

        Pageable page = PageRequest.of(pageSetting.getPage(),
                pageSetting.getSize());

        return monumentRepository.getMonumentsByCity(city, page).getContent();

    }


    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentsByTitle(String title, PageSettings pageSetting) {

        Pageable page = PageRequest.of(pageSetting.getPage(),
                pageSetting.getSize());

        return monumentRepository.getMonumentByTitle(title, page).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentsByPrice(Long minPrice, Long maxPrice, PageSettings pageSetting) {

        Pageable page = PageRequest.of(pageSetting.getPage(),
                pageSetting.getSize());

        return monumentRepository.getMonumentsByPrice(minPrice, maxPrice, page).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentsByAvgGrade(Integer avgGrade, PageSettings pageSetting) {

        Pageable page = PageRequest.of(pageSetting.getPage(),
                pageSetting.getSize());

        return monumentRepository.getMonumentsByAvgGrade(avgGrade, page).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentsByExcursionId(Long excursionId, PageSettings pageSetting) {

        Pageable page = PageRequest.of(pageSetting.getPage(),
                pageSetting.getSize());

        return monumentRepository.getMonumentsByExcursionId(excursionId, page).getContent();
    }

}
