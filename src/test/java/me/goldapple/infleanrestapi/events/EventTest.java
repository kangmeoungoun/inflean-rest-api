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
    @Test
    @DisplayName("비즈니스 로직에 맞는 free 상태값 변경 테스트")
    void testFree(){
        //given
        Event event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();
        //when
        event.statusUpdate();
        //then
        assertThat(event.isFree()).isFalse();
    }

    @Test
    @DisplayName("비즈니스 로직에 맞는 offline 상태값 변경 테스트")
    void testOffline() {
        //given
        //장소가 있을경우
        Event event = Event.builder()
                .location("강남역 D2 스타텁 팩토리")
                .build();
        //when
        event.statusUpdate();
        //then
        assertThat(event.isOffline()).isTrue();

        //given
        //장소가 없을경우
        event = Event.builder().build();
        //when
        event.statusUpdate();
        //then
        assertThat(event.isOffline()).isFalse();
    }

}