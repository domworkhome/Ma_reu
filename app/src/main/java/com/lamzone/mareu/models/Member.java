package com.lamzone.mareu.models;

import java.util.Objects;

/**
 * // Created by Stéphane TAILLET on 20/10/2019
 */
public class Member {

    // FIELDS

    private String mEmail;
    private boolean selected;

    // CONSTRUCTOR

    /**
     * @param email
     */
    public Member(String email) {
        mEmail = email;
    }

    // METHODS

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

    // FOR COMPARISON

    @Override
    public boolean equals(Object obj) {
        // Same address
        if (this == obj) return true;

        // Null or the class is different
        if (obj == null || getClass() != obj.getClass()) return false;

        // Cast Object to Member
        Member member = (Member) obj;

        return Objects.equals(this.mEmail, member.mEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mEmail);
    }
}
