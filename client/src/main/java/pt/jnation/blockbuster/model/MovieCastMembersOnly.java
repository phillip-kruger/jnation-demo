package pt.jnation.blockbuster.model;

public class MovieCastMembersOnly {

    private CastMembersWithMainActors castMembers;

    public MovieCastMembersOnly() {
    }

    public CastMembersWithMainActors getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(CastMembersWithMainActors castMembers) {
        this.castMembers = castMembers;
    }
}
