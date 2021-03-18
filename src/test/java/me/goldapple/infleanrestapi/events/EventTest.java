package me.goldapple.infleanrestapi.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
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
    @ParameterizedTest
    @DisplayName("비즈니스 로직에 맞는 free 상태값 변경 테스트")
    @MethodSource("stringIntAndBooleanProvider")
    void testFree(int basePrice,int maxPrice,boolean isFree){
        //given
        System.out.println("basePrice = " + basePrice);
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();
        //when
        event.statusUpdate();
        //then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    @ParameterizedTest
    @DisplayName("비즈니스 로직에 맞는 offline 상태값 변경 테스트")
    @MethodSource("stringAndBooleanProvider")
    void testOffline(String location,boolean isOffline) {
        //given
        //장소가 있을경우
        Event event = Event.builder()
                .location(location)
                .build();
        //when
        event.statusUpdate();
        //then
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }


    static Stream<Arguments> stringIntAndBooleanProvider(){
        return Stream.of(
          Arguments.arguments(0,0,true),
          Arguments.arguments(100,0,false),
          Arguments.arguments(0,100,false)
        );
    }

    static Stream<Arguments> stringAndBooleanProvider(){
        return Stream.of(
          Arguments.arguments("강남역 D2 스타텁 팩토리",true),
          Arguments.arguments(null,false)
        );
    }



}