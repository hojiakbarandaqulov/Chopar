package org.example.config;

import org.example.dto.profile.ProfileDTO;
import org.example.entity.ProfileEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class CustomMapperConfig {
    public static ModelMapper customModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<ProfileEntity, ProfileDTO>() {
            @Override
            protected void configure() {
                map().setEmail(source.getEmail());  // Maxsus moslashtirish logikasi
            }
        });

        return modelMapper;
    }
}
