

-- 1. 기존 객체 삭제 (있으면 삭제, 없으면 에러 떠도 무시)
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE MEMBER';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- ORA-00942: table or view does not exist
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE MEMBER_SEQ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN -- ORA-02289: sequence does not exist
            RAISE;
        END IF;
END;
/

-- 2. MEMBER 테이블 생성
CREATE TABLE MEMBER(
    NUM   NUMBER,                  -- 회원 번호 (PK 후보)
    NAME  VARCHAR2(10) NOT NULL,   -- 이름
    ID    VARCHAR2(30),            -- 로그인 아이디 (논리 PK)
    PW    VARCHAR2(30) NOT NULL,   -- 비밀번호
    EMAIL VARCHAR2(30),            -- 이메일
    SCORE NUMBER,                  -- 점수
    PRIMARY KEY(ID)                -- ID를 기본키로 사용
);

-- 3. MEMBER_SEQ 시퀀스 생성 (NUM 자동 증가용)
CREATE SEQUENCE MEMBER_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

-- 4. 초기 데이터 (원하면 사용, 아니면 지워도 됨)
INSERT INTO MEMBER (NUM, NAME, ID, PW, EMAIL, SCORE)
VALUES (MEMBER_SEQ.NEXTVAL, 'tina', 'tina1', 'ttt', 'tina11', 0);

INSERT INTO MEMBER (NUM, NAME, ID, PW, EMAIL, SCORE)
VALUES (MEMBER_SEQ.NEXTVAL, 'aa', 'aa', 'aa', 'aa', 0);

INSERT INTO MEMBER (NUM, NAME, ID, PW, EMAIL, SCORE)
VALUES (MEMBER_SEQ.NEXTVAL, 'jina', 'jn', 'jn', 'jn@mail.com', 60);

-- 관리자 계정 예시 (원하면 사용)
INSERT INTO MEMBER (NUM, NAME, ID, PW, EMAIL, SCORE)
VALUES (MEMBER_SEQ.NEXTVAL, 'admin', 'admin', 'admin', 'admin@mail.com', 0);

-- 5. 커밋
COMMIT;