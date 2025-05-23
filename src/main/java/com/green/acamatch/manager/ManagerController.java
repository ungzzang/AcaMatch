package com.green.acamatch.manager;

import com.green.acamatch.config.model.ResultResponse;
import com.green.acamatch.manager.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academy-manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

//    // 특정 수업 학생들에게 등하원 문자 발송 API
//    @PostMapping("/send-attendance/class/{classId}")
//    public ResultResponse<String> sendAttendanceToClass(@RequestParam Long senderId,
//                                                        @PathVariable Long classId,
//                                                        @RequestParam String message) {
//        managerService.sendAttendanceNotificationByClass(senderId, classId, message);
//        return ResultResponse.<String>builder()
//                .resultMessage("수업 학생들에게 등하원 알림 문자 전송 완료.")
//                .resultData("수업 ID: " + classId)
//                .build();
//    }

    @GetMapping("GetAcademyCount")
    @Operation(summary = "월별 등록한 학원 수", description = "이번달, 지난달 넣어서 테스트 해보시면 됩니다.")
    public ResultResponse<List<GetAcademyCountRes>> getAcademyCount(@ParameterObject GetAcademyCountReq req) {
        List<GetAcademyCountRes> result = managerService.getAcademyCount(req);
        return ResultResponse.<List<GetAcademyCountRes>>builder()
                .resultMessage("출력 완료")
                .resultData(result)
                .build();
    }

    @GetMapping("GetUserCount")
    @Operation(summary = "월별 등록한 유저 수", description = "이번달, 지난달 넣어서 테스트 해보시면 됩니다.")
    public ResultResponse<List<GetUserCountRes>> getUserCount(@ParameterObject GetUserCountReq req) {
        List<GetUserCountRes> result = managerService.getUserCount(req);
        return ResultResponse.<List<GetUserCountRes>>builder()
                .resultMessage("출력 완료")
                .resultData(result)
                .build();
    }

    @GetMapping("GetAcademyCostCount")
    @Operation(summary = "월별 결제한 건 수", description = "이번달, 지난달 넣어서 테스트 해보시면 됩니다.")
    public ResultResponse<List<GetAcademyCostCountRes>> getAcademyCostCount(@ParameterObject GetAcademyCostCountReq req) {
        List<GetAcademyCostCountRes> result = managerService.getAcademyCostCount(req);
        return ResultResponse.<List<GetAcademyCostCountRes>>builder()
                .resultMessage("출력 완료")
                .resultData(result)
                .build();
    }

    @GetMapping("GetAcademyCostByUserId")
    @Operation(summary = "학원 관계자가 본인이 등록한 학원들의 결제 수", description = "이번달, 지난달 넣어서 테스트 해보시면 됩니다.")
    public ResultResponse<List<GetAcademyCostByUserIdRes>> getAcademyCostByUserId(@ParameterObject GetAcademyCostByUserIdReq req) {
        List<GetAcademyCostByUserIdRes> result = managerService.getAcademyCostByUserId(req);
        return ResultResponse.<List<GetAcademyCostByUserIdRes>>builder()
                .resultMessage("출력 완료")
                .resultData(result)
                .build();
    }

    @GetMapping("GetUserCountByUserId")
    @Operation(summary = "학원 관계자가 본인이 등록한 학원들의 수강생 수", description = "이번달, 지난달 넣어서 테스트 해보시면 됩니다.")
    public ResultResponse<List<GetUserCountByUserIdRes>> getUserCountByUserId(@ParameterObject GetUserCountByUserIdReq req) {
        List<GetUserCountByUserIdRes> result = managerService.getUserCountByUserId(req);
        return ResultResponse.<List<GetUserCountByUserIdRes>>builder()
                .resultMessage("출력 완료")
                .resultData(result)
                .build();
    }

    @GetMapping("GetUserInfoList")
    @Operation(summary = "학원 관계자 대시보드에 학생 등록 요청 API", description = "certification이 0이면 승인 X 상태, 1이면 승인 O 상태")
    public ResultResponse<List<GetUserInfoListRes>> GetUserInfoList(@ParameterObject GetUserInfoListReq req){
        List<GetUserInfoListRes> result = managerService.GetUserInfoList(req);
        return ResultResponse.<List<GetUserInfoListRes>>builder()
                .resultMessage("출력 성공")
                .resultData(result)
                .build();
    }

    @GetMapping("GetAcademyCostInfoByUserId/{userId}")
    @Operation(summary = "학원 관계자가 대시보드에서 월 수익? 보기")
    public ResultResponse<GetAcademyCostInfoByUserId> getAcademyCostInfoByUserId(@PathVariable long userId){
        GetAcademyCostInfoByUserId result = managerService.getAcademyCostInfoByUserId(userId);
        return ResultResponse.<GetAcademyCostInfoByUserId>builder()
                .resultMessage("조회 성공")
                .resultData(result)
                .build();
    }
}