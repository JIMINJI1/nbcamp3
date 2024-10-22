package com.sparta.schedule.contorller;

import com.sparta.schedule.dto.CreateScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.dto.ScheduleWithCommentResponseDto;
import com.sparta.schedule.dto.UpdateScheduleRequestDto;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    //  1.일정 등록
    @PostMapping("")
    public ScheduleResponseDto createSchedule(@Valid @RequestBody CreateScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }

    //  2.일정 전체 조회 (페이징 추가)
    @GetMapping("")
    public Page<ScheduleResponseDto> getAllSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        return scheduleService.getAllSchedules(pageable);
    }

    //  3.일정 단건 조회
    @GetMapping("/{scheduleId}")
    public ScheduleWithCommentResponseDto getScheduleById(@PathVariable Long scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    //  4.일정 수정
    @PutMapping("/{scheduleId}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long scheduleId, @Valid @RequestBody UpdateScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(scheduleId, requestDto);
    }

    //  5.일정 삭제
    @DeleteMapping("/{scheduleId}")
    public void deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }
}


