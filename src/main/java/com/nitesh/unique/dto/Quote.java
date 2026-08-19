package com.nitesh.unique.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    public record quote(String type, Value value)
    {

    }
    public record value(Long id ,String quote){

    }

}
