package com.sparta.schedule.service;

import com.sparta.schedule.dto.*;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service

public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 1. 일정 등록
    public ScheduleResponseDto createSchedule(CreateScheduleRequestDto requestDto, User user) {
        // dto -> entity
        Schedule schedule = new Schedule(
                // 주어진 ID로 일정 조회, 없으면 예외 발생
                userRepository.findById(user.getUserId()).orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다")),
                requestDto.getTitle(),
                requestDto.getContent()
        );

        // DB 저장
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // entity -> responseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(savedSchedule);

        return scheduleResponseDto;

    }


    //  2-1. 일정 전체 조회 (페이징 추가)
    public Page<ScheduleResponseDto> getAllSchedules(Pageable pageable, User user) {
        // 모든 일정 페이징 처리하여 가져옴
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);
        // entity -> responsedto
        return schedulePage.map(ScheduleResponseDto::new);
    }

    //  2-2. 일정 단건 조회
    public ScheduleWithCommentResponseDto getScheduleById(Long scheduleId, User user) {
        // 주어진 ID로 일정 조회, 없으면 예외 발생
        Schedule schedule = findSchedule(scheduleId);

        // Schedule에 있는 댓글 CommentResponseDto로 변환
        List<CommentResponseDto> commentDtos = schedule.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        // entity -> responseDto
        return new ScheduleWithCommentResponseDto(
                schedule.getScheduleId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt(),
                commentDtos
        );
    }

    //  3. 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long scheduleId, UpdateScheduleRequestDto requestDto, User user) {
        // 해당 일정 DB 있는지 확인
        Schedule schedule = findSchedule(scheduleId);

        // 제목, 내용으로 수정
        if (requestDto.getTitle() != null) {
            if (requestDto.getTitle().isBlank()) {
                throw new IllegalArgumentException("일정 제목을 입력해 주세요");
            }
            schedule.updateTitleSchedule(requestDto.getTitle());
        }

        if (requestDto.getContent() != null) {
            if (requestDto.getContent().isBlank()) {
                throw new IllegalArgumentException("일정 내용을 입력해 주세요");
            }
            schedule.updateContentSchedule(requestDto.getContent());
        }

        // 수정된 Schedule을 DB 저장
        Schedule updatedSchedule = scheduleRepository.save(schedule);

        // Response DTO로 변환하여 반환
        return new ScheduleResponseDto(updatedSchedule);
    }

    //  4. 일정 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId, User user) {
        // 주어진 ID로 일정 조회, 없으면 예외 발생
        Schedule schedule = findSchedule(scheduleId);

        // 일정 삭제
        scheduleRepository.delete(schedule);

    }

    // 일정 존재 확인 메소드
    public Schedule findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));
    }

}


