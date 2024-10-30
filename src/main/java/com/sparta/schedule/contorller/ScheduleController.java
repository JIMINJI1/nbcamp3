package com.sparta.schedule.contorller;

import com.sparta.schedule.dto.CreateScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.dto.ScheduleWithCommentResponseDto;
import com.sparta.schedule.dto.UpdateScheduleRequestDto;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    //  1.일정 등록
    @PostMapping("")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody CreateScheduleRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(requestDto, userDetails.getUser()));

    }

    //  2.일정 전체 조회 (페이징 추가)
    @GetMapping("")
    public ResponseEntity<Page<ScheduleResponseDto>> getAllSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<ScheduleResponseDto> schedules = scheduleService.getAllSchedules(pageable, userDetails.getUser());
        return ResponseEntity.ok(schedules);
    }

    //  3.일정 단건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleWithCommentResponseDto> getScheduleById(@PathVariable Long scheduleId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(scheduleService.getScheduleById(scheduleId, userDetails.getUser()));
    }

    //  4.일정 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long scheduleId, @Valid @RequestBody UpdateScheduleRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, requestDto, userDetails.getUser()));
    }

    //  5.일정 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.deleteSchedule(scheduleId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}


