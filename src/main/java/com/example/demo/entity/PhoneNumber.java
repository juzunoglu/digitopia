package com.example.demo.entity;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Data
public class PhoneNumber {

    @NotEmpty
    private String value;

    @NotEmpty
    private String locale;
}
