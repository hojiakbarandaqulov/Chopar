package org.example.config;

import org.modelmapper.ModelMapper;

public class MapperConfig {
    public static ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
