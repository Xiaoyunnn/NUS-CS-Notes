import java.util.TreeSet;

public class Solution {
    // TODO: Include your data structures here
    TreeSet<Team> teamsGreater;
    int[] solvedArr;
    long[] penaltyArr;

    public static class Team implements Comparable<Team> {
        int teamId;
        int numSolved;
        long numPenalty;

        public Team(int teamId, int numSolved, long numPenalty) {
            this.teamId = teamId;
            this.numSolved = numSolved;
            this.numPenalty = numPenalty;
        }

        @Override
        public int compareTo(Team o) {
            int compareSolved = Integer.compare(this.numSolved, o.numSolved);
            int comparePenalty = Long.compare(this.numPenalty, o.numPenalty);
            int compareId = Integer.compare(this.teamId, o.teamId);
            if (compareSolved != 0) {
                return compareSolved;
            } else {
                if (comparePenalty != 0) {
                    return -comparePenalty;
                } else {
                    return compareId;
                }
            }
        }

        @Override
        public String toString() {
            return "(" +
                    "" + teamId +
                    ", " + numSolved +
                    ", " + numPenalty +
                    ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Team team = (Team) o;
            return teamId == team.teamId && numSolved == team.numSolved && numPenalty == team.numPenalty;
        }

        @Override
        //        public int hashCode() {
        //            return Objects.hash(teamId, numSolved, numPenalty);
        //        }
        public int hashCode(){
            long hash = (Double.doubleToLongBits(teamId) ^ Double.doubleToLongBits(numSolved) ^Double.doubleToLongBits(numPenalty));
            return (int) (hash ^ (hash >>> 32));
        }
    }

    public Solution(int numTeams) {
        // TODO: Construct/Initialise your data structures here
        this.solvedArr = new int[numTeams + 1];
        this.penaltyArr = new long[numTeams + 1];
        this.teamsGreater = new TreeSet<>();
    }

    public int update(int team, long newPenalty){
        // TODO: Implement your update function here
        int prevNumSolved = this.solvedArr[team];
        long prevPenalty = this.penaltyArr[team];
        this.solvedArr[team]++;
        this.penaltyArr[team] += newPenalty;
        Team one = new Team(1, solvedArr[1], penaltyArr[1]);

        if (team != 1) {
            Team teamNewScores = new Team(team, this.solvedArr[team], this.penaltyArr[team]);
            Team teamPrevScores = new Team(team, prevNumSolved, prevPenalty);

            // check if there are teams with better scores than team 1
            // if the updating team already had a better score than team 1,
            // remove old entry and insert new entry
            if (!teamsGreater.isEmpty() && teamPrevScores.compareTo(one) > 0) {
                teamsGreater.remove(teamPrevScores);
            }

            // insert the updating team into teams with better scores than team 1 into teamsGreater tree set
            if (teamNewScores.compareTo(one) > 0) {
                teamsGreater.add(teamNewScores);
            }
        } else {
            // update is with regard to team 1
            while (!teamsGreater.isEmpty()) {
                Team temp = teamsGreater.lower(one);
                if (temp != null) {
                    // there exists teams with lower scores than team 1 after team 1 is being updated.
                    this.teamsGreater.remove(temp);
                } else {
                    // there no longer exists a team with lower scores than team 1.
                    break;
                }
            }
        }
        return teamsGreater.size() + 1;
    }
}