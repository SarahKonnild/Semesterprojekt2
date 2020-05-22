package org.openjfx.presentation;

import org.openjfx.interfaces.ICast;

public class PCast {

    /**
     * This class has been created to allow for a singular object to be made, which contains an ICast object
     * along with the String role that the ICast object is associated with. This is due to the way that a
     * HashMap has been made to contain the roles for the Broadcasts/Movies that the Cast may be assigned to.
     * Since the HashMap's contents must be added to a singular array in order to be able to show the contents
     * to the ListView, it would therefore have to display a String-array, if it hadn't been because of this class.
     * <p>
     * Therefore, it is now possible to associate the roles that are received from the HashMap with a PCast object,
     * which can be added to an ArrayList and then written to the ListView. Hence, it is possible to unassign casts
     * from movies/broadcasts. It is therefore purely a presentation-object, that is used to ensure that presentation
     * and use of the presented object can be performed.
     *
     * @author Sarah
     */

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
