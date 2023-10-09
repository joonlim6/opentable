package com.springboot.opentable.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_SUCH_MANAGER("등록되지 않은 매니저 회원번호입니다"),
    DUPLICATE_EMAIL_MANAGER("해당 이메일을 등록한 매니저 회원이 이미 존재합니다"),
    NOT_A_PARTNER("해당 매니저는 파트너 매니저가 아닙니다"),
    NOT_IN_CHARGE("해당 매니저는 이 예약을 취소할 권한이 없습니다"),
    CANT_DECIDE("매니저가 예약을 승인/거절할 수 있는 단계가 아닙니다"),
    INVALID_DECISION("매니저는 예약을 승인 혹은 거절만 할 수 있습니다"),
    WRONG_EMAIL_OR_PASSWORD("이메일 혹은 비밀번호를 잘못 기입하셨습니다"),

    NO_SUCH_CUSTOMER("등록되지 않은 고객 회원번호입니다"),
    DUPLICATE_EMAIL_CUSTOMER("해당 이메일을 등록한 고객 회원이 이미 존재합니다"),

    NO_SUCH_STORE("등록되지 않은 매장입니다"),
    DUPLICATE_STORE("해당 매장은 이미 등록되었습니다"),

    NO_SUCH_RESERVATION("해당 번호의 예약은 존재하지 않습니다"),
    DUPLICATE_RESERVATION("해당 내용의 예약이 이미 존재합니다"),
    RESERVATION_UNAPPROVED("해당 예약은 아직 승인받지 못했습니다"),
//    DENIED_RESERVATION("해당 예약은 매장으로부터 거절되었습니다"),
//    RESERVATION_ALREADY_CANCELLED("해당 예약은 종전에 취소 되었습니다"),
//    CUSTOMER_ALREADY_ARRIVED("해당 예약의 고객님께서 이미 도착하셨습니다"),
    LATE_CHECK_IN("방문 확인 진행이 불가능한 시간입니다"),

    RESERVATION_INCOMPLETE("예약 방문 이전이므로 리뷰를 남기실 수 없습니다"),
    NO_SUCH_REVIEW("해당 번호의 리뷰는 존재하지 않습니다"),
    NOT_YOUR_REVIEW("이 리뷰는 사용자가 작성하신 것이 아닙니다"),
    DUPLICATE_REVIEW("리뷰를 이미 등록하셨습니다");

    private final String description;
}
