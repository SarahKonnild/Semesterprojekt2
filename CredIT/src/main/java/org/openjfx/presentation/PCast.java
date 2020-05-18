package org.openjfx.presentation;

import org.openjfx.interfaces.ICast;

public class PCast {

    private ICast cast;
    private String role;

    public PCast(ICast cast, String role) {
        this.role = role;
        this.cast = cast;
    }

    public ICast getCast() {
        return cast;
    }

    public String getRole() {
        return role;
    }

    public void setCast(ICast cast) {
        this.cast = cast;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return cast.getName() + " : " + role;
    }
}
