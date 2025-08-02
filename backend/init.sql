-- DB 선택
USE livestream;

-- Users 테이블
CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       login_id VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       description VARCHAR(255),
                       role VARCHAR(20) NOT NULL DEFAULT 'USER',
                       is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                       created_by BIGINT,
                       modified_by BIGINT,
                       created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- CameraType 테이블
CREATE TABLE camera_type (
                             camera_type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             created_by BIGINT,
                             modified_by BIGINT,
                             created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- CameraCategory 테이블
CREATE TABLE camera_category (
                                 camera_category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 camera_category_type VARCHAR(20) NOT NULL,
                                 parent_id BIGINT,
                                 name VARCHAR(100) NOT NULL,
                                 created_by BIGINT,
                                 modified_by BIGINT,
                                 created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 CONSTRAINT fk_parent_category FOREIGN KEY (parent_id) REFERENCES camera_category(camera_category_id)
);

-- Camera 테이블
CREATE TABLE camera (
                        camera_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        camera_category_id BIGINT NOT NULL,
                        camera_type_id BIGINT NOT NULL,
                        name VARCHAR(50) NOT NULL,
                        description VARCHAR(255),
                        streaming_url VARCHAR(255),
                        address VARCHAR(255),
                        latitude DOUBLE,
                        longitude DOUBLE,
                        is_active BOOLEAN NOT NULL DEFAULT TRUE,
                        status VARCHAR(20) NOT NULL DEFAULT 'STOPPED',
                        is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                        created_by BIGINT,
                        modified_by BIGINT,
                        created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        CONSTRAINT fk_camera_category FOREIGN KEY (camera_category_id) REFERENCES camera_category(camera_category_id),
                        CONSTRAINT fk_camera_type FOREIGN KEY (camera_type_id) REFERENCES camera_type(camera_type_id)
);

-- 초기 데이터 삽입

-- CameraType
INSERT INTO camera_type (name) VALUES ('도로');
INSERT INTO camera_type (name) VALUES ('하천');
INSERT INTO camera_type (name) VALUES ('학교앞');

-- CameraCategory
INSERT INTO camera_category (camera_category_type, parent_id, name) VALUES ('LARGE', NULL, '유관기관');
-- 유관기관 하위
INSERT INTO camera_category (camera_category_type, parent_id, name) VALUES ('MEDIUM', 1, '용인교통');
INSERT INTO camera_category (camera_category_type, parent_id, name) VALUES ('MEDIUM', 1, '수원교통');
-- 용인교통 하위
INSERT INTO camera_category (camera_category_type, parent_id, name) VALUES ('SMALL', 2, '수지구');
INSERT INTO camera_category (camera_category_type, parent_id, name) VALUES ('SMALL', 2, '기흥구');

-- Camera
INSERT INTO camera (name, description, camera_category_id, camera_type_id, streaming_url, address, latitude, longitude)
VALUES
    ('흥덕교', '흥덕교 부근', 4, 1, 'http://211.249.12.147:1935/live/video69.stream/playlist.m3u8', '주소', 37.2716932, 127.0910779),
    ('죽전육교', '죽전육교 부근', 4, 1, 'http://211.249.12.147:1935/live/video86.stream/playlist.m3u8', '도로', 37.32723616, 127.13377143),
    ('화운사입구3', '화운사입구3 부근', 4, 1, 'http://211.249.12.147:1935/live/video34.stream/playlist.m3u8', '도로', 37.2541872, 127.1660996),
    ('호수공원삼거리', '호수공원삼거리 부근', 4, 1, 'http://211.249.12.147:1935/live/video22.stream/playlist.m3u8', '도로', 37.2757328, 127.1481881),
    ('풍덕천사거리', '풍덕천사거리 부근', 4, 1, 'http://211.249.12.147:1935/live/video9.stream/playlist.m3u8', '도로', 37.3243, 127.1027);

