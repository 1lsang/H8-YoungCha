package team.youngcha.domain.option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.common.enums.AgeRange;
import team.youngcha.common.enums.Gender;
import team.youngcha.domain.category.enums.SelectiveCategory;
import team.youngcha.domain.option.dto.FindGuideOptionResponse;
import team.youngcha.domain.option.dto.GuideInfo;
import team.youngcha.domain.option.service.OptionService;

import java.util.List;

@Tag(name = "Option Guide", description = "옵션 가이드 API")
@RestController
@RequestMapping("/car-make/{trimId}/guide")
@RequiredArgsConstructor
public class GuideOptionController {

    private final OptionService optionService;

    @Operation(summary = "파워 트레인 가이드 모드 옵션 조회",
            description = "가이드 모드에서 파워 트레인의 옵션을 유사 사용자 선택량과 함께 조회합니다.")
    @GetMapping("/power-train")
    public ResponseEntity<SuccessResponse<List<FindGuideOptionResponse>>> findGuidePowerTrains(
            @Schema(description = "트림 아이디")
            @PathVariable Long trimId,
            @Schema(description = "성별 (남자: 0, 여자: 1, 선택 안함: 2)")
            @RequestParam Gender gender,
            @Schema(description = "나이 (20대 ~ 70대 이상, 2, 3,..., 7)")
            @RequestParam(name = "age") AgeRange ageRange,
            @Schema(description = "1순위 키워드 아이디")
            @RequestParam Long keyword1Id,
            @Schema(description = "2순위 키워드 아이디")
            @RequestParam Long keyword2Id,
            @Schema(description = "3순위 키워드 아이디")
            @RequestParam Long keyword3Id
    ) {
        GuideInfo guideInfo = new GuideInfo(gender, ageRange, List.of(keyword1Id, keyword2Id, keyword3Id));
        List<FindGuideOptionResponse> findGuideOptionResponses = optionService
                .findGuideOptions(trimId, guideInfo, SelectiveCategory.POWER_TRAIN);
        SuccessResponse<List<FindGuideOptionResponse>> successResponse =
                new SuccessResponse<>(findGuideOptionResponses);
        return ResponseEntity.ok(successResponse);
    }
}
