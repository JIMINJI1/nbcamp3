package com.sparta.schedule.service;

import com.sparta.schedule.dto.CreateScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.dto.UpdateScheduleRequestDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service

public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    
    // 1. 일정 등록
    public ScheduleResponseDto createSchedule(CreateScheduleRequestDto requestDto){
        // dto -> entity
        Schedule schedule = new Schedule(
                requestDto.getUsername(),
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
    public Page<ScheduleResponseDto> getAllSchedules(Pageable pageable) {
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);
        return schedulePage.map(ScheduleResponseDto::new);
    }

    //  2-2. 일정 단건 조회
    public ScheduleResponseDto getScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId) .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));

        // entity -> responseDto
        return new ScheduleResponseDto(
                schedule.getScheduleId(),
                schedule.getUsername(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    //  3. 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long scheduleId, UpdateScheduleRequestDto requestDto) {
        // 해당 일정 DB 있는지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));

        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());

        Schedule updatedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(updatedSchedule);
    }

    //  4. 일정 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);

        if(schedule == null){
            throw new IllegalArgumentException("일정이 존재하지 않지 않습니다");
        }
        scheduleRepository.deleteById(scheduleId);

    }


}
