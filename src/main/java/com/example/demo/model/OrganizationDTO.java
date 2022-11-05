package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.Normalizer;
import java.util.Date;
import java.util.Set;

public record OrganizationDTO(

        @NotBlank(message = "Organization name cannot be null or empty")
        @Size(min = 3, max = 30, message = "Organization name must be in between 3 and 30 characters")
        @Schema(description = "Organization name",
                name = "organizationName",
                required = true,
                type = "string",
                example = "DIGITOPIA")
        String organizationName,

        @Schema(description = "Registry number of the Organization",
                name = "registryNumber",
                required = true,
                example = "DIG12",
                type = "string")
        String registryNumber,

        @Schema(description = "Contact email of the organization",
                name = "concatEmail",
                required = true,
                example = "digitopia@gmail.com")
        @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
        String concatEmail,

        @Schema(description = "The year founded of the Organization",
                name = "yearFounded",
                required = false,
                example = "2017-07-21",
                type = "string",
                format = "date"
        )
        Date yearFounded,

        @Schema(description = "The phone number of the Organization",
                name = "phone",
                required = false,
                example = "+905527894854",
                type = "string"
        )
        // todo boşluklu tel kabul etmedi, buna bi bak fazladan slash var çok büyük ihtimalle
        @Pattern(regexp = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$", message = "Please enter a valid phone number")
        String phone,

        @Schema(description = "The size of the organization",
                name = "companySize",
                required = false,
                example = "50",
                type = "number"
        )
        Long companySize,

        @ArraySchema(
                schema = @Schema(
                        description = "List of users that belongs to the organization",
                        required = false,
                        implementation = UserDTO.class),
                uniqueItems = true
        )
        Set<UserDTO> users

) {

    public static String normalizeCompanyName(String companyName) { // todo?
        return Normalizer.normalize(companyName, Normalizer.Form.NFD)
                .replaceAll("\\d", "") // remove all numbers
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase(); // remove accents

    }
}
