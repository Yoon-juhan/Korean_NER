CREATE TABLE user_history (
history_id VARCHAR2(100) PRIMARY KEY,
user_id VARCHAR2(30) CONSTRAINT user_id_fk REFERENCES USERS(user_id) ON DELETE CASCADE,
TEXT VARCHAR2(4000),
predict_json VARCHAR2(4000)
);

CREATE SEQUENCE user_history_sequence
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOCYCLE
    NOCACHE;
    
    
drop table user_history;
commit;

select * from users;
select * from user_history;


desc user_history;