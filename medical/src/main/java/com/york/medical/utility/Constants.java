package com.york.medical.utility;

public class Constants {

    public enum UserRole {
        ROLE_DOCTOR("Doctor"), ROLE_ADMIN("Admin"), ROLE_PATIENT("Patient");

        private String role;

        private UserRole(String role) {
            this.role = role;
        }

        public String value() {
            return this.role;
        }
    }

    public enum ActiveStatus {
        ACTIVE("Active"), DEACTIVATED("Deactivated");

        private String status;

        private ActiveStatus(String status) {
            this.status = status;
        }

        public String value() {
            return this.status;
        }
    }

    public enum AppointmentStatus {
        PENDING("Pending"),
        CONFIRMED("Confirmed"),
        CANCELLED("CANCELLED");

        private String status;

        private AppointmentStatus(String status) {
            this.status = status;
        }

        public String value() {
            return this.status;
        }
    }

    public enum DoctorAvailability {
        YES("Yes"), NO("No");

        private String status;

        private DoctorAvailability(String status) {
            this.status = status;
        }

        public String value() {
            return this.status;
        }
    }

    public enum TimeSlot {
        NINE_TO_TEN_AM("09:00 - 10:00 am"),
        TEN_TO_ELEVEN_AM("10:00 - 11:00 am"),
        ELEVEN_TO_TWELLVE_AM("11:00 - 12:00 am"),
        TWELVE_TO_ONE_PM("12:00 - 01:00 pm"),
        ONE_TO_TWO_PM("01:00 - 02:00 pm"),
        TWO_TO_THREE_PM("02:00 - 03:00 pm"),
        THREE_TO_FOUR_PM("03:00 - 04:00 pm"),
        FOUR_TO_FIVE_PM("04:00 - 05:00 pm"),
        FIVE_TO_SIX_PM("05:00 - 06:00 pm"),
        SIX_TO_SEVEN_PM("06:00 - 07:00 pm"),
        SEVEN_TO_EIGHT_PM("07:00 - 08:00 pm"),
        EIGHT_TO_NINE_PM("08:00 - 09:00 pm"),
        NINE_TO_TEN_PM("09:00 - 10:00 pm");

        private String time;

        private TimeSlot(String time) {
            this.time = time;
        }

        public String value() {
            return this.time;
        }

    }

}