package com.springboot.opentable.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_SUCH_MANAGER("등록되지 않은 매니저 회원번호입니다"),
    DUPLICATE_EMAIL_MANAGER("해당 이메일을 등록한 매니저 회원이 이미 존재합니다"),
    NOT_A_PARTNER("해당 매니저는 파트너 매니저가 아닙니다"),

    NO_SUCH_CUSTOMER("등록되지 않은 고객 회원번호입니다"),
    DUPLICATE_EMAIL_CUSTOMER("해당 이메일을 등록한 고객 회원이 이미 존재합니다"),

    NO_SUCH_STORE("등록되지 않은 매장입니다"),
    DUPLICATE_STORE("해당 매장은 이미 등록되었습니다"),

    NO_SUCH_RESERVATION("해당 번호의 예약은 존재하지 않습니다"),
    DUPLICATE_RESERVATION("해당 내용의 예약이 이미 존재합니다"),
    RESERVATION_ALREADY_CANCELLED("해당 예약은 종전에 취소 되었습니다"),
    CUSTOMER_ALREADY_ARRIVED("해당 예약의 고객님께서 이미 도착하셨습니다"),
    LATE_CHECK_IN("방문 확인이 불가한 시간입니다");

    private final String description;
}
