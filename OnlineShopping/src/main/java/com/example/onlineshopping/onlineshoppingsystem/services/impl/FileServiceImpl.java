package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.FileDTO;
import com.example.onlineshopping.onlineshoppingsystem.entities.file.File;
import com.example.onlineshopping.onlineshoppingsystem.repositories.FileRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class FileServiceImpl implements FileService {
    private final ModelMapper modelMapper;
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository, ModelMapper modelMapper) {
        this.fileRepository = fileRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FileDTO getFileById(String id) {
        File byId = fileRepository.getById(id);
        FileDTO dto = modelMapper.map(byId, FileDTO.class);
        return dto;
    }
}
