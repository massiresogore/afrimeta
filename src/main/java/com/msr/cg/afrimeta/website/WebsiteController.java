package com.msr.cg.afrimeta.website;

import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import com.msr.cg.afrimeta.website.converter.WebsiteDtoToWebsiteConverter;
import com.msr.cg.afrimeta.website.converter.WebsiteToWebsiteDtoConverter;
import com.msr.cg.afrimeta.website.dto.WebsiteDto;
import com.msr.cg.afrimeta.website.dto.WebsiteRequest;
import com.msr.cg.afrimeta.website.dto.WebsiteResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/website")
public class WebsiteController {

    private final WebsiteService websiteService;
    private final WebsiteDtoToWebsiteConverter websiteDtoToWebsite;
    private final WebsiteToWebsiteDtoConverter websiteToWebsiteDto;


    public WebsiteController(WebsiteService websiteService, WebsiteDtoToWebsiteConverter websiteDtoToWebsite, WebsiteToWebsiteDtoConverter websiteToWebsiteDto) {
        this.websiteService = websiteService;
        this.websiteDtoToWebsite = websiteDtoToWebsite;
        this.websiteToWebsiteDto = websiteToWebsiteDto;
    }



    @GetMapping
    public Result getAllWebsites()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "tous les websites",
                this.websiteService
                        .findAll().stream().map(magasin -> new WebsiteResponse(
                                magasin.getWebsiteId(),
                                magasin.getWebsiteUrl(),
                                magasin.getMagasin().getMagasinId()
                        )).toList()
        );
    }

    @GetMapping("/{websiteId}")
    public Result getWebsiteById(@PathVariable("websiteId") String websiteId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "website trouvé",
                this.websiteToWebsiteDto
                        .websiteResponse(this.websiteService
                                .findById(Long.parseLong(websiteId))));
    }

    @PatchMapping("/{websiteId}")
    public Result updateWebsite(@PathVariable("websiteId") String websiteId, @RequestBody WebsiteDto websiteDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "website mis à jour",
                this.websiteToWebsiteDto
                        .websiteResponse(this.websiteService
                                .update(this.websiteDtoToWebsite
                                        .convert(websiteDto),Long.parseLong(websiteId)
                                )
                        )
        );
    }

    @DeleteMapping("/{websiteId}")
    public Result deleteWebsite(@PathVariable("websiteId") String websiteId){
        this.websiteService.deleteById(Long.parseLong(websiteId));
        return new Result(true,StatusCode.SUCCESS,"website supprimé");
    }

    @PostMapping("/{magasinId}")
    public Result saveWebsite(WebsiteRequest websiteRequest){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "website cré",
                this.websiteToWebsiteDto
                        .websiteResponse(this.websiteService
                                .save(this.websiteDtoToWebsite
                                        .convert(websiteRequest)))
        );
    }/* @PostMapping
    public Result saveWebsite(@RequestBody WebsiteDto websiteDto){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "website cré",
                this.websiteToWebsiteDto
                        .websiteResponse(this.websiteService
                                .save(this.websiteDtoToWebsite
                                        .convert(websiteDto)))
        );
    }*/


}
