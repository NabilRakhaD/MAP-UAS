package umn.ac.id.uas.retrofit;

import java.util.List;

public class UserModel {
    private List<User> users;
    public List<User> getUsers(){
        return users;
    }

    public class User{
        private int id;
        private String full_name, email;
        private double balance;

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", full_name='" + full_name + '\'' +
                    ", email='" + email + '\'' +
                    ", balance=" + balance +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}
