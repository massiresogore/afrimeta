package com.msr.cg.afrimeta.website.converter;

import com.msr.cg.afrimeta.website.Website;
import com.msr.cg.afrimeta.website.dto.WebsiteDto;
import com.msr.cg.afrimeta.website.dto.WebsiteResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WebsiteToWebsiteDtoConverter implements Converter<Website, WebsiteDto> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public WebsiteDto convert(Website source) {
        return new WebsiteDto(
                source.getWebsiteId(),
                source.getWebsiteUrl(),
                source.getMagasin()
        );

    }

    public WebsiteResponse websiteResponse(Website source){
        return new WebsiteResponse(
                source.getWebsiteId(),
                source.getWebsiteUrl(),
                source.getMagasin().getMagasinId()
        );
    }
}
