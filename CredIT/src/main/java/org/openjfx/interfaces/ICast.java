package org.openjfx.interfaces;

public interface ICast {

    /**
     * Merges the cast members in the persistence.
     *
     * @param cast the {@code Cast} object that is to be merged.
     * @return {@code True}: if the cast successfully merges in the persistence.
     * {@code false}: it wasn't possible to merge.
     */
    public boolean mergeCastMembers(ICast cast);

    /**
     * Updates a {@code Cast} object to a specified regDKID value in the persistence layer.
     *
     * @param name    the {@code name} object that is to be updated.
     * @param regDKID the regDKID of the given cast member, created as a {@code integer}.
     * @return the {@code name} object and the {@code regDKID} object.
     */
    public boolean updateCast(String name, String regDKID);

    /**
     * Saves a {@code Cast} object in the persistence layer.
     *
     * @return {@code True}: if the cast member successfully saves in the persistence.
     * {@code False}: it wasn't possible to save.
     */
    public boolean save();

    /**
     * Removes a {@code Cast} object from the persistence layer.
     *
     * @return {@code True}: if the cast member successfully removes in the persistence.
     * {@code False}: it wasn't possible to remove.
     */
    public boolean deleteCast();

    /**
     * Returns the ID of the cast member. This ID is given by the persistence layer.
     *
     * @return the ID of the cast member.
     */
    public int getId();

    /**
     * Returns the name of the cast.
     *
     * @return the name of the cast.
     */
    public String getName();

    /**
     * Returns the RegDKID of the cast member. The RegDKID is given in the persistence layer.
     *
     * @return the RegDKID of the cast member.
     */
    public String getRegDKID();

    public String getRole();

    public void setRole(String role);

    @Override
    public String toString();
}
