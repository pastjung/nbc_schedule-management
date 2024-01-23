package com.sparta.schedule.Dto;

import com.sparta.schedule.Entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Client 의 요청 데이터를 받아줄 ScheduleRequestDto 클래스
@Getter
@Setter
@NoArgsConstructor
public class ScheduleRequestDto {
    private String title;       // 일정 제목
    private String manager;     // 담당자
    private String date;        // 작성일
    private String password;    // 비밀번호
    private String contents;    // 일정 내용
}
