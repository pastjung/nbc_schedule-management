package com.sparta.schedule.Dto;

import com.sparta.schedule.Entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Client 에 데이터를 반환할 때 사용할 ScheduleResponseDto 클래스
@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {
    private String title;       // 일정 제목
    private String manager;     // 담당자
    private String date;        // 작성일
    private String contents;    // 일정 내용

    public ScheduleResponseDto(Schedule schedule) {
        this.title = schedule.getTitle();
        this.manager = schedule.getManager();
        this.date = schedule.getDate();
        this.contents = schedule.getContents();
    }
}
