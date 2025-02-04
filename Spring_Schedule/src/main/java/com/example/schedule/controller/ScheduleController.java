package com.example.schedule.controller;

import com.example.schedule.dto.FindAllResponseDto;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestdto) {
        Long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;

        Schedule schedule = new Schedule(
                scheduleId,
                requestdto.getTitle(),
                requestdto.getContent(),
                requestdto.getUsername(),
                requestdto.getPassword()
        );

        scheduleList.put(scheduleId, schedule);

        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FindAllResponseDto>> findAllSchedule(
            @RequestParam(required = false) String updatedDate,
            @RequestParam(required = false) String username
    ) {
        List<FindAllResponseDto> findAllScheduleList = new ArrayList<>();

        for (Schedule schedule : scheduleList.values()) {
            boolean matches = true;

            if (updatedDate != null && !schedule.getUpdatedDate().toLocalDate().toString().equals(updatedDate)) {
                matches = false;
            }

            if (username != null && !schedule.getUsername().equalsIgnoreCase(username)) {
                matches = false;
            }

            if (matches) {
                FindAllResponseDto responseDto = new FindAllResponseDto(schedule);
                findAllScheduleList.add(responseDto);
            }
        }

        findAllScheduleList.sort(Comparator.comparing(FindAllResponseDto::getUpdatedDate).reversed());

        return new ResponseEntity<>(findAllScheduleList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> choiceScheduleId(@PathVariable Long id) {
        Schedule schedule = scheduleList.get(id);

        if (schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updatePartSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto
    ) {
        Schedule schedule = scheduleList.get(id);

        if (schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getTitle() != null && dto.getPassword() != null &&
                dto.getContent() != null && dto.getUsername() != null) {
            if (!dto.getPassword().equals(schedule.getPassword())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            schedule.updatePart(dto);

            return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(
            @PathVariable Long id,
            @RequestParam Integer password
    ) {
        Schedule schedule = scheduleList.get(id);

        if (schedule != null && password.equals(schedule.getPassword())) {
            scheduleList.remove(id);
            return new ResponseEntity<>("정상: 선택된 일정이 삭제 되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("오류: 선택된 일정의 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }
    }
}
