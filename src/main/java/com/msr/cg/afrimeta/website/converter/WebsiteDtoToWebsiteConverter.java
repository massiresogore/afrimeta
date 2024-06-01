package com.msr.cg.afrimeta.website.converter;

import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.magasin.MagasinService;
import com.msr.cg.afrimeta.website.Website;
import com.msr.cg.afrimeta.website.dto.WebsiteDto;
import com.msr.cg.afrimeta.website.dto.WebsiteRequest;
import com.msr.cg.afrimeta.website.dto.WebsiteResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WebsiteDtoToWebsiteConverter implements Converter<WebsiteDto, Website> {
    private final MagasinService magasinService;

    public WebsiteDtoToWebsiteConverter(MagasinService magasinService) {
        this.magasinService = magasinService;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Website convert(WebsiteDto source) {

        return new Website(
                source.websiteId(),
                source.websiteUrl(),
                source.magasin()
        );
    }

    public WebsiteResponse WebsiteResponse(Website source){
        return new WebsiteResponse(
                source.getWebsiteId(),
                source.getWebsiteUrl(),
                source.getMagasin().getMagasinId()
        );
    }

    public Website convert(WebsiteRequest websiteRequest) {
        return new Website(websiteRequest.websiteUrl(), this.magasinService.findById(Long.parseLong(websiteRequest.magasinId())));
    }
}
