package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.domain.option.entity.OptionDetail;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Schema(description = "옵션 상세 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindOptionDetailResponse {

    @Schema(description = "옵션 이름")
    private String name;

    @Schema(description = "상세 설명")
    private String description;

    @Schema(description = "사진 주소")
    private String imgUrl;

    @Schema(description = "스펙")
    private List<FindSpecResponse> specs;

    public FindOptionDetailResponse(OptionDetail optionDetail) {
        this.name = optionDetail.getName();
        this.description = optionDetail.getDescription();
        this.imgUrl = optionDetail.getImgUrl();
        this.specs = optionDetail.getSpecs().stream()
                .map(FindSpecResponse::new)
                .collect(Collectors.toList());
    }

    @Builder
    public FindOptionDetailResponse(String name, String description, String imgUrl, List<FindSpecResponse> specs) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.specs = specs;
    }
}
