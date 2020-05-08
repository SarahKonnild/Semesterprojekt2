package org.openjfx.interfaces;

public interface ICast {

    /**
     * Merges the cast members in the persistence.
     *
     * @param cast the {@code Cast} object that is to be merged.
     * @return {@code True}: if the cast successfully merges in the persistence.
     * {@code false}: it wasn't possible to merge.
     */
    boolean mergeCastMembers(ICast cast);

    /**
     * Updates a {@code Cast} object to a specified regDKID value in the persistence layer.
     *
     * @param name    the {@code name} object that is to be updated.
     * @param regDKID the regDKID of the given cast member, created as a {@code integer}.
     * @return the {@code name} object and the {@code regDKID} object.
     */

    /**
     * Saves a {@code Cast} object in the persistence layer.
     *
     * @return {@code True}: if the cast member successfully saves in the persistence.
     * {@code False}: it wasn't possible to save.
     */
    boolean save();

    /**
     * Removes a {@code Cast} object from the persistence layer.
     *
     * @return {@code True}: if the cast member successfully removes in the persistence.
     * {@code False}: it wasn't possible to remove.
     */
    boolean delete();

    /**
     * Takes the new values of the variables as arguments and update the state of the instance to those.
     * It then call methods in persistence to save it to the database.
     * @param name
     * @param regDKID
     * @return True if it saves the updates to database and false if not
     */
    boolean update(String name, String regDKID);

    /**
     * Returns the ID of the cast member. This ID is given by the persistence layer.
     *
     * @return the ID of the cast member.
     */
    int getId();

    /**
     * Returns the name of the cast.
     *
     * @return the name of the cast.
     */
    String getName();

    /**
     * Returns the RegDKID of the cast member. The RegDKID is given in the persistence layer.
     *
     * @return the RegDKID of the cast member.
     */
    String getRegDKID();

    String getRole();

    @Override
    String toString();
}
