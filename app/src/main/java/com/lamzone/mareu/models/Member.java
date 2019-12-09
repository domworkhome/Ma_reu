package com.lamzone.mareu.models;

import java.util.Objects;

public class Member {

    private String mEmail;
    private boolean selected;

    public Member(String email) {
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Member member = (Member) obj;

        return Objects.equals(this.mEmail, member.mEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mEmail);
    }
}
