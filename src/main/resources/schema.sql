CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(30),
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS resources (
    resource_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resource_type VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    capacity INT,
    price_per_slot INT NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS resource_slots (
    slot_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resource_id BIGINT NOT NULL,
    start_at TIMESTAMP NOT NULL,
    end_at TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT uq_resource_slot UNIQUE (resource_id, start_at, end_at),
    CONSTRAINT fk_slot_resource FOREIGN KEY (resource_id) REFERENCES resources(resource_id)
);

CREATE TABLE IF NOT EXISTS reservations (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    resource_id BIGINT NOT NULL,
    start_at TIMESTAMP NOT NULL,
    end_at TIMESTAMP NOT NULL,
    total_price INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_reservation_resource FOREIGN KEY (resource_id) REFERENCES resources(resource_id)
);

CREATE TABLE IF NOT EXISTS reservation_slots (
    reservation_id BIGINT NOT NULL,
    slot_id BIGINT NOT NULL,
    PRIMARY KEY (reservation_id, slot_id),
    CONSTRAINT fk_rs_reservation FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id),
    CONSTRAINT fk_rs_slot FOREIGN KEY (slot_id) REFERENCES resource_slots(slot_id)
);

CREATE TABLE IF NOT EXISTS payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_id BIGINT NOT NULL,
    amount INT NOT NULL,
    method VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    paid_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_reservation FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id)
);
