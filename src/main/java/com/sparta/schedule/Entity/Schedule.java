package com.sparta.schedule.Entity;

import com.sparta.schedule.Dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private String title;       // 일정 제목
    private String manager;     // 담당자
    private String date;        // 작성일
    private String password;    // 비밀번호
    private String contents;     // 일정 내용

    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.manager = requestDto.getManager();
        this.date = requestDto.getDate();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
    }

    public void update(ScheduleRequestDto requestDto){
        if(password.equals(requestDto.getPassword())){
            this.title = requestDto.getTitle();
            this.manager = requestDto.getManager();
            this.contents = requestDto.getContents();
        }
    }
}