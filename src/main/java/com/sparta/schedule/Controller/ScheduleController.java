package com.sparta.schedule.Controller;

import com.sparta.schedule.Dto.ScheduleRequestDto;
import com.sparta.schedule.Dto.ScheduleResponseDto;
import com.sparta.schedule.Entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final Map<String, Schedule> scheduleList = new HashMap<>();   // DB 연결 대신 일정 데이터를 저장할 Java 컬렉션

    // 일정 생성 : POST http://localhost:8080/api/schedule/create  →  이후 Body-raw-jSON으로 변경후 {"" : "", ... } 형식의 JSON 파일 작성
    @PostMapping("/create")
    @ResponseBody
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto){
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // DB에 저장 로직(현재 Java 컬렉션으로 대체)
        scheduleList.put(schedule.getTitle(), schedule);

        // Entity -> RequestDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    // 선택 일정 조회 : GET http://localhost:8080/api/schedule/check/제목
    @GetMapping("/check/{title}")
    public ScheduleResponseDto getSchedule(@PathVariable String title) {
        // 해당 일정이 DB에 존재하는지 확인(현재 컬렉션으로 대체)
        if(scheduleList.containsKey(title)){
            // 선택한 일정 가져오기
            Schedule schedule = scheduleList.get(title);

            // Entity -> RequestDto
            ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

            return scheduleResponseDto;    
        }
        else {
            return null;
            // throw new 예외 처리;
        }
    }

    // 전체 일정 조회 : GET http://localhost:8080/api/schedule/allCheck
    @GetMapping("/allCheck")
    public List<ScheduleResponseDto> getAllSchedules() {
        // DB역할을 하는 scheduleList를 조회하여 List<ScheduleResponseDto>로 변환 후 반환
        List<ScheduleResponseDto> reponseList = scheduleList.values().stream()
                .map(ScheduleResponseDto::new)
                .sorted(Comparator.comparing(ScheduleResponseDto::getDate).reversed())
                .collect(Collectors.toList());
        return reponseList;
    }

    // 일정 수정 : PUT http://localhost:8080/api/schedule/modify/제목
    @PutMapping("/modify/{title}")
    @ResponseBody
    public ScheduleResponseDto modifySchedule(@PathVariable String title, @RequestBody ScheduleRequestDto requestDto) {
        // 해당 일정이 DB에 존재하는지 확인(현재 컬렉션으로 대체)
        if(scheduleList.containsKey(title)) {
            // 선택한 일정 가져오기
            Schedule schedule = scheduleList.get(title);

            // DB 수정
            schedule.update(requestDto);
            scheduleList.remove(title);
            scheduleList.put(schedule.getTitle(), schedule);

            // Entity -> RequestDto
            ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

            System.out.println("성공");
            return scheduleResponseDto;
        }
        else {
            return null;
            // throw new 예외 처리;
        }
    }

    // 일정 삭제 : DELETE http://localhost:8080/api/schedule/delete
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // 해당 일정이 DB에 존재하는지 확인(현재 컬렉션으로 대체)
        if(scheduleList.containsKey(requestDto.getTitle())) {
            // 선택한 일정 가져오기
            Schedule schedule = scheduleList.get(requestDto.getTitle());

            // 비밀번호 확인
            if(schedule.getPassword().equals(requestDto.getPassword())){
                // DB에서 삭제
                scheduleList.remove(requestDto.getTitle());
            }
        }
    }
}
