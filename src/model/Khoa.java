    package model;

    public class Khoa {
        private String maKhoa;
        private String tenKhoa;

        // Constructor mặc định
        public Khoa() {
        }

        // Constructor có tham số
        public Khoa(String maKhoa, String tenKhoa) {
            this.maKhoa = maKhoa;
            this.tenKhoa = tenKhoa;
        }

        // GETTER và SETTER
        public String getMaKhoa() {
            return maKhoa;
        }

        public void setMaKhoa(String maKhoa) {
            this.maKhoa = maKhoa;
        }

        public String getTenKhoa() {
            return tenKhoa;
        }

        public void setTenKhoa(String tenKhoa) {
            this.tenKhoa = tenKhoa;
        }

        // Phương thức so sánh (dùng cho Collections)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Khoa khoa = (Khoa) o;
            return maKhoa.equals(khoa.maKhoa);
        }

        @Override
        public int hashCode() {
            return maKhoa.hashCode();
        }
    }