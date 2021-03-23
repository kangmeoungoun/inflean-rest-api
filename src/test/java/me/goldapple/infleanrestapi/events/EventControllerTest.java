package me.goldapple.infleanrestapi.events;

import me.goldapple.infleanrestapi.accounts.Account;
import me.goldapple.infleanrestapi.accounts.AccountRepository;
import me.goldapple.infleanrestapi.accounts.AccountRole;
import me.goldapple.infleanrestapi.accounts.AccountService;
import me.goldapple.infleanrestapi.common.AppProperties;
import me.goldapple.infleanrestapi.common.BaseControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class EventControllerTest extends BaseControllerTest{

    @Autowired
    EventRepository eventRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AppProperties appProperties;
    @BeforeEach
    void setUp(){
        eventRepository.deleteAll();
        accountRepository.deleteAll();
    }


    @Test
    @DisplayName("정상적으로 이벤트를 생성하는 테스트")
    void createEvent() throws Exception{
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021 , Month.MARCH , 15 , 14 , 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2021 , Month.MARCH , 16 , 14 , 21))
                .beginEventDateTime(LocalDateTime.of(2021 , Month.MARCH , 17 , 14 , 21))
                .endEventDateTime(LocalDateTime.of(2021 , Month.MARCH , 18 , 14 , 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

        String content = objectMapper.writeValueAsString(event);
        mockMvc.perform(post("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                                .accept(MediaTypes.HAL_JSON)
                                .content(content))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE , MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("create-event",
                                links(
                                        linkWithRel("self").description("link to self"),
                                        linkWithRel("query-events").description("lint to query events"),
                                        linkWithRel("update-event").description("link to update an existing event"),
                                        linkWithRel("profile").description("link to update an existing event")
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.ACCEPT).description("accept_header"),
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type_header")
                                ),
                                requestFields(
                                        fieldWithPath("name").description("name of new event"),
                                        fieldWithPath("description").description("description of new evnet"),
                                        fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                        fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                        fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                        fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                        fieldWithPath("location").description("location of new event"),
                                        fieldWithPath("basePrice").description("basePrice of new event"),
                                        fieldWithPath("maxPrice").description("maxPrice of new event"),
                                        fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event")
                                ),
                                responseHeaders(
                                        headerWithName(HttpHeaders.LOCATION).description("location_header"),
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type_header")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("identifier of new event"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("name of new event"),
                                        fieldWithPath("description").description("description of new evnet").optional(),
                                        fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                        fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                        fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                        fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                        fieldWithPath("location").description("location of new event"),
                                        fieldWithPath("basePrice").description("basePrice of new event"),
                                        fieldWithPath("maxPrice").description("maxPrice of new event"),
                                        fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event"),
                                        fieldWithPath("free").description("it tells if this event is free or not"),
                                        fieldWithPath("offline").description("it tells if this event is offline event or not"),
                                        fieldWithPath("eventStatus").description("event status"),
                                        fieldWithPath("_links.self.href").description("link to self").ignored(),
                                        fieldWithPath("_links.query-events.href").description("link to query-events"),
                                        fieldWithPath("_links.update-event.href").description("link to update-event"),
                                        fieldWithPath("_links.profile.href").description("link to profile")
                                )
                ));
    }

    private String getBearerToken() throws Exception{
        return "Bearer " + getAccessToken();
    }

    private String getAccessToken() throws Exception{
        //given
        String username = appProperties.getAdminUsername();
        String password = appProperties.getAdminPassword();
        Account goldapple = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER ))
                .build();
        this.accountService.saveAccount(goldapple);

        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                                                             .with(httpBasic(appProperties.getClientId() , appProperties.getClientSecret()))
                                                             .param("username" , username)
                                                             .param("password" , password)
                                                             .param("grant_type" , "password"));
        var responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
    }

    @Test
    @DisplayName("입력받는값이 아닌데 입력값이 들어올때 BadRequest 테스트")
    void createEvent_Bad_Request() throws Exception{
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021 , Month.MARCH , 15 , 14 , 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2021 , Month.MARCH , 16 , 14 , 21))
                .beginEventDateTime(LocalDateTime.of(2021 , Month.MARCH , 17 , 14 , 21))
                .endEventDateTime(LocalDateTime.of(2021 , Month.MARCH , 18 , 14 , 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        String content = objectMapper.writeValueAsString(event);
        mockMvc.perform(post("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                                .characterEncoding("UTF-8")
                                .accept(MediaTypes.HAL_JSON)
                                .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력값이 빈값이 들어왔을때 에러가 발생하는 테스트")
    void createEvent_Bad_Request_Empty_Input() throws Exception{
        EventDto eventDto = EventDto.builder().build();
        this.mockMvc.perform(post("/api/events")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                                     .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력값이 잘못들어왔을때 에러가 발생하는 테스트")
    void createEvent_Bad_Request_Wrong_Input() throws Exception{
        //등록일자 종료일자가 이벤트 시작일자 종료일자보다 느리게 설정
        //기본가격이 최고 가격보다 높게 설정
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021 , Month.MARCH , 26 , 14 , 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2021 , Month.MARCH , 25 , 14 , 21))
                .beginEventDateTime(LocalDateTime.of(2021 , Month.MARCH , 24 , 14 , 21))
                .endEventDateTime(LocalDateTime.of(2021 , Month.MARCH , 23 , 14 , 21))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();
        this.mockMvc.perform(post("/api/events")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                                     .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].field").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].rejectedValue").exists())
                .andExpect(jsonPath("_links.index").exists());
    }
    @Test
    @DisplayName("30개의 이벤트를 10개씩 두번째 페이지 조회하기")
    void queryEvents() throws Exception{
        //given
        IntStream.range(0,30).forEach(this::generateEvent);

        //when
        this.mockMvc.perform(get("/api/events")
                    .param("page","1")
                    .param("size","10")
                    .param("sort","name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events",
                                    relaxedResponseFields(
                                        fieldWithPath("_embedded.eventList").description("Event List"),
                                        fieldWithPath("page.size").description("Count per page"),
                                        fieldWithPath("page.totalElements").description("list count"),
                                        fieldWithPath("page.totalPages").description("totalPage size"),
                                        fieldWithPath("page.number").description("current page number ,0 is first page")
                                    ),
                                    links(
                                        linkWithRel("first").description("첫번째 페이지"),
                                        linkWithRel("prev").description("현재페이지 기준으로 이전페이지"),
                                        linkWithRel("self").description("현재페이지"),
                                        linkWithRel("next").description("현재페이지 기준으로 다음페이지"),
                                        linkWithRel("last").description("마지막 페이지"),
                                        linkWithRel("profile").description("link to profile")
                                    )
                                )
                )
        ;

    }
    @Test
    @DisplayName("30개의 이벤트를 10개씩 두번째 페이지 조회하기")
    void queryEventsWithAuthentication() throws Exception{
        //given
        IntStream.range(0,30).forEach(this::generateEvent);

        //when
        this.mockMvc.perform(get("/api/events")
                                     .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                                     .param("page","1")
                                     .param("size","10")
                                     .param("sort","name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.create-event").exists())
                ;
        ;

    }





    @Test
    @DisplayName("기존의 이벤트를 하나 조회하기")
    void getEvent() throws Exception{
        //given
        Event event = this.generateEvent(100);
        //when
        this.mockMvc.perform(get("/api/events/{id}",event.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-event",
                                    links(
                                            linkWithRel("self").description("link to self"),
                                            linkWithRel("profile").description("link to update an existing event")
                                    ),
                                    relaxedResponseFields(
                                            fieldWithPath("id").type(JsonFieldType.NUMBER).description("identifier of new event"),
                                            fieldWithPath("name").type(JsonFieldType.STRING).description("name of new event"),
                                            fieldWithPath("description").description("description of new evnet").optional(),
                                            fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                            fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                            fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                            fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                            fieldWithPath("location").description("location of new event"),
                                            fieldWithPath("basePrice").description("basePrice of new event"),
                                            fieldWithPath("maxPrice").description("maxPrice of new event"),
                                            fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event"),
                                            fieldWithPath("free").description("it tells if this event is free or not"),
                                            fieldWithPath("offline").description("it tells if this event is offline event or not"),
                                            fieldWithPath("eventStatus").description("event status"),
                                            fieldWithPath("_links.self.href").description("link to self").ignored(),
                                            fieldWithPath("_links.profile.href").description("link to update an existing event").ignored()
                                    )
                                ))

                ;
        //then
    }

    @Test
    @DisplayName("없는 이벤트를 조회했을때 404 응답받기")
    void getEvent404() throws Exception{
        //when&then
        this.mockMvc.perform(get("/api/events/11883"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("이벤트를 정상적으로 수정하기")
    void updateEvent() throws Exception{
        //given
        Event event = this.generateEvent(200);

        EventDto eventDto = this.modelMapper.map(event , EventDto.class);
        String eventName = "UpdatedEvent";
        eventDto.setName(eventName);
        //when &then
        this.mockMvc.perform(put("/api/events/{id}",event.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(eventName))
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-event",
                                links(
                                        linkWithRel("self").description("link to self"),
                                        linkWithRel("profile").description("link to update an existing event")
                                ),
                                requestFields(
                                        fieldWithPath("name").description("name of new event"),
                                        fieldWithPath("description").description("description of new evnet"),
                                        fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                        fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                        fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                        fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                        fieldWithPath("location").description("location of new event"),
                                        fieldWithPath("basePrice").description("basePrice of new event"),
                                        fieldWithPath("maxPrice").description("maxPrice of new event"),
                                        fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event")
                                ),
                                relaxedResponseFields(
                                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("identifier of new event"),
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("name of new event"),
                                    fieldWithPath("description").description("description of new evnet").optional(),
                                    fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                    fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                    fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                    fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                    fieldWithPath("location").description("location of new event"),
                                    fieldWithPath("basePrice").description("basePrice of new event"),
                                    fieldWithPath("maxPrice").description("maxPrice of new event"),
                                    fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event"),
                                    fieldWithPath("free").description("it tells if this event is free or not"),
                                    fieldWithPath("offline").description("it tells if this event is offline event or not"),
                                    fieldWithPath("eventStatus").description("event status"),
                                    fieldWithPath("_links.self.href").description("link to self").ignored(),
                                    fieldWithPath("_links.profile.href").description("link to update an existing event").ignored()
                        )
                ))
        ;
    }
    @Test
    @DisplayName("입력값이 비어있는 경우에 이벤트 수정 실패")
    void updateEvent400_Empty() throws Exception{
        //given
        Event event = this.generateEvent(200);
        EventDto eventDto = new EventDto();
        //when &then
        this.mockMvc.perform(put("/api/events/{id}",event.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                    .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                ;
    }
    @Test
    @DisplayName("입력값이 잘못된 경우에 이벤트 수정 실패")
    void updateEvent400_Wrong() throws Exception{
        //given
        Event event = this.generateEvent(200);

        EventDto eventDto = this.modelMapper.map(event , EventDto.class);
        eventDto.setBasePrice(2000);
        eventDto.setMaxPrice(1000);
        //when &then
        this.mockMvc.perform(put("/api/events/{id}",event.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                    .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    @DisplayName("존재하지 않는 이벤트 수정 실패")
    void updateEvent404() throws Exception{

        Event event = this.generateEvent(200);
        EventDto eventDto = this.modelMapper.map(event , EventDto.class);
        //when&then
        this.mockMvc.perform(put("/api/events/123123")
                .contentType(MediaType.APPLICATION_JSON)
                 .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
    @Test
    @DisplayName("이벤트 매니저가 아닌 사람이 이벤트 수정 실패")
    void updateEventForbidden() throws Exception{
        String username = appProperties.getUserUsername();
        String password = appProperties.getUserPassword();
        Account goldapple = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER ))
                .build();
        Account account = this.accountService.saveAccount(goldapple);
        Event event = Event.builder()
                .name("event")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021 , Month.MARCH , 23 , 14 , 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2021 , Month.MARCH , 24 , 14 , 21))
                .beginEventDateTime(LocalDateTime.of(2021 , Month.MARCH , 25 , 14 , 21))
                .endEventDateTime(LocalDateTime.of(2021 , Month.MARCH , 26 , 14 , 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(false)
                .offline(true)
                .eventStatus(EventStatus.DRAFT)
                .manager(account)
                .build();
        this.eventRepository.save(event);
        EventDto eventDto = this.modelMapper.map(event , EventDto.class);
        //when&then
        this.mockMvc.perform(put("/api/events/"+event.getId())
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                                     .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }




    private Event generateEvent(int index){
        Event event = Event.builder()
                .name("event" + index)
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021 , Month.MARCH , 23 , 14 , 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2021 , Month.MARCH , 24 , 14 , 21))
                .beginEventDateTime(LocalDateTime.of(2021 , Month.MARCH , 25 , 14 , 21))
                .endEventDateTime(LocalDateTime.of(2021 , Month.MARCH , 26 , 14 , 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(false)
                .offline(true)
                .eventStatus(EventStatus.DRAFT)
                .build();
        return this.eventRepository.save(event);
    }


}
