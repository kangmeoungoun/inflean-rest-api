package me.goldapple.infleanrestapi.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EventTest{

    @Test
    @DisplayName("롬복 플러그인 builder 테스트")
    void builder(){
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API developement")
                .build();
        assertThat(event).isNotNull();
    }
    @Test
    @DisplayName("Java Bean 테스트")
    void javaBean(){
        //given
        String name = "Event";
        String spring = "Spring";
        //when
        Event event = new Event();
        event.setName(name);
        event.setDescription(spring);
        //then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(spring);
    }
}